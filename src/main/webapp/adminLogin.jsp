<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập Admin - PhoneStore</title>
    <link rel="stylesheet" href="css/style.css?v=<%= System.currentTimeMillis() %>">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body class="admin-login">
    <div class="login-container">
        <div class="login-form">
            <div class="login-header">
                <h2>🔐 Đăng nhập Admin</h2>
                <p>PhoneStore Management System</p>
            </div>
            
            <% String error = (String) request.getAttribute("error"); %>
            <% if (error != null) { %>
                <div class="error-message">
                    ❌ <%= error %>
                </div>
            <% } %>
            
            <form method="post" action="admin" id="loginForm">
                <input type="hidden" name="action" value="login"/>
                
                <div class="form-group">
                    <label for="username">👤 Tên đăng nhập</label>
                    <input type="text" id="username" name="username" required 
                           placeholder="Nhập tên đăng nhập"/>
                </div>
                
                <div class="form-group">
                    <label for="password">🔒 Mật khẩu</label>
                    <input type="password" id="password" name="password" required 
                           placeholder="Nhập mật khẩu"/>
                    <span class="password-toggle" onclick="togglePassword()">👁️</span>
                </div>
                
                <div class="form-group">
                    <label class="checkbox-label">
                        <input type="checkbox" name="remember"/>
                        Ghi nhớ đăng nhập
                    </label>
                </div>
                
                <button type="submit" class="btn btn-primary btn-large">
                    🚀 Đăng nhập
                </button>
            </form>
            
            <div class="login-footer">
                <p><a href="product">← Quay về trang chủ</a></p>
                <p class="demo-info">
                    <small>Demo: admin/admin123</small>
                </p>
            </div>
        </div>
        
        <div class="login-bg">
            <div class="bg-overlay"></div>
            <div class="bg-content">
                <h3>Quản lý PhoneStore</h3>
                <ul>
                    <li>✅ Quản lý sản phẩm</li>
                    <li>✅ Quản lý đơn hàng</li>
                    <li>✅ Quản lý khách hàng</li>
                    <li>✅ Báo cáo thống kê</li>
                </ul>
            </div>
        </div>
    </div>
    
    <script>
        function togglePassword() {
            const passwordField = document.getElementById('password');
            const toggleIcon = document.querySelector('.password-toggle');
            
            if (passwordField.type === 'password') {
                passwordField.type = 'text';
                toggleIcon.textContent = '🙈';
            } else {
                passwordField.type = 'password';
                toggleIcon.textContent = '👁️';
            }
        }
        
        // Auto-focus on username field
        document.getElementById('username').focus();
        
        // Form validation
        document.getElementById('loginForm').addEventListener('submit', function(e) {
            const username = document.getElementById('username').value.trim();
            const password = document.getElementById('password').value;
            
            if (!username || !password) {
                e.preventDefault();
                alert('Vui lòng nhập đầy đủ thông tin!');
            }
        });
    </script>
</body>
</html>