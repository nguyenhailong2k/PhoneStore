package com.example.controller;

import com.example.dao.AdminDAO;
import com.example.model.Admin;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AdminDAO adminDAO;
    
    @Override
    public void init() {
        adminDAO = new AdminDAO();
    }
    
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        
        Admin admin = adminDAO.validateAdmin(username, password);
        
        if (admin != null) {
            HttpSession session = req.getSession();
            session.setAttribute("user", admin.getUsername());
            session.setAttribute("admin", admin);
            resp.sendRedirect("admin");
        } else {
            resp.sendRedirect("login.jsp?error=1");
        }
    }
}