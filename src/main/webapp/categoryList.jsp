<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.model.Category" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh mục sản phẩm</title>
    <link rel="stylesheet" href="css/style.css?v=<%= System.currentTimeMillis() %>">
    <style>
        .category-grid {
            display: grid;
            grid-template-columns: repeat(3, 1fr);
            gap: 20px;
            margin-top: 20px;
        }
        .category-card {
            border: 1px solid #ddd;
            border-radius: 10px;
            padding: 10px;
            text-align: center;
            background-color: #fff;
            transition: transform 0.2s;
        }
        .category-card:hover {
            transform: scale(1.03);
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .category-card img {
            max-width: 100%;
            height: 120px;
            object-fit: cover;
            border-radius: 6px;
        }
        .category-card a {
            text-decoration: none;
            color: #333;
            display: block;
            font-size: 16px;
            font-weight: bold;
            margin-top: 10px;
        }
    </style>
</head>
<body>

<header>
    <div class="container">
        <h1>📂 Danh mục sản phẩm</h1>
        <p>Chọn danh mục để xem sản phẩm theo loại</p>
    </div>
</header>

<nav>
    <a href="product">🏠 Trang chủ</a>
    <a href="cart">🛒 Giỏ hàng</a>
</nav>

<div class="container">
    <%
        @SuppressWarnings("unchecked")
        List<Category> categories = (List<Category>) request.getAttribute("categories");
        if (categories != null && !categories.isEmpty()) {
    %>
        <div class="category-grid">
            <% for (Category category : categories) { %>
                <div class="category-card">
                    <a href="product?categoryId=<%= category.getId() %>">
                        <img src="images/<%= category.getImage() != null ? category.getImage() : "default-category.jpg" %>" alt="<%= category.getName() %>">
                        <%= category.getName() %>
                    </a>
                </div>
            <% } %>
        </div>
    <%
        } else {
    %>
        <div class="error-message">
            ❌ Không có danh mục nào!
        </div>
    <%
        }
    %>
</div>

<footer>
    <p>&copy; 2024 PhoneStore</p>
</footer>

</body>
</html>
