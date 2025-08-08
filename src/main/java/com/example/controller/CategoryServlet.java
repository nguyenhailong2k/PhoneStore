// Khai báo package và các thư viện cần thiết
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

// Servlet xử lý các thao tác liên quan đến danh mục (category), ánh xạ tới URL "/category"
@WebServlet("/category")
public class CategoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // DAO để thao tác với CSDL
    private CategoryDAO categoryDAO;
    private ProductDAO productDAO;

    // Hàm khởi tạo servlet, chạy khi servlet được load lần đầu
    @Override
    public void init() {
        categoryDAO = new CategoryDAO(); // Truy vấn danh mục
        productDAO = new ProductDAO();   // Truy vấn sản phẩm
    }

    // Xử lý yêu cầu GET (hiển thị danh sách, chi tiết, chỉnh sửa danh mục,...)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action"); // Lấy action từ URL

        // Hiển thị danh sách tất cả danh mục
        if ("list".equals(action)) {
            List<Category> categories = categoryDAO.getAllCategories();
            req.setAttribute("categories", categories);
            req.getRequestDispatcher("categoryList.jsp").forward(req, resp);

        // Hiển thị sản phẩm theo danh mục cụ thể
        } else if ("view".equals(action)) {
            int categoryId = Integer.parseInt(req.getParameter("id"));
            Category category = categoryDAO.getCategoryById(categoryId);
            List<Product> products = productDAO.getProductsByCategory(categoryId);

            req.setAttribute("category", category);
            req.setAttribute("products", products);
            req.getRequestDispatcher("categoryProducts.jsp").forward(req, resp);

        // Hiển thị form chỉnh sửa danh mục
        } else if ("edit".equals(action)) {
            int categoryId = Integer.parseInt(req.getParameter("id"));
            Category category = categoryDAO.getCategoryById(categoryId);
            req.setAttribute("category", category);
            req.getRequestDispatcher("editCategory.jsp").forward(req, resp);

        // Mặc định: hiển thị danh sách danh mục
        } else {
            List<Category> categories = categoryDAO.getAllCategories();
            req.setAttribute("categories", categories);
            req.getRequestDispatcher("categoryList.jsp").forward(req, resp);
        }
    }

    // Xử lý yêu cầu POST (thêm, sửa, xóa danh mục)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action"); // Lấy action từ form gửi lên

        if ("add".equals(action)) {
            addCategory(req, resp); // Thêm danh mục
        } else if ("update".equals(action)) {
            updateCategory(req, resp); // Cập nhật danh mục
        } else if ("delete".equals(action)) {
            deleteCategory(req, resp); // Xóa danh mục
        }
    }

    // Thêm danh mục mới
    private void addCategory(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            Category category = new Category();
            category.setName(req.getParameter("name"));
            category.setDescription(req.getParameter("description"));
            category.setImage(req.getParameter("image")); // Có thể là tên file ảnh đã được upload trước đó
            category.setActive("on".equals(req.getParameter("active"))); // Checkbox có giá trị "on"

            // Thêm vào CSDL
            if (categoryDAO.addCategory(category)) {
                req.getSession().setAttribute("message", "Đã thêm danh mục thành công!");
            } else {
                req.getSession().setAttribute("error", "Không thể thêm danh mục!");
            }
        } catch (Exception e) {
            req.getSession().setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }

        // Sau khi xử lý, chuyển hướng lại danh sách danh mục
        resp.sendRedirect("category?action=list");
    }

    // Cập nhật danh mục
    private void updateCategory(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            Category category = new Category();
            category.setId(Integer.parseInt(req.getParameter("id"))); // Lấy ID để cập nhật
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

    // Xóa danh mục
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
