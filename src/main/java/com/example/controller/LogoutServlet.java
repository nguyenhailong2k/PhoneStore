// Khai báo package chứa lớp controller
package com.example.controller;

// Import các thư viện cần thiết
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

// Servlet xử lý đăng xuất, ánh xạ URL "/logout"
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;

    // Phương thức xử lý yêu cầu GET (thường khi người dùng nhấn nút "Đăng xuất")
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {

        // Lấy session hiện tại (nếu có), không tạo mới nếu chưa tồn tại
        HttpSession session = req.getSession(false);

        // Nếu session tồn tại thì hủy nó (đăng xuất người dùng)
        if (session != null) {
            session.invalidate();  // Hủy toàn bộ session
        }

        // Chuyển hướng người dùng về trang sản phẩm (trang chủ)
        resp.sendRedirect("product");
    }

    // Phương thức xử lý yêu cầu POST, chuyển tiếp xử lý về doGet
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}
