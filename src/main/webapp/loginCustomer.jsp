<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập khách hàng</title>
    <link rel="stylesheet" href="css/style.css?v=<%= System.currentTimeMillis() %>">

</head>
<body>
<div class="container">
    <h2>🔐 Đăng nhập</h2>
    <% if (request.getParameter("error") != null) { %>
        <div class="error-message">❌ Email hoặc mật khẩu không đúng!</div>
    <% } else if (request.getParameter("success") != null) { %>
        <div class="success-message">✅ Đăng ký thành công, mời bạn đăng nhập!</div>
    <% } %>

    <form action="customer" method="post">
        <input type="hidden" name="action" value="login">
        <div class="form-group">
            <label>Email:</label>
            <input type="email" name="email" required>
        </div>
        <div class="form-group">
            <label>Mật khẩu:</label>
            <input type="password" name="password" required>
        </div>
        <button type="submit" class="btn btn-primary">🔓 Đăng nhập</button>
    </form>
    <p>Chưa có tài khoản? <a href="registerCustomer.jsp">Đăng ký</a></p>
</div>
</body>
</html>
