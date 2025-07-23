<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.model.Cart" %>
<%@ page import="com.example.model.CartItem" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Giỏ hàng - PhoneStore</title>
    <link rel="stylesheet" href="css/style.css?v=<%= System.currentTimeMillis() %>">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <header>
        <div class="container">
            <h1>🛒 Giỏ hàng</h1>
            <p>Xem lại các sản phẩm bạn đã chọn</p>
        </div>
    </header>
    
    <nav>
        <a href="product">Trang chủ</a>
        <a href="cart">Giỏ hàng</a>
        <a href="contact.jsp">Liên hệ</a>
    </nav>
    
    <div class="container">
        <% 
            String message = (String) session.getAttribute("message");
            if (message != null) {
                session.removeAttribute("message");
        %>
            <div class="success-message">✅ <%= message %></div>
        <% } %>
        
        <%
            Cart cart = (Cart) request.getAttribute("cart");
            if (cart != null && !cart.getItems().isEmpty()) {
        %>
        <div class="cart-container">
            <div class="cart-items">
                <h3>Sản phẩm trong giỏ hàng</h3>
                <% for (CartItem item : cart.getItems()) { %>
                    <div class="cart-item">
                        <div class="item-info">
                            <% if (item.getProduct().getImage() != null && !item.getProduct().getImage().isEmpty()) { %>
                                <img src="images/<%= item.getProduct().getImage() %>" alt="<%= item.getProduct().getName() %>"/>
                            <% } %>
                            <div class="item-details">
                                <h4><%= item.getProduct().getName() %></h4>
                                <p class="price"><%= String.format("%,.0f", item.getProduct().getPrice()) %> VND</p>
                            </div>
                        </div>
                        <div class="item-actions">
                            <form method="post" action="cart" class="quantity-form">
                                <input type="hidden" name="action" value="update"/>
                                <input type="hidden" name="productId" value="<%= item.getProduct().getId() %>"/>
                                <input type="number" name="quantity" value="<%= item.getQuantity() %>" min="1" max="10"/>
                                <button type="submit" class="btn btn-sm">Cập nhật</button>
                            </form>
                            <form method="post" action="cart" class="remove-form">
                                <input type="hidden" name="action" value="remove"/>
                                <input type="hidden" name="productId" value="<%= item.getProduct().getId() %>"/>
                                <button type="submit" class="btn btn-delete btn-sm" onclick="return confirm('Xóa sản phẩm này?')">Xóa</button>
                            </form>
                        </div>
                        <div class="item-subtotal">
                            <strong><%= String.format("%,.0f", item.getSubtotal()) %> VND</strong>
                        </div>
                    </div>
                <% } %>
            </div>
            
            <div class="cart-summary">
                <h3>Tổng kết đơn hàng</h3>
                <div class="summary-item">
                    <span>Tổng số lượng:</span>
                    <strong><%= cart.getTotalItems() %> sản phẩm</strong>
                </div>
                <div class="summary-item total">
                    <span>Tổng tiền:</span>
                    <strong><%= String.format("%,.0f", cart.getTotalPrice()) %> VND</strong>
                </div>
                
                <div class="cart-actions">
                    <a href="checkout.jsp" class="btn btn-primary btn-large">🛒 Đặt hàng</a>
                    <form method="post" action="cart" style="display: inline;">
                        <input type="hidden" name="action" value="clear"/>
                        <button type="submit" class="btn btn-secondary" onclick="return confirm('Xóa tất cả sản phẩm?')">Xóa tất cả</button>
                    </form>
                </div>
            </div>
        </div>
        <%
            } else {
        %>
        <div class="empty-cart">
            <h3>Giỏ hàng trống</h3>
            <p>Bạn chưa có sản phẩm nào trong giỏ hàng.</p>
            <a href="product" class="btn btn-primary">Tiếp tục mua sắm</a>
        </div>
        <% } %>
    </div>
    
    <footer>
        <p>&copy; 2024 PhoneStore. All rights reserved.</p>
    </footer>
</body>
</html>