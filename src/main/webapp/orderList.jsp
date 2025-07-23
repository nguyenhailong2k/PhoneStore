<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.model.Order" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh sách đơn hàng - PhoneStore</title>
    <link rel="stylesheet" href="css/style.css?v=<%= System.currentTimeMillis() %>">
</head>
<body>

<header>
    <div class="container header-inner">
        <h1>📦 Danh sách đơn hàng</h1>
    </div>
</header>

<nav>
    <div class="container menu">
        <a href="product">🏠 Trang chủ</a>
        <a href="admin">📊 Quản trị</a>
        <a href="logout.jsp">🚪 Đăng xuất</a>
    </div>
</nav>

<div class="container">
<%
    @SuppressWarnings("unchecked")
    List<Order> orders = (List<Order>) request.getAttribute("orders");
    if (orders != null && !orders.isEmpty()) {
%>
    <table class="order-table">
        <thead>
            <tr>
                <th>Mã đơn</th>
                <th>Khách hàng</th>
                <th>Email</th>
                <th>Điện thoại</th>
                <th>Trạng thái</th>
                <th>Ngày đặt</th>
                <th>Tổng tiền</th>
                <th>Xem</th>
                <th>Xóa</th>
            </tr>
        </thead>
        <tbody>
        <% for (Order o : orders) { %>
            <tr>
                <td>#<%= o.getId() %></td>
                <td><%= o.getCustomerName() %></td>
                <td><%= o.getEmail() %></td>
                <td><%= o.getPhone() %></td>
                <td>
                    <span class="badge <%= o.getStatus() != null ? o.getStatus().toLowerCase() : "" %>">
                        <%= o.getStatus() %>
                    </span>
                </td>
                <td><%= o.getCreatedAt() != null ? o.getCreatedAt().toString() : "" %></td>
                <td><%= String.format("%,.0f", o.getTotalAmount()) %> đ</td>
                <td>
                    <a href="order?action=view&id=<%= o.getId() %>" class="btn btn-secondary">🔍 Chi tiết</a>
                </td>
                <td>
                    <form action="order" method="post" onsubmit="return confirm('Bạn có chắc chắn muốn xóa đơn hàng này không?');">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="id" value="<%= o.getId() %>">
                        <button type="submit" class="btn btn-danger">🗑 Xóa</button>
                    </form>
                </td>
            </tr>
        <% } %>
        </tbody>
    </table>
<% } else { %>
    <p class="error-message">Không có đơn hàng nào.</p>
<% } %>
</div>

<footer>
    <div class="container">
        &copy; 2025 PhoneStore
    </div>
</footer>

</body>
</html>
