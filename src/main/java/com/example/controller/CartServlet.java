// Khai báo package và các thư viện cần thiết
package com.example.controller;

import com.example.dao.ProductDAO;
import com.example.model.Cart;
import com.example.model.Product;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

// Servlet xử lý các thao tác liên quan đến giỏ hàng, ánh xạ tới URL "/cart"
@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // DAO để thao tác với bảng sản phẩm
    private ProductDAO productDAO;

    // Hàm khởi tạo, chạy khi servlet được load lần đầu
    @Override
    public void init() {
        productDAO = new ProductDAO();
    }

    // Xử lý yêu cầu GET: hiển thị nội dung giỏ hàng
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Lấy session của người dùng
        HttpSession session = req.getSession();

        // Lấy giỏ hàng từ session, nếu chưa có thì tạo mới
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        // Gửi giỏ hàng sang JSP để hiển thị
        req.setAttribute("cart", cart);
        req.getRequestDispatcher("cart.jsp").forward(req, resp);
    }

    // Xử lý yêu cầu POST: thêm, xóa, cập nhật hoặc xóa toàn bộ sản phẩm trong giỏ
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Lấy action từ form gửi lên: add, remove, update, clear
        String action = req.getParameter("action");

        // Lấy giỏ hàng từ session
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        try {
            switch (action) {
                // Thêm sản phẩm vào giỏ
                case "add":
                    int productId = Integer.parseInt(req.getParameter("productId"));
                    int quantity = Integer.parseInt(req.getParameter("quantity"));
                    Product product = productDAO.getProductById(productId);

                    if (product != null) {
                        cart.addItem(product, quantity);
                        session.setAttribute("message", "Đã thêm sản phẩm vào giỏ hàng!");
                    }
                    break;

                // Xóa một sản phẩm khỏi giỏ
                case "remove":
                    int removeId = Integer.parseInt(req.getParameter("productId"));
                    cart.removeItem(removeId);
                    session.setAttribute("message", "Đã xóa sản phẩm khỏi giỏ hàng!");
                    break;

                // Cập nhật số lượng sản phẩm trong giỏ
                case "update":
                    int updateId = Integer.parseInt(req.getParameter("productId"));
                    int newQuantity = Integer.parseInt(req.getParameter("quantity"));
                    cart.updateQuantity(updateId, newQuantity);
                    session.setAttribute("message", "Đã cập nhật số lượng!");
                    break;

                // Xóa toàn bộ sản phẩm khỏi giỏ
                case "clear":
                    cart.clear();
                    session.setAttribute("message", "Đã xóa tất cả sản phẩm trong giỏ hàng!");
                    break;
            }
        } catch (Exception e) {
            // Ghi log lỗi nếu có vấn đề xảy ra
            e.printStackTrace();
            session.setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }

        // Sau khi xử lý, chuyển hướng lại trang giỏ hàng để hiển thị
        resp.sendRedirect("cart");
    }
}
