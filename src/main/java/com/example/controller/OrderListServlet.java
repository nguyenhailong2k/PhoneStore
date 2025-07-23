package com.example.controller;

import com.example.dao.OrderDAO;
import com.example.model.Customer;
import com.example.model.Order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/orders")
public class OrderListServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OrderDAO orderDAO;

    @Override
    public void init() {
        orderDAO = new OrderDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Object userObj = session.getAttribute("customer");
        List<Order> orders;

        if (userObj == null) {
            resp.sendRedirect("index.jsp");
            return;
        }

        if (userObj instanceof Customer) {
            Customer customer = (Customer) userObj;

            if ("admin".equalsIgnoreCase(customer.getEmail())) {
                orders = orderDAO.getAllOrders();
            } else {
                orders = orderDAO.getOrdersByEmail(customer.getEmail());
            }

            req.setAttribute("orders", orders);
            req.getRequestDispatcher("orderList.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("index.jsp");
        }
    }
}