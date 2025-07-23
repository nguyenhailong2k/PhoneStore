package com.example.controller;

import com.example.dao.OrderDAO;
import com.example.model.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrderDAO orderDAO;

    @Override
    public void init() {
        orderDAO = new OrderDAO();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if ("list".equals(action)) {
            List<Order> orders = orderDAO.getAllOrders();
            req.setAttribute("orders", orders);
            req.getRequestDispatcher("orderList.jsp").forward(req, resp);
        } else if ("view".equals(action)) {
            int orderId = Integer.parseInt(req.getParameter("id"));
            Order order = orderDAO.getOrderById(orderId);
            req.setAttribute("order", order);
            req.getRequestDispatcher("orderDetail.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("admin.jsp");
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if ("create".equals(action)) {
            createOrder(req, resp);
        } else if ("updateStatus".equals(action)) {
            updateOrderStatus(req, resp);
        } else if ("delete".equals(action)) {
            deleteOrder(req, resp);
        }
    }

    private void createOrder(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null || cart.getItems().isEmpty()) {
            session.setAttribute("error", "Giỏ hàng trống!");
            resp.sendRedirect("cart");
            return;
        }

        try {
            Order order = new Order();
            order.setCustomerName(req.getParameter("customerName"));
            order.setEmail(req.getParameter("email"));
            order.setPhone(req.getParameter("phone"));
            order.setAddress(req.getParameter("address"));
            order.setTotalAmount(cart.getTotalPrice());

            int orderId = orderDAO.createOrder(order);

            if (orderId > 0) {
                for (CartItem item : cart.getItems()) {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrderId(orderId);
                    orderItem.setProductId(item.getProduct().getId());
                    orderItem.setProductName(item.getProduct().getName());
                    orderItem.setPrice(item.getProduct().getPrice());
                    orderItem.setQuantity(item.getQuantity());
                    orderItem.setSubtotal(item.getSubtotal());

                    orderDAO.addOrderItem(orderItem);
                }

                cart.clear();
                session.setAttribute("message", "Đặt hàng thành công! Mã đơn hàng: " + orderId);
                resp.sendRedirect("orderSuccess.jsp?orderId=" + orderId);
            } else {
                session.setAttribute("error", "Có lỗi xảy ra khi đặt hàng!");
                resp.sendRedirect("checkout.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            resp.sendRedirect("checkout.jsp");
        }
    }

    private void updateOrderStatus(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            int orderId = Integer.parseInt(req.getParameter("orderId"));
            String status = req.getParameter("status");

            if (orderDAO.updateOrderStatus(orderId, status)) {
                req.getSession().setAttribute("message", "Đã cập nhật trạng thái đơn hàng!");
            } else {
                req.getSession().setAttribute("error", "Không thể cập nhật trạng thái đơn hàng!");
            }
        } catch (Exception e) {
            req.getSession().setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }

        resp.sendRedirect("order?action=list");
    }

    // ✅ Thêm chức năng xóa đơn hàng
    private void deleteOrder(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            int orderId = Integer.parseInt(req.getParameter("id"));
            orderDAO.deleteOrderById(orderId);
            req.getSession().setAttribute("message", "Đã xóa đơn hàng thành công!");
        } catch (Exception e) {
            req.getSession().setAttribute("error", "Không thể xóa đơn hàng: " + e.getMessage());
        }

        resp.sendRedirect("order?action=list");
    }
}
