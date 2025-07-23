package com.example.controller;

import com.example.dao.ProductDAO;
import com.example.model.Cart;
import com.example.model.Product;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
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
        
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        
        req.setAttribute("cart", cart);
        req.getRequestDispatcher("cart.jsp").forward(req, resp);
    }
    
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        String action = req.getParameter("action");
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        
        try {
            switch (action) {
                case "add":
                    int productId = Integer.parseInt(req.getParameter("productId"));
                    int quantity = Integer.parseInt(req.getParameter("quantity"));
                    Product product = productDAO.getProductById(productId);
                    
                    if (product != null) {
                        cart.addItem(product, quantity);
                        session.setAttribute("message", "Đã thêm sản phẩm vào giỏ hàng!");
                    }
                    break;
                    
                case "remove":
                    int removeId = Integer.parseInt(req.getParameter("productId"));
                    cart.removeItem(removeId);
                    session.setAttribute("message", "Đã xóa sản phẩm khỏi giỏ hàng!");
                    break;
                    
                case "update":
                    int updateId = Integer.parseInt(req.getParameter("productId"));
                    int newQuantity = Integer.parseInt(req.getParameter("quantity"));
                    cart.updateQuantity(updateId, newQuantity);
                    session.setAttribute("message", "Đã cập nhật số lượng!");
                    break;
                    
                case "clear":
                    cart.clear();
                    session.setAttribute("message", "Đã xóa tất cả sản phẩm trong giỏ hàng!");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }
        
        resp.sendRedirect("cart");
    }
}