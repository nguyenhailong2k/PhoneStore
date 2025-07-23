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

@WebServlet("/product")
public class ProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductDAO productDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if ("detail".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            Product product = productDAO.getProductById(id);
            req.setAttribute("product", product);
            req.getRequestDispatcher("productDetail.jsp").forward(req, resp);
            return;
        }

        String keyword = req.getParameter("keyword");
        String sort = req.getParameter("sort");
        String minStr = req.getParameter("min");
        String maxStr = req.getParameter("max");
        String categoryIdStr = req.getParameter("categoryId");

        Double minPrice = null;
        Double maxPrice = null;
        Integer categoryId = null;

        // Parse price
        try {
            if (minStr != null && !minStr.trim().isEmpty()) {
                minPrice = Double.parseDouble(minStr.trim());
            }
            if (maxStr != null && !maxStr.trim().isEmpty()) {
                maxPrice = Double.parseDouble(maxStr.trim());
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        // Parse categoryId
        try {
            if (categoryIdStr != null && !categoryIdStr.trim().isEmpty()) {
                categoryId = Integer.parseInt(categoryIdStr.trim());

                // Lấy tên danh mục để hiển thị
                CategoryDAO categoryDAO = new CategoryDAO();
                Category selectedCategory = categoryDAO.getCategoryById(categoryId);
                req.setAttribute("selectedCategory", selectedCategory);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        // Lọc sản phẩm với các tham số
        List<Product> products = productDAO.filterProductsWithCategory(keyword, minPrice, maxPrice, sort, categoryId);

        req.setAttribute("products", products);
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}
