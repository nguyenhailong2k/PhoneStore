// Khai báo package chứa lớp controller
package com.example.controller;

// Import các lớp cần thiết
import com.example.dao.OrderDAO;
import com.example.model.Customer;
import com.example.model.Order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

// Servlet xử lý yêu cầu tại đường dẫn /orders
@WebServlet("/orders")
public class OrderListServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;
    private OrderDAO orderDAO;

    // Khởi tạo OrderDAO khi servlet được load
    @Override
    public void init() {
        orderDAO = new OrderDAO();
    }

    // Xử lý yêu cầu GET để hiển thị danh sách đơn hàng
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Lấy phiên làm việc (session) hiện tại
        HttpSession session = req.getSession();

        // Lấy thông tin người dùng đã đăng nhập từ session (có thể là customer hoặc admin)
        Object userObj = session.getAttribute("customer");
        List<Order> orders;

        // Nếu chưa đăng nhập thì chuyển hướng về trang chủ
        if (userObj == null) {
            resp.sendRedirect("index.jsp");
            return;
        }

        // Kiểm tra xem người dùng là một khách hàng hay không
        if (userObj instanceof Customer) {
            Customer customer = (Customer) userObj;

            // Nếu là admin (đăng nhập bằng email 'admin'), lấy toàn bộ đơn hàng
            if ("admin".equalsIgnoreCase(customer.getEmail())) {
                orders = orderDAO.getAllOrders(); // Admin xem tất cả đơn
            } else {
                // Nếu là khách hàng bình thường, chỉ lấy đơn theo email của họ
                orders = orderDAO.getOrdersByEmail(customer.getEmail());
            }

            // Gửi danh sách đơn hàng tới trang hiển thị
            req.setAttribute("orders", orders);
            req.getRequestDispatcher("orderList.jsp").forward(req, resp);
        } else {
            // Nếu không phải đối tượng Customer (dữ liệu sai), chuyển hướng về trang chủ
            resp.sendRedirect("index.jsp");
        }
    }
}
