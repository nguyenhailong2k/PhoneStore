<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lỗi hệ thống - PhoneStore</title>
    <link rel="stylesheet" href="css/style.css?v=<%= System.currentTimeMillis() %>">

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <header>
        <div class="container">
            <h1>❌ Có lỗi xảy ra</h1>
            <p>Xin lỗi vì sự bất tiện này</p>
        </div>
    </header>
    
    <nav>
        <a href="product">Trang chủ</a>
        <a href="category?action=list">Danh mục</a>
        <a href="cart">Giỏ hàng</a>
        <a href="contact.jsp">Liên hệ</a>
    </nav>
    
    <div class="container">
        <div class="error-container">
            <div class="error-icon">💥</div>
            
            <div class="error-content">
                <%
                    String errorMessage = (String) request.getAttribute("errorMessage");
                    Integer errorCode = (Integer) request.getAttribute("errorCode");
                    
                    if (errorCode != null) {
                %>
                    <h2>Lỗi <%= errorCode %></h2>
                <% } else { %>
                    <h2>Lỗi hệ thống</h2>
                <% } %>
                
                <% if (errorMessage != null) { %>
                    <p class="error-message"><%= errorMessage %></p>
                <% } else { %>
                    <p class="error-message">Đã xảy ra lỗi không mong muốn. Vui lòng thử lại sau.</p>
                <% } %>
                
                <div class="error-actions">
                    <a href="javascript:history.back()" class="btn btn-secondary">← Quay lại</a>
                    <a href="product" class="btn btn-primary">🏠 Trang chủ</a>
                    <a href="contact.jsp" class="btn btn-outline">📞 Liên hệ hỗ trợ</a>
                </div>
                
                <div class="error-help">
                    <h3>Một số gợi ý:</h3>
                    <ul>
                        <li>Kiểm tra đường dẫn URL</li>
                        <li>Làm mới trang (F5)</li>
                        <li>Xóa cache trình duyệt</li>
                        <li>Thử lại sau vài phút</li>
                    </ul>
                </div>
                
                <% if (exception != null) { %>
                    <details class="error-details">
                        <summary>Chi tiết lỗi (cho developer)</summary>
                        <pre><%= exception.getMessage() %></pre>
                    </details>
                <% } %>
            </div>
        </div>
    </div>
    
    <footer>
        <div class="container">
            <p>&copy; 2024 PhoneStore. All rights reserved.</p>
        </div>
    </footer>
    
    <script>
        // Auto-report error (optional)
        if (window.navigator.onLine) {
            // Log error to server for monitoring
            fetch('/api/log-error', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    url: window.location.href,
                    userAgent: navigator.userAgent,
                    timestamp: new Date().toISOString()
                })
            }).catch(() => {
                // Ignore logging errors
            });
        }
    </script>
</body>
</html>