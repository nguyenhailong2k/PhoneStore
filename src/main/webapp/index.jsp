<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.model.Product" %>
<%@ page import="com.example.model.Customer" %>
<%@ page import="com.example.model.Category" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Trang chủ - PhoneStore</title>
    <link rel="stylesheet" href="css/style.css?v=<%= System.currentTimeMillis() %>">
    <link rel="stylesheet" href="css/dark.css" id="darkTheme" disabled>
</head>
<body>

<header>
    <div class="container">
        <h1>📱 PhoneStore</h1>
        <p>Chào mừng bạn đến với cửa hàng điện thoại trực tuyến</p>
    </div>
</header>

<nav>
    <div class="menu">
        <a href="product">🏠 Trang chủ</a>
        <a href="category?action=list">📂 Danh mục</a>
        <a href="cart">🛒 Giỏ hàng (<%= session.getAttribute("cart") != null ? ((com.example.model.Cart)session.getAttribute("cart")).getTotalItems() : 0 %>)</a>
        <a href="order?action=list">📦 Đơn hàng</a>
        <% if (session.getAttribute("customer") == null) { %>
            <a href="javascript:void(0);" onclick="openAuthModal('login')">🔐 Đăng nhập</a> |
            <a href="javascript:void(0);" onclick="openAuthModal('register')">📝 Đăng ký</a>
        <% } else { %>
            <a href="checkout.jsp">💳 Thanh toán</a>
            <a href="logout.jsp">🚪 Đăng xuất (<%= ((Customer)session.getAttribute("customer")).getName() %>)</a>
        <% } %>
    </div>
    <button id="themeToggle" onclick="toggleTheme()" class="btn">🌙 Giao diện tối</button>
</nav>

<div class="ad-banner">
    🎉 <strong>Khuyến mãi HOT:</strong> Giảm giá 10% cho đơn hàng trên 10 triệu!
</div>

<div class="container">
    <%
        Category selectedCategory = (Category) request.getAttribute("selectedCategory");
        if (selectedCategory != null) {
    %>
        <div class="breadcrumb">› <span><%= selectedCategory.getName() %></span></div>
        <h2>Sản phẩm thuộc danh mục: <%= selectedCategory.getName() %></h2>
    <%
        } else {
    %>
        <h2>Tất cả sản phẩm</h2>
        <form action="product" method="get" style="margin-bottom: 20px;">
            <input type="text" name="keyword" placeholder="🔍 Tìm kiếm..." value="<%= request.getParameter("keyword") != null ? request.getParameter("keyword") : "" %>"/>
            <select name="sort">
                <option value="">-- Sắp xếp theo giá --</option>
                <option value="asc" <%= "asc".equals(request.getParameter("sort")) ? "selected" : "" %>>Tăng dần</option>
                <option value="desc" <%= "desc".equals(request.getParameter("sort")) ? "selected" : "" %>>Giảm dần</option>
            </select>
            <input type="number" name="min" placeholder="Giá từ" value="<%= request.getParameter("min") != null ? request.getParameter("min") : "" %>"/>
            <input type="number" name="max" placeholder="Đến" value="<%= request.getParameter("max") != null ? request.getParameter("max") : "" %>"/>
            <button type="submit" class="btn">🔍 Lọc</button>
        </form>
    <%
        }

        @SuppressWarnings("unchecked")
        List<Product> products = (List<Product>) request.getAttribute("products");
        if (products != null && !products.isEmpty()) {
    %>
    <div class="product-list">
        <% for (Product product : products) { %>
            <div class="product-card">
                <img src="images/<%= product.getImage() %>" alt="<%= product.getName() %>">
                <div class="product-info">
                    <h3><a href="product?action=detail&id=<%= product.getId() %>"><%= product.getName() %></a></h3>
                    <p class="price"><%= String.format("%,.0f", product.getPrice()) %> đ</p>
                    <form action="cart" method="post">
                        <input type="hidden" name="action" value="add">
                        <input type="hidden" name="productId" value="<%= product.getId() %>">
                        <input type="hidden" name="quantity" value="1">
                        <button type="submit" class="btn">🛒 Thêm vào giỏ</button>
                    </form>
                </div>
            </div>
        <% } %>
    </div>
    <% } else { %>
        <div class="error-message">
            ❌ Không có sản phẩm nào được tìm thấy.
        </div>
    <% } %>
</div>

<!-- Modal chung: Đăng nhập / Đăng ký -->
<div id="authModal" class="modal-overlay" style="display: none;">
  <div class="modal-content" style="max-width: 500px;">
    <span class="close-button" onclick="closeAuthModal()">×</span>

    <!-- Tabs -->
    <div style="display: flex; justify-content: center; margin-bottom: 20px;">
      <button class="tab-btn" onclick="switchTab('login')">🔐 Đăng nhập</button>
      <button class="tab-btn" onclick="switchTab('register')">📝 Đăng ký</button>
    </div>

    <!-- Login Form -->
    <form id="loginForm" action="loginCustomer" method="post" style="display: none;">
      <h3>🔐 Đăng nhập</h3>
      <input type="email" name="email" placeholder="Email" required>
      <input type="password" name="password" placeholder="Mật khẩu" required>
      <button type="submit" class="btn">Đăng nhập</button>
    </form>

    <!-- Register Form -->
    <form id="registerForm" action="registerCustomer" method="post" onsubmit="return validateRegisterForm();" style="display: none;">
      <h3>📝 Đăng ký</h3>
      <input type="text" name="name" placeholder="Họ tên" required>
      <input type="email" name="email" placeholder="Email" required>
      <input type="text" name="phone" placeholder="Số điện thoại" required pattern="0[0-9]{9,10}" title="Nhập số điện thoại hợp lệ">
      <input type="text" name="address" placeholder="Địa chỉ" required>
      <input type="password" id="regPassword" name="password" placeholder="Mật khẩu" required>
      <input type="password" id="regConfirm" placeholder="Nhập lại mật khẩu" required>
      <div id="registerErrorMessage" style="color: red; margin-bottom: 10px;"></div>
      <button type="submit" class="btn btn-primary">Đăng ký</button>
    </form>
  </div>
</div>

<%
    String loginError = (String) request.getAttribute("loginError");
    String registerError = (String) request.getAttribute("registerError");
    if (loginError != null) {
%>
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            openAuthModal("login");
            alert("<%= loginError %>");
        });
    </script>
<% } else if (registerError != null) { %>
    <script>
        document.addEventListener("DOMContentLoaded", function () {
            openAuthModal("register");
            document.getElementById("registerErrorMessage").textContent = "<%= registerError %>";
        });
    </script>
<% } %>

<footer>
    <p>&copy; 2024 PhoneStore - Thiết kế bởi bạn</p>
</footer>

<script src="js/script.js"></script>
<script>
function toggleTheme() {
  const darkLink = document.getElementById("darkTheme");
  const theme = darkLink.disabled ? "dark" : "light";
  darkLink.disabled = !darkLink.disabled;
  localStorage.setItem("theme", theme);
  const btn = document.getElementById("themeToggle");
  if (btn) btn.textContent = theme === "dark" ? "☀️ Giao diện sáng" : "🌙 Giao diện tối";
}

window.addEventListener("DOMContentLoaded", () => {
  const theme = localStorage.getItem("theme");
  const darkLink = document.getElementById("darkTheme");
  if (theme === "dark" && darkLink) {
    darkLink.disabled = false;
  }

  const btn = document.getElementById("themeToggle");
  if (btn) {
    btn.textContent = darkLink.disabled ? "🌙 Giao diện tối" : "☀️ Giao diện sáng";
  }
});
</script>
</body>
</html>