<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng ký tài khoản</title>
    <link rel="stylesheet" href="css/style.css?v=<%= System.currentTimeMillis() %>">

</head>
<body>
<div class="container">
    <h2>📝 Đăng ký tài khoản</h2>
    <% if (request.getParameter("error") != null) { %>
        <div class="error-message">❌ Email đã được sử dụng hoặc lỗi hệ thống!</div>
    <% } %>

    <form action="customer" method="post">
        <input type="hidden" name="action" value="register">
        <div class="form-group">
            <label>Họ tên:</label>
            <input type="text" name="name" required>
        </div>
        <div class="form-group">
            <label>Email:</label>
            <input type="email" name="email" required>
        </div>
        <div class="form-group">
            <label>Mật khẩu:</label>
            <input type="password" name="password" required>
        </div>
        <div class="form-group">
            <label>Điện thoại:</label>
            <input type="text" name="phone">
        </div>
        <div class="form-group">
            <label>Địa chỉ:</label>
            <textarea name="address" rows="3"></textarea>
        </div>
        <button type="submit" class="btn btn-primary">✅ Đăng ký</button>
    </form>
    <p>Đã có tài khoản? <a href="loginCustomer.jsp">Đăng nhập</a></p>
</div>
</body>
</html>
