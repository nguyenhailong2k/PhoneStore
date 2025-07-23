package com.example.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.dao.CustomerDAO;
import com.example.model.Customer;

@WebServlet("/customer")
public class CustomerServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CustomerDAO customerDAO;

    @Override
    public void init() {
        customerDAO = new CustomerDAO();
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");
        if ("register".equals(action)) {
            // Đăng ký
            Customer c = new Customer();
            c.setName(req.getParameter("name"));
            c.setEmail(req.getParameter("email"));
            c.setPassword(req.getParameter("password")); // Hash sau nếu cần
            c.setPhone(req.getParameter("phone"));
            c.setAddress(req.getParameter("address"));
            boolean success = customerDAO.register(c);
            resp.sendRedirect(success ? "loginCustomer.jsp?success=1" : "registerCustomer.jsp?error=1");

        } else if ("login".equals(action)) {
            // Đăng nhập
            String email = req.getParameter("email");
            String pass = req.getParameter("password");
            Customer c = customerDAO.login(email, pass);
            if (c != null) {
                req.getSession().setAttribute("customer", c);
                resp.sendRedirect("product");
            } else {
                resp.sendRedirect("loginCustomer.jsp?error=1");
            }
        }
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("CustomerServlet is alive!");
    }

}
