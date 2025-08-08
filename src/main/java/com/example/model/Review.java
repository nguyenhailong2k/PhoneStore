package com.example.model;

import java.util.Date;

/**
 * Lớp Review đại diện cho một đánh giá (review) của khách hàng về sản phẩm.
 */
public class Review {
    // Mã định danh duy nhất cho review
    private int id;

    // Mã sản phẩm được đánh giá
    private int productId;

    // Tên khách hàng viết đánh giá
    private String customerName;

    // Email của khách hàng
    private String email;

    // Số sao đánh giá (rating), thường từ 1 đến 5
    private int rating;

    // Nội dung nhận xét, bình luận của khách hàng
    private String comment;

    // Ngày đánh giá được viết
    private Date reviewDate;

    // Trạng thái phê duyệt của đánh giá (true: đã duyệt, false: chưa duyệt)
    private boolean approved;

    /**
     * Constructor mặc định
     */
    public Review() {}

    // Getter và Setter cho id
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    // Getter và Setter cho productId
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    // Getter và Setter cho customerName
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    // Getter và Setter cho email
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    // Getter và Setter cho rating
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    // Getter và Setter cho comment
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    // Getter và Setter cho reviewDate
    public Date getReviewDate() { return reviewDate; }
    public void setReviewDate(Date reviewDate) { this.reviewDate = reviewDate; }

    // Getter và Setter cho approved
    public boolean isApproved() { return approved; }
    public void setApproved(boolean approved) { this.approved = approved; }
}
