<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.example.model.Review" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đánh giá sản phẩm - PhoneStore</title>
    <link rel="stylesheet" href="css/style.css?v=<%= System.currentTimeMillis() %>">

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <header>
        <div class="container">
            <h1>⭐ Đánh giá sản phẩm</h1>
            <p>Xem các đánh giá từ khách hàng</p>
        </div>
    </header>
    
    <nav>
        <a href="product">Trang chủ</a>
        <a href="category?action=list">Danh mục</a>
        <a href="cart">Giỏ hàng</a>
        <a href="contact.jsp">Liên hệ</a>
    </nav>
    
    <div class="container">
        <%
            @SuppressWarnings("unchecked")
            List<Review> reviews = (List<Review>) request.getAttribute("reviews");
            Integer productId = (Integer) request.getAttribute("productId");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        %>
        
        <div class="reviews-header">
            <a href="product?action=view&id=<%= productId %>" class="btn btn-secondary">← Quay lại sản phẩm</a>
            
            <div class="add-review-btn">
                <button onclick="showReviewForm()" class="btn btn-primary">✍️ Viết đánh giá</button>
            </div>
        </div>
        
        <!-- Form thêm đánh giá -->
        <div id="reviewForm" class="review-form" style="display: none;">
            <h3>Viết đánh giá của bạn</h3>
            <form method="post" action="review">
                <input type="hidden" name="action" value="add"/>
                <input type="hidden" name="productId" value="<%= productId %>"/>
                
                <div class="form-group">
                    <label for="customerName">Họ và tên *</label>
                    <input type="text" id="customerName" name="customerName" required/>
                </div>
                
                <div class="form-group">
                    <label for="email">Email *</label>
                    <input type="email" id="email" name="email" required/>
                </div>
                
                <div class="form-group">
                    <label>Đánh giá *</label>
                    <div class="rating-input">
                        <input type="radio" id="star5" name="rating" value="5"/>
                        <label for="star5">⭐⭐⭐⭐⭐</label>
                        
                        <input type="radio" id="star4" name="rating" value="4"/>
                        <label for="star4">⭐⭐⭐⭐</label>
                        
                        <input type="radio" id="star3" name="rating" value="3"/>
                        <label for="star3">⭐⭐⭐</label>
                        
                        <input type="radio" id="star2" name="rating" value="2"/>
                        <label for="star2">⭐⭐</label>
                        
                        <input type="radio" id="star1" name="rating" value="1"/>
                        <label for="star1">⭐</label>
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="comment">Bình luận *</label>
                    <textarea id="comment" name="comment" rows="4" required 
                              placeholder="Chia sẻ trải nghiệm của bạn về sản phẩm..."></textarea>
                </div>
                
                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">Gửi đánh giá</button>
                    <button type="button" onclick="hideReviewForm()" class="btn btn-secondary">Hủy</button>
                </div>
            </form>
        </div>
        
        <!-- Danh sách đánh giá -->
        <div class="reviews-list">
            <% if (reviews != null && !reviews.isEmpty()) { %>
                <h3>Đánh giá từ khách hàng (<%= reviews.size() %> đánh giá)</h3>
                
                <% for (Review review : reviews) { %>
                    <div class="review-item">
                        <div class="review-header">
                            <div class="reviewer-info">
                                <span class="reviewer-name"><%= review.getCustomerName() %></span>
                                <span class="review-date"><%= sdf.format(review.getReviewDate()) %></span>
                            </div>
                            
                            <div class="review-rating">
                                <% for (int i = 1; i <= 5; i++) { %>
                                    <% if (i <= review.getRating()) { %>
                                        <span class="star filled">⭐</span>
                                    <% } else { %>
                                        <span class="star">☆</span>
                                    <% } %>
                                <% } %>
                                <span class="rating-text">(<%= review.getRating() %>/5)</span>
                            </div>
                        </div>
                        
                        <div class="review-content">
                            <p><%= review.getComment() %></p>
                        </div>
                    </div>
                <% } %>
            <% } else { %>
                <div class="no-reviews">
                    <h3>Chưa có đánh giá nào</h3>
                    <p>Hãy là người đầu tiên đánh giá sản phẩm này!</p>
                </div>
            <% } %>
        </div>
    </div>
    
    <footer>
        <div class="container">
            <p>&copy; 2024 PhoneStore. All rights reserved.</p>
        </div>
    </footer>
    
    <script>
        function showReviewForm() {
            document.getElementById('reviewForm').style.display = 'block';
            document.getElementById('reviewForm').scrollIntoView({ behavior: 'smooth' });
        }
        
        function hideReviewForm() {
            document.getElementById('reviewForm').style.display = 'none';
        }
    </script>
</body>
</html>