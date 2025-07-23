package com.example.controller;

import com.example.dao.ProductDAO;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/delete")
public class DeleteProductServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ProductDAO productDAO;
    
    @Override
    public void init() {
        productDAO = new ProductDAO();
    }
    
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            boolean success = productDAO.deleteProduct(id);
            
            if (success) {
                req.getSession().setAttribute("message", "Xóa sản phẩm thành công!");
            } else {
                req.getSession().setAttribute("error", "Có lỗi xảy ra khi xóa sản phẩm!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.getSession().setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }
        
        resp.sendRedirect("admin");
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}