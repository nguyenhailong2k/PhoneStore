// Khai báo package và các import cần thiết
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

// Định nghĩa servlet với URL pattern "/product"
@WebServlet("/product")
public class ProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductDAO productDAO;

    // Khởi tạo đối tượng ProductDAO khi servlet được tạo
    @Override
    public void init() {
        productDAO = new ProductDAO();
    }

    // Xử lý yêu cầu GET từ client
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Lấy tham số 'action' từ URL (ví dụ: ?action=detail)
        String action = req.getParameter("action");

        // Nếu action là "detail" → hiển thị chi tiết sản phẩm
        if ("detail".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id")); // Lấy ID sản phẩm
            Product product = productDAO.getProductById(id);    // Tìm sản phẩm theo ID
            req.setAttribute("product", product);               // Gán sản phẩm vào request
            req.getRequestDispatcher("productDetail.jsp").forward(req, resp); // Chuyển tiếp đến trang chi tiết
            return;
        }

        // Nếu không phải "detail" → xử lý danh sách sản phẩm, lọc, tìm kiếm, phân loại
        String keyword = req.getParameter("keyword");      // Từ khóa tìm kiếm
        String sort = req.getParameter("sort");            // Kiểu sắp xếp (priceAsc, priceDesc...)
        String minStr = req.getParameter("min");           // Giá tối thiểu
        String maxStr = req.getParameter("max");           // Giá tối đa
        String categoryIdStr = req.getParameter("categoryId"); // ID danh mục sản phẩm

        Double minPrice = null;
        Double maxPrice = null;
        Integer categoryId = null;

        // Parse (chuyển đổi) giá từ chuỗi sang kiểu số thực
        try {
            if (minStr != null && !minStr.trim().isEmpty()) {
                minPrice = Double.parseDouble(minStr.trim());
            }
            if (maxStr != null && !maxStr.trim().isEmpty()) {
                maxPrice = Double.parseDouble(maxStr.trim());
            }
        } catch (NumberFormatException e) {
            e.printStackTrace(); // In lỗi nếu nhập không hợp lệ
        }

        // Parse categoryId từ chuỗi sang số nguyên
        try {
            if (categoryIdStr != null && !categoryIdStr.trim().isEmpty()) {
                categoryId = Integer.parseInt(categoryIdStr.trim());

                // Lấy tên danh mục để hiển thị trên giao diện
                CategoryDAO categoryDAO = new CategoryDAO();
                Category selectedCategory = categoryDAO.getCategoryById(categoryId);
                req.setAttribute("selectedCategory", selectedCategory);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        // Lấy danh sách sản phẩm sau khi đã lọc và sắp xếp
        List<Product> products = productDAO.filterProductsWithCategory(
            keyword, minPrice, maxPrice, sort, categoryId);

        // Gán danh sách sản phẩm vào request
        req.setAttribute("products", products);

        // Chuyển hướng đến trang index.jsp (trang hiển thị sản phẩm)
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }

    // Nếu có POST request thì xử lý tương tự GET
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp); // Gọi lại doGet
    }
}
