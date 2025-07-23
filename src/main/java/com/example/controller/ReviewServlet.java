package com.example.controller;

import com.example.dao.ReviewDAO;
import com.example.model.Review;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/review")
public class ReviewServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ReviewDAO reviewDAO;
    
    @Override
    public void init() {
        reviewDAO = new ReviewDAO();
    }
    
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        String action = req.getParameter("action");
        
        if ("list".equals(action)) {
            List<Review> reviews = reviewDAO.getAllReviews();
            req.setAttribute("reviews", reviews);
            req.getRequestDispatcher("reviewList.jsp").forward(req, resp);
        } else if ("product".equals(action)) {
            int productId = Integer.parseInt(req.getParameter("productId"));
            List<Review> reviews = reviewDAO.getReviewsByProduct(productId);
            req.setAttribute("reviews", reviews);
            req.setAttribute("productId", productId);
            req.getRequestDispatcher("productReviews.jsp").forward(req, resp);
        }
    }
    
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        String action = req.getParameter("action");
        
        if ("add".equals(action)) {
            addReview(req, resp);
        } else if ("approve".equals(action)) {
            approveReview(req, resp);
        } else if ("delete".equals(action)) {
            deleteReview(req, resp);
        }
    }
    
    private void addReview(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        try {
            Review review = new Review();
            review.setProductId(Integer.parseInt(req.getParameter("productId")));
            review.setCustomerName(req.getParameter("customerName"));
            review.setEmail(req.getParameter("email"));
            review.setRating(Integer.parseInt(req.getParameter("rating")));
            review.setComment(req.getParameter("comment"));
            
            if (reviewDAO.addReview(review)) {
                req.getSession().setAttribute("message", "Cảm ơn bạn đã đánh giá! Bình luận sẽ được kiểm duyệt trước khi hiển thị.");
            } else {
                req.getSession().setAttribute("error", "Có lỗi xảy ra khi gửi đánh giá!");
            }
        } catch (Exception e) {
            req.getSession().setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }
        
        String productId = req.getParameter("productId");
        resp.sendRedirect("product?action=view&id=" + productId);
    }
    
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
        
        resp.sendRedirect("review?action=list");
    }
    
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
        
        resp.sendRedirect("review?action=list");
    }
}