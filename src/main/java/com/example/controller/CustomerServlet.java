// Khai báo package và các thư viện cần thiết
package com.example.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.example.dao.CustomerDAO;
import com.example.model.Customer;

// Servlet xử lý các chức năng liên quan đến khách hàng (đăng ký, đăng nhập)
// Được ánh xạ với URL: /customer
@WebServlet("/customer")
public class CustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // DAO để thao tác với bảng khách hàng trong CSDL
    private CustomerDAO customerDAO;

    // Hàm khởi tạo servlet (gọi 1 lần khi servlet được load)
    @Override
    public void init() {
        customerDAO = new CustomerDAO();
    }

    // Xử lý các yêu cầu POST: đăng ký và đăng nhập
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Lấy tham số action để xác định người dùng đang gửi yêu cầu gì
        String action = req.getParameter("action");

        // ==== XỬ LÝ ĐĂNG KÝ ====
        if ("register".equals(action)) {
            // Tạo đối tượng Customer và gán dữ liệu từ form
            Customer c = new Customer();
            c.setName(req.getParameter("name"));
            c.setEmail(req.getParameter("email"));
            c.setPassword(req.getParameter("password")); // Có thể mã hóa sau
            c.setPhone(req.getParameter("phone"));
            c.setAddress(req.getParameter("address"));

            // Gọi DAO để thực hiện lưu vào CSDL
            boolean success = customerDAO.register(c);

            // Chuyển hướng tới trang phù hợp dựa vào kết quả đăng ký
            resp.sendRedirect(success ? "loginCustomer.jsp?success=1" : "registerCustomer.jsp?error=1");

        // ==== XỬ LÝ ĐĂNG NHẬP ====
        } else if ("login".equals(action)) {
            String email = req.getParameter("email");
            String pass = req.getParameter("password");

            // Gọi DAO để kiểm tra thông tin đăng nhập
            Customer c = customerDAO.login(email, pass);

            if (c != null) {
                // Đăng nhập thành công -> lưu đối tượng customer vào session
                req.getSession().setAttribute("customer", c);
                resp.sendRedirect("product"); // Chuyển về trang sản phẩm
            } else {
                // Đăng nhập thất bại
                resp.sendRedirect("loginCustomer.jsp?error=1");
            }
        }
    }

    // Xử lý GET: dùng để test servlet có hoạt động hay không
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.getWriter().println("CustomerServlet is alive!");
    }
}
