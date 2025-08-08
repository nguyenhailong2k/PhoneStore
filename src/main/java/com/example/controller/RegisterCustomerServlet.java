package com.example.controller;

// Import các lớp cần thiết
import com.example.dao.CustomerDAO;
import com.example.model.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

// Định nghĩa servlet xử lý yêu cầu tại đường dẫn "/registerCustomer"
@WebServlet("/registerCustomer")
public class RegisterCustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // DAO để thao tác với dữ liệu khách hàng
    private CustomerDAO customerDAO;

    // Khởi tạo DAO khi servlet được load lần đầu
    @Override
    public void init() {
        customerDAO = new CustomerDAO();
    }

    // Xử lý khi người dùng gửi biểu mẫu đăng ký bằng POST
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Lấy dữ liệu từ form đăng ký
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");

        // Kiểm tra email đã tồn tại trong hệ thống chưa
        if (customerDAO.isEmailExists(email)) {
            // Nếu tồn tại, thông báo lỗi và chuyển về trang đăng ký
            req.setAttribute("registerError", "Email đã tồn tại!");
            req.getRequestDispatcher("index.jsp").forward(req, resp);
            return;
        }

        // Tạo đối tượng Customer mới từ dữ liệu người dùng nhập
        Customer newCustomer = new Customer();
        newCustomer.setName(name);
        newCustomer.setEmail(email);
        newCustomer.setPassword(password);
        newCustomer.setPhone(phone);
        newCustomer.setAddress(address);

        // Lưu khách hàng mới vào cơ sở dữ liệu
        customerDAO.addCustomer(newCustomer);

        // Tự động đăng nhập ngay sau khi đăng ký thành công
        HttpSession session = req.getSession();
        session.setAttribute("customer", newCustomer);

        // Chuyển hướng đến trang sản phẩm (trang chủ)
        resp.sendRedirect("product");
    }
}
