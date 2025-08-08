package com.example.controller;

import com.example.dao.ReviewDAO;
import com.example.model.Review;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

// Đăng ký servlet với đường dẫn /review
@WebServlet("/review")
public class ReviewServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // DAO để thao tác với bảng đánh giá trong CSDL
    private ReviewDAO reviewDAO;

    // Phương thức khởi tạo servlet, khởi tạo đối tượng DAO
    @Override
    public void init() {
        reviewDAO = new ReviewDAO();
    }

    /**
     * Xử lý yêu cầu GET từ client
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        String action = req.getParameter("action");

        // Nếu yêu cầu là "list" -> hiển thị danh sách tất cả đánh giá
        if ("list".equals(action)) {
            List<Review> reviews = reviewDAO.getAllReviews();
            req.setAttribute("reviews", reviews);
            req.getRequestDispatcher("reviewList.jsp").forward(req, resp);

        // Nếu yêu cầu là "product" -> hiển thị đánh giá của sản phẩm cụ thể
        } else if ("product".equals(action)) {
            int productId = Integer.parseInt(req.getParameter("productId"));
            List<Review> reviews = reviewDAO.getReviewsByProduct(productId);
            req.setAttribute("reviews", reviews);
            req.setAttribute("productId", productId);
            req.getRequestDispatcher("productReviews.jsp").forward(req, resp);
        }
    }

    /**
     * Xử lý yêu cầu POST từ client
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        String action = req.getParameter("action");

        // Thêm đánh giá mới
        if ("add".equals(action)) {
            addReview(req, resp);

        // Duyệt đánh giá (admin)
        } else if ("approve".equals(action)) {
            approveReview(req, resp);

        // Xóa đánh giá
        } else if ("delete".equals(action)) {
            deleteReview(req, resp);
        }
    }

    /**
     * Thêm đánh giá mới từ người dùng
     */
    private void addReview(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        try {
            // Tạo đối tượng Review từ các tham số trên form
            Review review = new Review();
            review.setProductId(Integer.parseInt(req.getParameter("productId")));
            review.setCustomerName(req.getParameter("customerName"));
            review.setEmail(req.getParameter("email"));
            review.setRating(Integer.parseInt(req.getParameter("rating")));
            review.setComment(req.getParameter("comment"));

            // Gọi DAO để thêm đánh giá
            if (reviewDAO.addReview(review)) {
                req.getSession().setAttribute("message", "Cảm ơn bạn đã đánh giá! Bình luận sẽ được kiểm duyệt trước khi hiển thị.");
            } else {
                req.getSession().setAttribute("error", "Có lỗi xảy ra khi gửi đánh giá!");
            }
        } catch (Exception e) {
            req.getSession().setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }

        // Quay lại trang chi tiết sản phẩm sau khi đánh giá
        String productId = req.getParameter("productId");
        resp.sendRedirect("product?action=view&id=" + productId);
    }

    /**
     * Duyệt đánh giá (chức năng dành cho admin)
     */
    private void approveReview(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        try {
            int reviewId = Integer.parseInt(req.getParameter("reviewId"));

            if (reviewDAO.approveReview(reviewId)) {
                req.getSession().setAttribute("message", "Đã duyệt bình luận!");
            } else {
                req.getSession().setAttribute("error", "Không thể duyệt bình luận!");
            }
        } catch (Exception e) {
            req.getSession().setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }

        // Quay lại danh sách đánh giá
        resp.sendRedirect("review?action=list");
    }

    /**
     * Xóa đánh giá (chức năng dành cho admin)
     */
    private void deleteReview(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        try {
            int reviewId = Integer.parseInt(req.getParameter("reviewId"));

            if (reviewDAO.deleteReview(reviewId)) {
                req.getSession().setAttribute("message", "Đã xóa bình luận!");
            } else {
                req.getSession().setAttribute("error", "Không thể xóa bình luận!");
            }
        } catch (Exception e) {
            req.getSession().setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }

        // Quay lại danh sách đánh giá
        resp.sendRedirect("review?action=list");
    }
}
