package com.example.controller;

import com.example.dao.CustomerDAO;
import com.example.model.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/loginCustomer")
public class LoginCustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CustomerDAO customerDAO;

    @Override
    public void init() {
        customerDAO = new CustomerDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        Customer customer = customerDAO.checkLogin(email, password);

        if (customer != null) {
            // Đăng nhập thành công
            HttpSession session = req.getSession();
            session.setAttribute("customer", customer);
            resp.sendRedirect("product");
        } else {
            // Đăng nhập thất bại
            req.setAttribute("loginError", "Sai email hoặc mật khẩu!");
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        }
    }
}
