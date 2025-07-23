<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.model.Brand" %>
<%@ page import="com.example.model.Category" %>
<%@ page import="com.example.dao.BrandDAO" %>
<%@ page import="com.example.dao.CategoryDAO" %>
<%
    BrandDAO brandDAO = new BrandDAO();
    List<String> brands = brandDAO.getAllBrandNames();

    CategoryDAO categoryDAO = new CategoryDAO();
    List<Category> categories = categoryDAO.getAllCategories();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thêm sản phẩm mới</title>
    <link rel="stylesheet" href="css/style.css?v=<%= System.currentTimeMillis() %>">
    <style>
        .form-group { margin-bottom: 1rem; }
        label { display: block; font-weight: bold; margin-top: 10px; }
        input, textarea, select { width: 100%; padding: 8px; }
        textarea { resize: vertical; }
    </style>
</head>
<body>
<div class="container">
    <h2>➕ Thêm sản phẩm mới</h2>
    <form action="add" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label for="name">Tên sản phẩm:</label>
            <input type="text" name="name" id="name" required>
        </div>

        <div class="form-group">
            <label for="price">Giá (VND):</label>
            <input type="text" name="price" id="price" required placeholder="Ví dụ: 25000000">
        </div>

        <div class="form-group">
            <label for="stock">Số lượng tồn kho:</label>
            <input type="number" name="stock" id="stock" min="0" required>
        </div>

        <div class="form-group">
            <label for="categoryId">Danh mục:</label>
            <select name="categoryId" id="categoryId" required>
                <option value="">-- Chọn danh mục --</option>
                <% for (Category c : categories) { %>
                    <option value="<%= c.getId() %>"><%= c.getName() %></option>
                <% } %>
            </select>
        </div>

        <div class="form-group">
            <label for="brand">Hãng sản xuất:</label>
            <select name="brand" id="brand" required>
                <option value="">-- Chọn hãng --</option>
                <% for (String b : brands) { %>
                    <option value="<%= b %>"><%= b %></option>
                <% } %>
            </select>
            <input type="text" name="brandNew" placeholder="Hoặc nhập hãng mới nếu chưa có">
        </div>

        <div class="form-group">
            <label for="description">Mô tả sản phẩm:</label>
            <textarea name="description" id="description" rows="4" required></textarea>
        </div>

        <div class="form-group">
            <label for="specs">Thông số kỹ thuật:</label>
            <textarea name="specs" id="specs" rows="4" required></textarea>
        </div>

        <div class="form-group">
            <label for="image">Hình ảnh:</label>
            <input type="file" name="image" id="image" accept="image/*" required>
        </div>

        <button type="submit">✅ Thêm sản phẩm</button>
        <a href="admin">❌ Hủy</a>
    </form>
</div>
</body>
</html>