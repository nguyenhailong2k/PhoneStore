// Khai báo package controller
package com.example.controller;

// Import các lớp cần thiết
import com.example.dao.CustomerDAO;
import com.example.model.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

// Servlet xử lý đăng nhập cho khách hàng với URL mapping là "/loginCustomer"
@WebServlet("/loginCustomer")
public class LoginCustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Đối tượng DAO để thao tác với dữ liệu khách hàng
    private CustomerDAO customerDAO;

    // Phương thức khởi tạo servlet, được gọi 1 lần khi servlet được load
    @Override
    public void init() {
        customerDAO = new CustomerDAO(); // Khởi tạo đối tượng DAO
    }

    // Xử lý khi có request POST gửi đến servlet
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        // Lấy email và mật khẩu từ request (gửi từ form đăng nhập)
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        // Gọi DAO để kiểm tra đăng nhập với email và mật khẩu
        Customer customer = customerDAO.checkLogin(email, password);

        if (customer != null) {
            // Nếu đăng nhập thành công, tạo phiên làm việc (session)
            HttpSession session = req.getSession();
            session.setAttribute("customer", customer); // Lưu thông tin khách hàng vào session

            // Chuyển hướng sang trang danh sách sản phẩm
            resp.sendRedirect("product");
        } else {
            // Nếu đăng nhập thất bại, gửi thông báo lỗi về trang đăng nhập
            req.setAttribute("loginError", "Sai email hoặc mật khẩu!");
            req.getRequestDispatcher("index.jsp").forward(req, resp); // Giữ nguyên trang và hiện lỗi
        }
    }
}
