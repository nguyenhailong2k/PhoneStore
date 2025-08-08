// Gói chứa lớp này
package com.example.controller;

// Import các lớp cần thiết
import com.example.dao.OrderDAO;
import com.example.model.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

// Servlet xử lý các yêu cầu liên quan đến đơn hàng, ánh xạ với URL /order
@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private OrderDAO orderDAO;

    // Phương thức khởi tạo servlet, tạo đối tượng DAO để thao tác với CSDL
    @Override
    public void init() {
        orderDAO = new OrderDAO();
    }

    // Xử lý các yêu cầu GET (xem danh sách, xem chi tiết đơn hàng)
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if ("list".equals(action)) {
            // Lấy danh sách tất cả đơn hàng
            List<Order> orders = orderDAO.getAllOrders();
            req.setAttribute("orders", orders);
            // Hiển thị danh sách ra giao diện
            req.getRequestDispatcher("orderList.jsp").forward(req, resp);

        } else if ("view".equals(action)) {
            // Lấy thông tin đơn hàng theo ID
            int orderId = Integer.parseInt(req.getParameter("id"));
            Order order = orderDAO.getOrderById(orderId);
            req.setAttribute("order", order);
            // Hiển thị chi tiết đơn hàng
            req.getRequestDispatcher("orderDetail.jsp").forward(req, resp);

        } else {
            // Nếu không có action hợp lệ thì chuyển về trang admin
            resp.sendRedirect("admin.jsp");
        }
    }

    // Xử lý các yêu cầu POST (tạo đơn hàng, cập nhật trạng thái, xóa đơn hàng)
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if ("create".equals(action)) {
            createOrder(req, resp); // Tạo đơn hàng mới
        } else if ("updateStatus".equals(action)) {
            updateOrderStatus(req, resp); // Cập nhật trạng thái đơn hàng
        } else if ("delete".equals(action)) {
            deleteOrder(req, resp); // Xóa đơn hàng
        }
    }

    // Hàm xử lý tạo đơn hàng mới
    private void createOrder(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        // Nếu giỏ hàng rỗng thì báo lỗi và chuyển về trang giỏ hàng
        if (cart == null || cart.getItems().isEmpty()) {
            session.setAttribute("error", "Giỏ hàng trống!");
            resp.sendRedirect("cart");
            return;
        }

        try {
            // Tạo đối tượng Order từ dữ liệu form
            Order order = new Order();
            order.setCustomerName(req.getParameter("customerName"));
            order.setEmail(req.getParameter("email"));
            order.setPhone(req.getParameter("phone"));
            order.setAddress(req.getParameter("address"));
            order.setTotalAmount(cart.getTotalPrice());

            // Thêm đơn hàng vào CSDL và lấy ID vừa tạo
            int orderId = orderDAO.createOrder(order);

            if (orderId > 0) {
                // Duyệt từng sản phẩm trong giỏ hàng để thêm vào chi tiết đơn hàng
                for (CartItem item : cart.getItems()) {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrderId(orderId);
                    orderItem.setProductId(item.getProduct().getId());
                    orderItem.setProductName(item.getProduct().getName());
                    orderItem.setPrice(item.getProduct().getPrice());
                    orderItem.setQuantity(item.getQuantity());
                    orderItem.setSubtotal(item.getSubtotal());

                    orderDAO.addOrderItem(orderItem); // Thêm vào DB
                }

                // Xóa giỏ hàng và thông báo thành công
                cart.clear();
                session.setAttribute("message", "Đặt hàng thành công! Mã đơn hàng: " + orderId);
                resp.sendRedirect("orderSuccess.jsp?orderId=" + orderId);

            } else {
                // Báo lỗi nếu không thêm được đơn hàng
                session.setAttribute("error", "Có lỗi xảy ra khi đặt hàng!");
                resp.sendRedirect("checkout.jsp");
            }

        } catch (Exception e) {
            // Bắt lỗi và thông báo
            e.printStackTrace();
            session.setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
            resp.sendRedirect("checkout.jsp");
        }
    }

    // Hàm xử lý cập nhật trạng thái đơn hàng
    private void updateOrderStatus(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            int orderId = Integer.parseInt(req.getParameter("orderId"));
            String status = req.getParameter("status");

            // Thử cập nhật trạng thái trong DB
            if (orderDAO.updateOrderStatus(orderId, status)) {
                req.getSession().setAttribute("message", "Đã cập nhật trạng thái đơn hàng!");
            } else {
                req.getSession().setAttribute("error", "Không thể cập nhật trạng thái đơn hàng!");
            }
        } catch (Exception e) {
            // Thông báo lỗi nếu có exception
            req.getSession().setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }

        // Chuyển về trang danh sách đơn hàng
        resp.sendRedirect("order?action=list");
    }

    // ✅ Hàm xử lý xóa đơn hàng khỏi hệ thống
    private void deleteOrder(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            int orderId = Integer.parseInt(req.getParameter("id"));
            orderDAO.deleteOrderById(orderId); // Gọi DAO để xóa
            req.getSession().setAttribute("message", "Đã xóa đơn hàng thành công!");
        } catch (Exception e) {
            req.getSession().setAttribute("error", "Không thể xóa đơn hàng: " + e.getMessage());
        }

        // Sau khi xóa, quay lại danh sách đơn hàng
        resp.sendRedirect("order?action=list");
    }
}
