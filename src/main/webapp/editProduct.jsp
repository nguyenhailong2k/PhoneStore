<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.model.Product" %>
<%@ page import="com.example.model.Brand" %>
<%@ page import="com.example.model.Category" %>
<%@ page import="com.example.dao.BrandDAO" %>
<%@ page import="com.example.dao.CategoryDAO" %>
<%@ page import="java.util.List" %>
<%
    Product product = (Product) request.getAttribute("product");
    BrandDAO brandDAO = new BrandDAO();
    List<String> brands = brandDAO.getAllBrandNames();

    CategoryDAO categoryDAO = new CategoryDAO();
    List<Category> categories = categoryDAO.getAllCategories();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sửa sản phẩm</title>
    <link rel="stylesheet" href="css/style.css?v=<%= System.currentTimeMillis() %>">
</head>
<body>
<div class="container">
    <h2>✏️ Sửa sản phẩm</h2>
    <% if (product != null) { %>
    <form action="edit" method="post" enctype="multipart/form-data">
        <input type="hidden" name="id" value="<%= product.getId() %>">

        <div class="form-group">
            <label for="name">Tên sản phẩm:</label>
            <input type="text" name="name" id="name" value="<%= product.getName() %>" required>
        </div>

        <div class="form-group">
            <label for="price">Giá (VND):</label>
            <input type="text" name="price" id="price" value="<%= String.format("%,.0f", product.getPrice()) %>" required>
        </div>

        <div class="form-group">
            <label for="stock">Số lượng tồn kho:</label>
            <input type="number" name="stock" id="stock" value="<%= product.getStockQuantity() %>" min="0" required>
        </div>

        <div class="form-group">
            <label for="categoryId">Danh mục:</label>
            <select name="categoryId" id="categoryId" required>
                <% for (Category c : categories) { %>
                    <option value="<%= c.getId() %>" <%= (c.getId() == product.getCategoryId()) ? "selected" : "" %>><%= c.getName() %></option>
                <% } %>
            </select>
        </div>

        <div class="form-group">
            <label for="brand">Hãng sản xuất:</label>
            <select name="brand" id="brand" required>
                <% for (String b : brands) { %>
                    <option value="<%= b %>" <%= b.equals(product.getBrand()) ? "selected" : "" %>><%= b %></option>
                <% } %>
            </select>
            <input type="text" name="brandNew" placeholder="Hoặc nhập hãng mới nếu chưa có">
        </div>

        <div class="form-group">
            <label for="description">Mô tả sản phẩm:</label>
            <textarea name="description" id="description" rows="4" required><%= product.getDescription() %></textarea>
        </div>

        <div class="form-group">
            <label for="specs">Thông số kỹ thuật:</label>
            <textarea name="specs" id="specs" rows="4" required><%= product.getSpecs() %></textarea>
        </div>

        <div class="form-group">
            <label>Hình ảnh hiện tại:</label>
            <% if (product.getImage() != null && !product.getImage().isEmpty()) { %>
                <img src="images/<%= product.getImage() %>" width="150" alt="Ảnh hiện tại">
            <% } else { %>
                <p>Chưa có ảnh</p>
            <% } %>
        </div>

        <div class="form-group">
            <label for="image">Thay đổi hình ảnh (tùy chọn):</label>
            <input type="file" name="image" id="image" accept="image/*">
        </div>

        <button type="submit">✅ Cập nhật</button>
        <a href="admin">❌ Hủy</a>
    </form>
    <% } else { %>
    <p>❌ Không tìm thấy sản phẩm!</p>
    <a href="admin">← Quay lại</a>
    <% } %>
</div>
</body>
</html>
