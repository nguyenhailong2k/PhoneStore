<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.example.model.Product" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản trị - PhoneStore</title>
    <link rel="stylesheet" href="css/style.css?v=<%= System.currentTimeMillis() %>">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <header>
        <div class="container">
            <h1>🛠️ Quản trị PhoneStore</h1>
            <p>Chào mừng, <%= session.getAttribute("user") %>!</p>
        </div>
    </header>
    
    <nav>
        <a href="admin">Quản lý sản phẩm</a>
        <a href="add">Thêm sản phẩm</a>
        <a href="product">Xem website</a>
        <a href="logout">Đăng xuất</a>
    </nav>
    
    <div class="container">
        <% 
            String message = (String) session.getAttribute("message");
            String error = (String) session.getAttribute("error");
            if (message != null) {
                session.removeAttribute("message");
        %>
            <div class="success-message">✅ <%= message %></div>
        <%
            }
            if (error != null) {
                session.removeAttribute("error");
        %>
        <% 
        	if (session.getAttribute("admin") != null) {
     			response.sendRedirect("admin.jsp");
   			}
		%>
            <div class="error-message">❌ <%= error %></div>
        <% } %>
        
        <div class="admin-actions">
            <a href="add" class="btn btn-primary">➕ Thêm sản phẩm mới</a>
        </div>
        <h3 class="product-list-title">Danh sách sản phẩm</h3>
        <div class="admin-table-wrapper">
            <table class="admin-table" style="overflow-x: auto;">
                <thead>
                        <tr>
  							<th>ID</th>
  							<th>Hình ảnh</th>
  							<th>Tên sản phẩm</th>
  							<th>Giá</th>
  							<th>Tồn kho</th>
  							<th>Thao tác</th>
						</tr>
                </thead>
                <tbody>
                    <%
                        @SuppressWarnings("unchecked")
                        List<Product> products = (List<Product>) request.getAttribute("products");
                        if (products != null && !products.isEmpty()) {
                            for (Product product : products) {
                    %>
                        <tr>
                            <td><%= product.getId() %></td>
                            <td>
                                <% if (product.getImage() != null && !product.getImage().isEmpty()) { %>
                                    <img src="images/<%= product.getImage() %>" alt="<%= product.getName() %>" class="admin-product-image"/>
                                <% } else { %>
                                    <span>Không có ảnh</span>
                                <% } %>
                            </td>
                            <td><%= product.getName() %></td>
                            <td><%= String.format("%,.0f", product.getPrice()) %> VND</td>
                            <% System.out.println("ADMIN.JSP - Tồn kho: " + product.getStockQuantity()); %>
							<td><%= product.getStockQuantity() %></td>

                            <td>
                                <div class="action-buttons">
                                    <a href="edit?id=<%= product.getId() %>" class="btn btn-edit">✏️ Sửa</a>
                                    <a href="delete?id=<%= product.getId() %>" 
                                       onclick="return confirm('Bạn có chắc chắn muốn xóa sản phẩm này?')" 
                                       class="btn btn-delete">🗑️ Xóa</a>
                                </div>
                            </td>
                        </tr>
                    <%
                            }
                        } else {
                    %>
                        <tr>
                            <td colspan="5">Chưa có sản phẩm nào.</td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
            </div>
        </div>

    
    <footer>
        <p>&copy; 2024 PhoneStore Admin Panel. All rights reserved.</p>
    </footer>
</body>
</html>
