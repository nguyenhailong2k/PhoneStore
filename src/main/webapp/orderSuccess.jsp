<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đặt hàng thành công - PhoneStore</title>
    <link rel="stylesheet" href="css/style.css?v=<%= System.currentTimeMillis() %>">

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <header>
        <div class="container">
            <h1>✅ Đặt hàng thành công!</h1>
            <p>Cảm ơn bạn đã mua sắm tại PhoneStore</p>
        </div>
    </header>
    
    <nav>
        <a href="product">Trang chủ</a>
        <a href="category?action=list">Danh mục</a>
        <a href="contact.jsp">Liên hệ</a>
    </nav>
    
    <div class="container">
        <div class="success-message">
            <div class="success-icon">🎉</div>
            <h2>Đơn hàng đã được tiếp nhận!</h2>
            
            <%
                String orderId = request.getParameter("orderId");
                if (orderId != null) {
            %>
            <p class="order-info">
                <strong>Mã đơn hàng: #<%= orderId %></strong>
            </p>
            <% } %>
            
            <div class="next-steps">
                <h3>Các bước tiếp theo:</h3>
                <div class="steps">
                    <div class="step">
                        <span class="step-number">1</span>
                        <div class="step-content">
                            <h4>Xác nhận đơn hàng</h4>
                            <p>Chúng tôi sẽ gọi điện xác nhận trong vòng 30 phút</p>
                        </div>
                    </div>
                    
                    <div class="step">
                        <span class="step-number">2</span>
                        <div class="step-content">
                            <h4>Chuẩn bị hàng</h4>
                            <p>Đóng gói và chuẩn bị giao hàng trong 1-2 ngày</p>
                        </div>
                    </div>
                    
                    <div class="step">
                        <span class="step-number">3</span>
                        <div class="step-content">
                            <h4>Giao hàng</h4>
                            <p>Nhận hàng và thanh toán tại nhà trong 2-3 ngày</p>
                        </div>
                    </div>
                </div>
            </div>
            
            <div class="contact-info">
                <h3>Thông tin liên hệ:</h3>
                <div class="contact-details">
                    <p>📞 Hotline: 1900-xxxx</p>
                    <p>📧 Email: support@phonestore.com</p>
                    <p>🕒 Thời gian hỗ trợ: 8:00 - 22:00 hàng ngày</p>
                </div>
            </div>
            
            <div class="action-buttons">
                <a href="product" class="btn btn-primary">Tiếp tục mua sắm</a>
                <a href="contact.jsp" class="btn btn-secondary">Liên hệ hỗ trợ</a>
            </div>
        </div>
    </div>
    
    <footer>
        <div class="container">
            <p>&copy; 2024 PhoneStore. All rights reserved.</p>
        </div>
    </footer>
</body>
</html>