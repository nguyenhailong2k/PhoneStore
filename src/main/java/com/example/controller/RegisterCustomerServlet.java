package com.example.controller;

import com.example.dao.CustomerDAO;
import com.example.model.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/registerCustomer")
public class RegisterCustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CustomerDAO customerDAO;

    @Override
    public void init() {
        customerDAO = new CustomerDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

    	String name = req.getParameter("name");
    	String email = req.getParameter("email");
    	String password = req.getParameter("password");
    	String phone = req.getParameter("phone");
    	String address = req.getParameter("address");

        if (customerDAO.isEmailExists(email)) {
            req.setAttribute("registerError", "Email đã tồn tại!");
            req.getRequestDispatcher("index.jsp").forward(req, resp);
            return;
        }

        Customer newCustomer = new Customer();
        newCustomer.setName(name);
        newCustomer.setEmail(email);
        newCustomer.setPassword(password);
        newCustomer.setPhone(phone);
        newCustomer.setAddress(address);

        customerDAO.addCustomer(newCustomer);

        // Đăng nhập ngay sau khi đăng ký
        HttpSession session = req.getSession();
        session.setAttribute("customer", newCustomer);

        resp.sendRedirect("product");
    }
}
