<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập Admin - PhoneStore</title>
    <link rel="stylesheet" href="css/style.css?v=<%= System.currentTimeMillis() %>">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <div class="login-container">
        <div class="login-form">
            <h2>🔐 Đăng nhập Admin</h2>
            
            <% if (request.getParameter("error") != null) { %>
                <div class="error-message">
                    ❌ Tên đăng nhập hoặc mật khẩu không đúng!
                </div>
            <% } %>
            
            <form action="login" method="post">
                <div class="form-group">
                    <label for="username">Tên đăng nhập:</label>
                    <input type="text" id="username" name="username" required 
                           placeholder="Nhập tên đăng nhập"/>
                </div>
                
                <div class="form-group">
                    <label for="password">Mật khẩu:</label>
                    <input type="password" id="password" name="password" required 
                           placeholder="Nhập mật khẩu"/>
                </div>
                
                <button type="submit">Đăng nhập</button>
            </form>
            
            <div class="back-link">
                <a href="product">← Quay lại trang chủ</a>
            </div>
        </div>
    </div>
</body>
</html>