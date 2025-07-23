<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Bảo trì hệ thống - PhoneStore</title>
    <link rel="stylesheet" href="css/style.css?v=<%= System.currentTimeMillis() %>">

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="refresh" content="300"> <!-- Refresh every 5 minutes -->
</head>
<body>
    <div class="maintenance-container">
        <div class="maintenance-content">
            <div class="maintenance-icon">🔧</div>
            
            <h1>Hệ thống đang bảo trì</h1>
            <p>Chúng tôi đang nâng cấp hệ thống để mang đến trải nghiệm tốt hơn cho bạn.</p>
            
            <div class="maintenance-info">
                <div class="info-item">
                    <h3>🕐 Thời gian bảo trì</h3>
                    <p>02:00 - 04:00 (GMT+7)</p>
                </div>
                
                <div class="info-item">
                    <h3>📅 Ngày bảo trì</h3>
                    <p>Chủ nhật hàng tuần</p>
                </div>
                
                <div class="info-item">
                    <h3>⏰ Thời gian còn lại</h3>
                    <div id="countdown" class="countdown"></div>
                </div>
            </div>
            
            <div class="maintenance-updates">
                <h3>Các cải tiến mới:</h3>
                <ul>
                    <li>✅ Tăng tốc độ tải trang</li>
                    <li>✅ Cải thiện giao diện mobile</li>
                    <li>✅ Thêm tính năng tìm kiếm nâng cao</li>
                    <li>✅ Tối ưu hóa thanh toán</li>
                </ul>
            </div>
            
            <div class="contact-emergency">
                <h3>Cần hỗ trợ khẩn cấp?</h3>
                <p>📞 Hotline: <strong>1900-xxxx</strong></p>
                <p>📧 Email: <strong>support@phonestore.com</strong></p>
            </div>
            
            <button onclick="checkStatus()" class="btn btn-primary">
                🔄 Kiểm tra lại
            </button>
        </div>
    </div>
    
    <script>
        // Countdown timer
        function updateCountdown() {
            const now = new Date();
            const maintenanceEnd = new Date();
            maintenanceEnd.setHours(4, 0, 0, 0); // 4:00 AM
            
            if (now > maintenanceEnd) {
                maintenanceEnd.setDate(maintenanceEnd.getDate() + 7); // Next week
            }
            
            const diff = maintenanceEnd - now;
            
            if (diff > 0) {
                const hours = Math.floor(diff / (1000 * 60 * 60));
                const minutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));
                const seconds = Math.floor((diff % (1000 * 60)) / 1000);
                
                document.getElementById('countdown').innerHTML = 
                    `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;
            } else {
                document.getElementById('countdown').innerHTML = 'Sắp hoàn thành...';
                setTimeout(() => {
                    window.location.reload();
                }, 5000);
            }
        }
        
        // Update countdown every second
        setInterval(updateCountdown, 1000);
        updateCountdown();
        
        function checkStatus() {
            window.location.reload();
        }
        
        // Auto-refresh every 5 minutes
        setTimeout(() => {
            window.location.reload();
        }, 300000);
    </script>
</body>
</html>