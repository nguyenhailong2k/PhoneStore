package com.example.model;

import java.util.Date;

public class Review {
    private int id;
    private int productId;
    private String customerName;
    private String email;
    private int rating;
    private String comment;
    private Date reviewDate;
    private boolean approved;
    
    public Review() {}
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }
    
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }
    
    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }
    
    public Date getReviewDate() { return reviewDate; }
    public void setReviewDate(Date reviewDate) { this.reviewDate = reviewDate; }
    
    public boolean isApproved() { return approved; }
    public void setApproved(boolean approved) { this.approved = approved; }
}