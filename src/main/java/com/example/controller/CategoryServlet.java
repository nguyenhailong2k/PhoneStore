package com.example.controller;

import com.example.dao.CategoryDAO;
import com.example.dao.ProductDAO;
import com.example.model.Category;
import com.example.model.Product;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/category")
public class CategoryServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CategoryDAO categoryDAO;
    private ProductDAO productDAO;
    
    @Override
    public void init() {
        categoryDAO = new CategoryDAO();
        productDAO = new ProductDAO();
    }
    
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        String action = req.getParameter("action");
        
        if ("list".equals(action)) {
            List<Category> categories = categoryDAO.getAllCategories();
            req.setAttribute("categories", categories);
            req.getRequestDispatcher("categoryList.jsp").forward(req, resp);
        } else if ("view".equals(action)) {
            int categoryId = Integer.parseInt(req.getParameter("id"));
            Category category = categoryDAO.getCategoryById(categoryId);
            List<Product> products = productDAO.getProductsByCategory(categoryId);
            
            req.setAttribute("category", category);
            req.setAttribute("products", products);
            req.getRequestDispatcher("categoryProducts.jsp").forward(req, resp);
        } else if ("edit".equals(action)) {
            int categoryId = Integer.parseInt(req.getParameter("id"));
            Category category = categoryDAO.getCategoryById(categoryId);
            req.setAttribute("category", category);
            req.getRequestDispatcher("editCategory.jsp").forward(req, resp);
        } else {
            List<Category> categories = categoryDAO.getAllCategories();
            req.setAttribute("categories", categories);
            req.getRequestDispatcher("categoryList.jsp").forward(req, resp);
        }
    }
    
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        String action = req.getParameter("action");
        
        if ("add".equals(action)) {
            addCategory(req, resp);
        } else if ("update".equals(action)) {
            updateCategory(req, resp);
        } else if ("delete".equals(action)) {
            deleteCategory(req, resp);
        }
    }
    
    private void addCategory(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        try {
            Category category = new Category();
            category.setName(req.getParameter("name"));
            category.setDescription(req.getParameter("description"));
            category.setImage(req.getParameter("image"));
            category.setActive("on".equals(req.getParameter("active")));
            
            if (categoryDAO.addCategory(category)) {
                req.getSession().setAttribute("message", "Đã thêm danh mục thành công!");
            } else {
                req.getSession().setAttribute("error", "Không thể thêm danh mục!");
            }
        } catch (Exception e) {
            req.getSession().setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }
        
        resp.sendRedirect("category?action=list");
    }
    
    private void updateCategory(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        try {
            Category category = new Category();
            category.setId(Integer.parseInt(req.getParameter("id")));
            category.setName(req.getParameter("name"));
            category.setDescription(req.getParameter("description"));
            category.setImage(req.getParameter("image"));
            category.setActive("on".equals(req.getParameter("active")));
            
            if (categoryDAO.updateCategory(category)) {
                req.getSession().setAttribute("message", "Đã cập nhật danh mục thành công!");
            } else {
                req.getSession().setAttribute("error", "Không thể cập nhật danh mục!");
            }
        } catch (Exception e) {
            req.getSession().setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }
        
        resp.sendRedirect("category?action=list");
    }
    
    private void deleteCategory(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        try {
            int categoryId = Integer.parseInt(req.getParameter("id"));
            
            if (categoryDAO.deleteCategory(categoryId)) {
                req.getSession().setAttribute("message", "Đã xóa danh mục thành công!");
            } else {
                req.getSession().setAttribute("error", "Không thể xóa danh mục!");
            }
        } catch (Exception e) {
            req.getSession().setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }
        
        resp.sendRedirect("category?action=list");
    }
}