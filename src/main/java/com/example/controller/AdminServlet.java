// Khai báo package và các thư viện cần thiết
package com.example.controller;

import com.example.dao.ProductDAO;
import com.example.model.Product;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

// Servlet xử lý trang quản trị sản phẩm, ánh xạ URL "/admin"
@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Khai báo DAO để truy xuất dữ liệu sản phẩm từ CSDL
    private ProductDAO productDAO;
    
    // Hàm khởi tạo servlet, chạy khi servlet được load lần đầu
    @Override
    public void init() {
        productDAO = new ProductDAO();  // Khởi tạo DAO sản phẩm
    }
    
    // Xử lý yêu cầu GET (khi truy cập trang admin)
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        // Lấy danh sách tất cả sản phẩm từ CSDL
        List<Product> products = productDAO.getAllProducts();

        // Đưa danh sách sản phẩm vào request để hiển thị trên JSP
        req.setAttribute("products", products);

        // Chuyển tiếp yêu cầu tới trang admin.jsp để hiển thị sản phẩm
        req.getRequestDispatcher("admin.jsp").forward(req, resp);
    }
    
    // Xử lý yêu cầu POST (nếu có), chuyển về xử lý như GET
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);  // Gọi lại doGet để tái sử dụng logic
    }
}
