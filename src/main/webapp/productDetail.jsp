<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.model.Product" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chi tiết sản phẩm</title>
    <link rel="stylesheet" href="css/style.css?v=<%= System.currentTimeMillis() %>">

</head>
<body>

<header>
    <div class="container">
        <h1>🔍 Chi tiết sản phẩm</h1>
    </div>
</header>

<nav>
    <a href="product">🏠 Trang chủ</a>
    <a href="cart">🛒 Giỏ hàng</a>
</nav>

<div class="container">
    <%
        Product p = (Product) request.getAttribute("product");
        if (p != null) {
    %>
    <div class="product-detail">
        <img src="images/<%= p.getImage() %>" alt="<%= p.getName() %>" class="product-image-large">
        <div class="product-info">
            <h2><%= p.getName() %></h2>
            <p class="price">💰 <%= String.format("%,.0f", p.getPrice()) %> VND</p>
            <p><strong>Thương hiệu:</strong> <%= p.getBrand() %></p>
            <p><strong>Thông số kỹ thuật:</strong><br><%= p.getSpecs() %></p>
            <p><strong>Mô tả:</strong><br><%= p.getDescription() %></p>
            <p><strong>Số lượng còn lại:</strong> <%= p.getStockQuantity() %></p>

            <form action="cart" method="post">
                <input type="hidden" name="action" value="add">
                <input type="hidden" name="productId" value="<%= p.getId() %>">
                <input type="hidden" name="quantity" value="1">
                <button type="submit" class="btn btn-primary">🛒 Thêm vào giỏ</button>
            </form>
        </div>
    </div>
    <% } else { %>
    <div class="error-message">
        ❌ Không tìm thấy sản phẩm!
    </div>
    <% } %>
</div>

<footer>
    <p>&copy; 2024 PhoneStore</p>
</footer>

</body>
</html>
