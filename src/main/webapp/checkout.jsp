<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.model.Cart" %>
<%@ page import="com.example.model.CartItem" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Thanh toán - PhoneStore</title>
    <link rel="stylesheet" href="css/style.css?v=<%= System.currentTimeMillis() %>">
</head>
<body>

<header>
    <div class="container header-inner">
        <h1>🛒 Thanh toán</h1>
    </div>
</header>

<div class="container">
    <%
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null || cart.getItems().isEmpty()) {
    %>
        <div class="error-message">❌ Giỏ hàng của bạn đang trống.</div>
    <%
        } else {
    %>

    <form action="order" method="post" class="checkout-form">
        <input type="hidden" name="action" value="create">
        <h2>🧾 Thông tin người nhận</h2>
        <div class="form-group">
            <label>Họ tên:</label>
            <input type="text" name="customerName" required>
        </div>
        <div class="form-group">
            <label>Email:</label>
            <input type="email" name="email" required>
        </div>
        <div class="form-group">
            <label>Số điện thoại:</label>
            <input type="text" name="phone" required>
        </div>
        <div class="form-group">
            <label>Địa chỉ giao hàng:</label>
            <textarea name="address" required></textarea>
        </div>

        <h2>💳 Phương thức thanh toán</h2>
        <div class="form-group">
            <label><input type="radio" name="paymentMethod" value="COD" checked> Thanh toán khi nhận hàng (COD)</label><br>
            <label><input type="radio" name="paymentMethod" value="bank"> Chuyển khoản ngân hàng</label><br>
            <label><input type="radio" name="paymentMethod" value="momo"> Ví MoMo</label>
        </div>

        <div class="bank-info" id="bankInfo" style="display: none;">
            <p><strong>Ngân hàng:</strong> Vietcombank</p>
            <p><strong>Số tài khoản:</strong> 0123456789</p>
            <p><strong>Chủ tài khoản:</strong> Nguyễn Văn A</p>
        </div>

        <h2>🧺 Giỏ hàng</h2>
        <table class="cart-table">
            <thead>
                <tr>
                    <th>Sản phẩm</th>
                    <th>Giá</th>
                    <th>Số lượng</th>
                    <th>Tạm tính</th>
                </tr>
            </thead>
            <tbody>
                <% for (CartItem item : cart.getItems()) { %>
                <tr>
                    <td><%= item.getProduct().getName() %></td>
                    <td><%= String.format("%,.0f", item.getProduct().getPrice()) %> đ</td>
                    <td><%= item.getQuantity() %></td>
                    <td><%= String.format("%,.0f", item.getSubtotal()) %> đ</td>
                </tr>
                <% } %>
                <tr>
                    <td colspan="3" class="text-right"><strong>Tổng cộng:</strong></td>
                    <td><strong><%= String.format("%,.0f", cart.getTotalPrice()) %> đ</strong></td>
                </tr>
            </tbody>
        </table>

        <div class="form-group text-center">
            <button type="submit" class="btn btn-primary">✅ Xác nhận đặt hàng</button>
        </div>
    </form>

    <script>
        const radios = document.querySelectorAll('input[name="paymentMethod"]');
        const bankInfo = document.getElementById("bankInfo");
        radios.forEach(radio => {
            radio.addEventListener("change", () => {
                bankInfo.style.display = radio.value === "bank" ? "block" : "none";
            });
        });
    </script>

    <% } %>
</div>

<footer>
    <div class="container">© 2025 PhoneStore</div>
</footer>

</body>
</html>
