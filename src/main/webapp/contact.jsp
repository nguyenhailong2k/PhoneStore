<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liên hệ - PhoneStore</title>
    <link rel="stylesheet" href="css/style.css?v=<%= System.currentTimeMillis() %>">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <header>
        <div class="container">
            <h1>📞 Liên hệ với chúng tôi</h1>
            <p>Chúng tôi luôn sẵn sàng hỗ trợ bạn</p>
        </div>
    </header>
    
    <nav>
        <a href="product">Trang chủ</a>
        <a href="category?action=list">Danh mục</a>
        <a href="cart">Giỏ hàng</a>
        <a href="contact.jsp" class="active">Liên hệ</a>
    </nav>
    
    <div class="container">
        <div class="contact-container">
            <div class="contact-info">
                <h3>Thông tin liên hệ</h3>
                
                <div class="contact-item">
                    <div class="contact-icon">🏢</div>
                    <div class="contact-details">
                        <h4>Địa chỉ cửa hàng</h4>
                        <p>123 Đường ABC, Quận 1<br>
                           TP. Hồ Chí Minh, Việt Nam</p>
                    </div>
                </div>
                
                <div class="contact-item">
                    <div class="contact-icon">📞</div>
                    <div class="contact-details">
                        <h4>Điện thoại</h4>
                        <p>Hotline: <strong>1900-xxxx</strong><br>
                           Di động: <strong>0901-xxx-xxx</strong></p>
                    </div>
                </div>
                
                <div class="contact-item">
                    <div class="contact-icon">📧</div>
                    <div class="contact-details">
                        <h4>Email</h4>
                        <p>Hỗ trợ: <strong>support@phonestore.com</strong><br>
                           Bán hàng: <strong>sales@phonestore.com</strong></p>
                    </div>
                </div>
                
                <div class="contact-item">
                    <div class="contact-icon">🕒</div>
                    <div class="contact-details">
                        <h4>Giờ làm việc</h4>
                        <p>Thứ 2 - Thứ 7: <strong>8:00 - 22:00</strong><br>
                           Chủ nhật: <strong>9:00 - 21:00</strong></p>
                    </div>
                </div>
                
                <div class="social-links">
                    <h4>Kết nối với chúng tôi</h4>
                    <div class="social-icons">
                        <a href="#" class="social-link">📘 Facebook</a>
                        <a href="#" class="social-link">📸 Instagram</a>
                        <a href="#" class="social-link">🐦 Twitter</a>
                        <a href="#" class="social-link">📺 YouTube</a>
                    </div>
                </div>
            </div>
            
            <div class="contact-form">
                <h3>Gửi tin nhắn cho chúng tôi</h3>
                
                <form id="contactForm" onsubmit="submitContact(event)">
                    <div class="form-group">
                        <label for="fullName">Họ và tên *</label>
                        <input type="text" id="fullName" name="fullName" required/>
                    </div>
                    
                    <div class="form-group">
                        <label for="email">Email *</label>
                        <input type="email" id="email" name="email" required/>
                    </div>
                    
                    <div class="form-group">
                        <label for="phone">Số điện thoại</label>
                        <input type="tel" id="phone" name="phone"/>
                    </div>
                    
                    <div class="form-group">
                        <label for="subject">Chủ đề *</label>
                        <select id="subject" name="subject" required>
                            <option value="">Chọn chủ đề</option>
                            <option value="support">Hỗ trợ kỹ thuật</option>
                            <option value="order">Thông tin đơn hàng</option>
                            <option value="complaint">Khiếu nại</option>
                            <option value="suggestion">Góp ý</option>
                            <option value="other">Khác</option>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="message">Nội dung tin nhắn *</label>
                        <textarea id="message" name="message" rows="5" required 
                                  placeholder="Mô tả chi tiết vấn đề của bạn..."></textarea>
                    </div>
                    
                    <div class="form-group">
                        <label class="checkbox-label">
                            <input type="checkbox" required/>
                            Tôi đồng ý với <a href="#" onclick="showPrivacyPolicy()">chính sách bảo mật</a>
                        </label>
                    </div>
                    
                    <button type="submit" class="btn btn-primary btn-large">
                        📤 Gửi tin nhắn
                    </button>
                </form>
                
                <div id="contactResult" class="form-result" style="display: none;"></div>
            </div>
        </div>
        
        <div class="faq-section">
            <h3>Câu hỏi thường gặp</h3>
            
            <div class="faq-item">
                <div class="faq-question" onclick="toggleFAQ(this)">
                    <span>❓ Làm thế nào để kiểm tra bảo hành sản phẩm?</span>
                    <span class="faq-toggle">+</span>
                </div>
                <div class="faq-answer">
                    <p>Bạn có thể kiểm tra thông tin bảo hành bằng cách liên hệ hotline hoặc mang sản phẩm đến cửa hàng gần nhất.</p>
                </div>
            </div>
            
            <div class="faq-item">
                <div class="faq-question" onclick="toggleFAQ(this)">
                    <span>🚚 Chính sách giao hàng như thế nào?</span>
                    <span class="faq-toggle">+</span>
                </div>
                <div class="faq-answer">
                    <p>Chúng tôi giao hàng miễn phí toàn quốc trong 2-3 ngày làm việc. Hỗ trợ giao hàng nhanh trong ngày tại các thành phố lớn.</p>
                </div>
            </div>
            
            <div class="faq-item">
                <div class="faq-question" onclick="toggleFAQ(this)">
                    <span>💳 Các phương thức thanh toán được hỗ trợ?</span>
                    <span class="faq-toggle">+</span>
                </div>
                <div class="faq-answer">
                    <p>Chúng tôi hỗ trợ thanh toán COD, chuyển khoản ngân hàng, và các ví điện tử phổ biến.</p>
                </div>
            </div>
            
            <div class="faq-item">
                <div class="faq-question" onclick="toggleFAQ(this)">
                    <span>🔄 Chính sách đổi trả sản phẩm?</span>
                    <span class="faq-toggle">+</span>
                </div>
                <div class="faq-answer">
                    <p>Đổi trả trong vòng 7 ngày với sản phẩm còn nguyên seal, có đầy đủ hóa đơn và phụ kiện.</p>
                </div>
            </div>
        </div>
    </div>
    
    <footer>
        <div class="container">
            <p>&copy; 2024 PhoneStore. All rights reserved.</p>
        </div>
    </footer>
    
    <script>
        function submitContact(event) {
            event.preventDefault();
            
            // Simulate form submission
            const resultDiv = document.getElementById('contactResult');
            resultDiv.innerHTML = `
                <div class="success-message">
                    ✅ Cảm ơn bạn đã liên hệ! Chúng tôi sẽ phản hồi trong vòng 24 giờ.
                </div>
            `;
            resultDiv.style.display = 'block';
            
            // Reset form
            document.getElementById('contactForm').reset();
            
            // Scroll to result
            resultDiv.scrollIntoView({ behavior: 'smooth' });
        }
        
        function toggleFAQ(element) {
            const answer = element.nextElementSibling;
            const toggle = element.querySelector('.faq-toggle');
            
            if (answer.style.display === 'block') {
                answer.style.display = 'none';
                toggle.textContent = '+';
            } else {
                answer.style.display = 'block';
                toggle.textContent = '-';
            }
        }
        
        function showPrivacyPolicy() {
            alert('Chính sách bảo mật:\n\n- Chúng tôi cam kết bảo vệ thông tin cá nhân của bạn\n- Thông tin chỉ được sử dụng để hỗ trợ và liên hệ\n- Không chia sẻ thông tin với bên thứ ba\n- Dữ liệu được mã hóa và bảo mật cao');
        }
    </script>
</body>
</html>