package com.example.dao;

import com.example.model.Review;
import com.example.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Lớp ReviewDAO xử lý các thao tác với bảng `reviews` trong CSDL
 */
public class ReviewDAO {

    /**
     * Thêm đánh giá mới vào CSDL
     * @param review đối tượng Review chứa thông tin đánh giá
     * @return true nếu thêm thành công, ngược lại false
     */
    public boolean addReview(Review review) {
        String sql = "INSERT INTO reviews(product_id, customer_name, email, rating, comment) VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Gán giá trị cho các tham số trong câu lệnh SQL
            ps.setInt(1, review.getProductId());
            ps.setString(2, review.getCustomerName());
            ps.setString(3, review.getEmail());
            ps.setInt(4, review.getRating());
            ps.setString(5, review.getComment());

            // Thực hiện câu lệnh và trả về kết quả
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Lấy danh sách các đánh giá đã được duyệt của 1 sản phẩm
     * @param productId ID của sản phẩm
     * @return danh sách các đánh giá
     */
    public List<Review> getReviewsByProduct(int productId) {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM reviews WHERE product_id = ? AND approved = 1 ORDER BY review_date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            // Duyệt qua kết quả và tạo danh sách Review
            while (rs.next()) {
                Review review = new Review();
                review.setId(rs.getInt("id"));
                review.setProductId(rs.getInt("product_id"));
                review.setCustomerName(rs.getString("customer_name"));
                review.setEmail(rs.getString("email"));
                review.setRating(rs.getInt("rating"));
                review.setComment(rs.getString("comment"));
                review.setReviewDate(rs.getTimestamp("review_date"));
                review.setApproved(rs.getBoolean("approved"));
                reviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    /**
     * Lấy tất cả đánh giá (kể cả chưa được duyệt), dùng cho admin
     * @return danh sách tất cả các đánh giá
     */
    public List<Review> getAllReviews() {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT r.*, p.name as product_name FROM reviews r LEFT JOIN products p ON r.product_id = p.id ORDER BY r.review_date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // Duyệt kết quả và thêm vào danh sách
            while (rs.next()) {
                Review review = new Review();
                review.setId(rs.getInt("id"));
                review.setProductId(rs.getInt("product_id"));
                review.setCustomerName(rs.getString("customer_name"));
                review.setEmail(rs.getString("email"));
                review.setRating(rs.getInt("rating"));
                review.setComment(rs.getString("comment"));
                review.setReviewDate(rs.getTimestamp("review_date"));
                review.setApproved(rs.getBoolean("approved"));
                reviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    /**
     * Duyệt đánh giá (approved = 1)
     * @param reviewId ID của đánh giá cần duyệt
     * @return true nếu thành công, ngược lại false
     */
    public boolean approveReview(int reviewId) {
        String sql = "UPDATE reviews SET approved = 1 WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, reviewId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Xóa một đánh giá khỏi CSDL
     * @param reviewId ID của đánh giá cần xóa
     * @return true nếu xóa thành công, ngược lại false
     */
    public boolean deleteReview(int reviewId) {
        String sql = "DELETE FROM reviews WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, reviewId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Tính trung bình số sao của các đánh giá đã được duyệt cho sản phẩm
     * @param productId ID của sản phẩm
     * @return giá trị trung bình số sao (rating)
     */
    public double getAverageRating(int productId) {
        String sql = "SELECT AVG(rating) as avg_rating FROM reviews WHERE product_id = ? AND approved = 1";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble("avg_rating");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    /**
     * Đếm số lượng đánh giá đã được duyệt của một sản phẩm
     * @param productId ID của sản phẩm
     * @return số lượng đánh giá
     */
    public int getReviewCount(int productId) {
        String sql = "SELECT COUNT(*) as count FROM reviews WHERE product_id = ? AND approved = 1";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
