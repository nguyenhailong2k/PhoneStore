<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.model.Order" %>
<%@ page import="com.example.model.OrderItem" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chi tiết đơn hàng - PhoneStore</title>
    <link rel="stylesheet" href="css/style.css?v=<%= System.currentTimeMillis() %>">

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <header>
        <div class="container">
            <h1>📦 Chi tiết đơn hàng</h1>
        </div>
    </header>

    <nav>
        <a href="admin">🏠 Quản lý</a>
        <a href="order?action=list">📋 Danh sách đơn hàng</a>
        <a href="logout">🔓 Đăng xuất</a>
    </nav>

    <div class="container">
        <%
            Order order = (Order) request.getAttribute("order");
            if (order != null) {
        %>
            <div class="form-container">
                <h3>Thông tin khách hàng</h3>
                <p><strong>Họ tên:</strong> <%= order.getCustomerName() %></p>
                <p><strong>Email:</strong> <%= order.getEmail() %></p>
                <p><strong>Điện thoại:</strong> <%= order.getPhone() %></p>
                <p><strong>Địa chỉ:</strong> <%= order.getAddress() %></p>
                <p><strong>Trạng thái:</strong> <%= order.getStatus() %></p>
                <p><strong>Ngày đặt:</strong> <%= order.getOrderDate() %></p>
                <p><strong>Tổng tiền:</strong> <%= String.format("%,.0f", order.getTotalAmount()) %> VND</p>

                <hr />

                <h3>Chi tiết sản phẩm</h3>
                <table>
                    <thead>
                        <tr>
                            <th>Tên sản phẩm</th>
                            <th>Giá</th>
                            <th>Số lượng</th>
                            <th>Thành tiền</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            List<OrderItem> items = order.getItems();
                            for (OrderItem item : items) {
                        %>
                            <tr>
                                <td><%= item.getProductName() %></td>
                                <td><%= String.format("%,.0f", item.getPrice()) %> VND</td>
                                <td><%= item.getQuantity() %></td>
                                <td><%= String.format("%,.0f", item.getSubtotal()) %> VND</td>
                            </tr>
                        <%
                            }
                        %>
                    </tbody>
                </table>
            </div>
        <%
            } else {
        %>
            <div class="error-message">
                ❌ Không tìm thấy đơn hàng!
            </div>
            <a href="orderList.jsp" class="btn btn-secondary">← Quay lại</a>
        <%
            }
        %>
    </div>

    <footer>
        <p>&copy; 2024 PhoneStore Admin Panel. All rights reserved.</p>
    </footer>
</body>
</html>
