// Khai báo package controller
package com.example.controller;

// Import các lớp cần thiết
import com.example.dao.AdminDAO;
import com.example.model.Admin;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

// Servlet xử lý đăng nhập admin, ánh xạ với URL "/login"
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Đối tượng DAO để thao tác với dữ liệu admin
    private AdminDAO adminDAO;
    
    // Phương thức khởi tạo servlet, được gọi khi servlet được load lần đầu
    @Override
    public void init() {
        adminDAO = new AdminDAO(); // Khởi tạo đối tượng DAO để truy cập cơ sở dữ liệu
    }
    
    // Xử lý các yêu cầu POST (từ form đăng nhập gửi lên)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        // Lấy dữ liệu từ form đăng nhập (username và password)
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        
        // Kiểm tra thông tin đăng nhập bằng DAO
        Admin admin = adminDAO.validateAdmin(username, password);
        
        if (admin != null) {
            // Nếu đăng nhập thành công, tạo session lưu thông tin người dùng
            HttpSession session = req.getSession();
            session.setAttribute("user", admin.getUsername()); // Lưu tên đăng nhập
            session.setAttribute("admin", admin);               // Lưu đối tượng Admin

            // Chuyển hướng đến trang admin (admin.jsp hoặc servlet)
            resp.sendRedirect("admin");
        } else {
            // Nếu đăng nhập thất bại, chuyển về login.jsp với tham số lỗi
            resp.sendRedirect("login.jsp?error=1");
        }
    }
}
