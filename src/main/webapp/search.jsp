<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.example.model.Product" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tìm kiếm - PhoneStore</title>
    <link rel="stylesheet" href="css/style.css?v=<%= System.currentTimeMillis() %>">

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <header>
        <div class="container">
            <h1>🔍 Tìm kiếm sản phẩm</h1>
            <%
                String keyword = request.getParameter("keyword");
                if (keyword != null && !keyword.trim().isEmpty()) {
            %>
                <p>Kết quả tìm kiếm cho: "<strong><%= keyword %></strong>"</p>
            <% } %>
        </div>
    </header>
    
    <nav>
        <a href="product">Trang chủ</a>
        <a href="category?action=list">Danh mục</a>
        <a href="cart">Giỏ hàng</a>
        <a href="contact.jsp">Liên hệ</a>
    </nav>
    
    <div class="container">
        <!-- Search Form -->
        <div class="search-form">
            <form method="get" action="search">
                <div class="search-input-group">
                    <input type="text" name="keyword" placeholder="Nhập tên sản phẩm, thương hiệu..." 
                           value="<%= keyword != null ? keyword : "" %>" required/>
                    <button type="submit" class="btn btn-primary">🔍 Tìm kiếm</button>
                </div>
                
                <div class="search-filters">
                    <div class="filter-group">
                        <label>Khoảng giá:</label>
                        <select name="priceRange">
                            <option value="">Tất cả</option>
                            <option value="0-5000000">Dưới 5 triệu</option>
                            <option value="5000000-10000000">5-10 triệu</option>
                            <option value="10000000-20000000">10-20 triệu</option>
                            <option value="20000000-50000000">20-50 triệu</option>
                            <option value="50000000-">Trên 50 triệu</option>
                        </select>
                    </div>
                    
                    <div class="filter-group">
                        <label>Thương hiệu:</label>
                        <select name="brand">
                            <option value="">Tất cả</option>
                            <option value="Apple">Apple</option>
                            <option value="Samsung">Samsung</option>
                            <option value="Xiaomi">Xiaomi</option>
                            <option value="Oppo">Oppo</option>
                            <option value="Vivo">Vivo</option>
                        </select>
                    </div>
                </div>
            </form>
        </div>
        
        <!-- Search Results -->
        <%
            @SuppressWarnings("unchecked")
            List<Product> products = (List<Product>) request.getAttribute("products");
            if (products != null) {
        %>
            <div class="search-results">
                <div class="results-header">
                    <h3>Tìm thấy <%= products.size() %> sản phẩm</h3>
                    
                    <div class="sort-options">
                        <label>Sắp xếp:</label>
                        <select onchange="sortResults(this.value)">
                            <option value="relevance">Liên quan nhất</option>
                            <option value="name">Tên A-Z</option>
                            <option value="price-asc">Giá thấp đến cao</option>
                            <option value="price-desc">Giá cao đến thấp</option>
                        </select>
                    </div>
                </div>
                
                <% if (!products.isEmpty()) { %>
                    <div class="products-grid">
                        <% for (Product product : products) { %>
                            <div class="product-card">
                                <div class="product-image">
                                    <% if (product.getImage() != null && !product.getImage().trim().isEmpty()) { %>
                                        <img src="<%= product.getImage() %>" alt="<%= product.getName() %>" 
                                             onerror="this.src='images/no-image.png'"/>
                                    <% } else { %>
                                        <img src="images/no-image.png" alt="<%= product.getName() %>"/>
                                    <% } %>
                                </div>
                                
                                <div class="product-info">
                                    <h4><%= product.getName() %></h4>
                                    <p class="brand"><%= product.getBrand() %></p>
                                    <div class="price">
                                        <%= String.format("%,.0f", product.getPrice()) %> VND
                                    </div>
                                    
                                    <div class="product-actions">
                                        <a href="product?action=view&id=<%= product.getId() %>" 
                                           class="btn btn-secondary">Chi tiết</a>
                                        
                                        <% if (product.getStockQuantity() > 0) { %>
                                            <form method="post" action="cart" style="display: inline;">
                                                <input type="hidden" name="action" value="add"/>
                                                <input type="hidden" name="productId" value="<%= product.getId() %>"/>
                                                <input type="hidden" name="quantity" value="1"/>
                                                <button type="submit" class="btn btn-primary">🛒 Thêm</button>
                                            </form>
                                        <% } else { %>
                                            <button class="btn btn-disabled" disabled>Hết hàng</button>
                                        <% } %>
                                    </div>
                                </div>
                            </div>
                        <% } %>
                    </div>
                <% } else { %>
                    <div class="no-results">
                        <div class="no-results-icon">😔</div>
                        <h3>Không tìm thấy sản phẩm nào</h3>
                        <p>Thử tìm kiếm với từ khóa khác hoặc điều chỉnh bộ lọc</p>
                        
                        <div class="search-suggestions">
                            <h4>Gợi ý tìm kiếm:</h4>
                            <ul>
                                <li>Kiểm tra chính tả từ khóa</li>
                                <li>Sử dụng từ khóa tổng quát hơn</li>
                                <li>Thử tìm theo thương hiệu</li>
                            </ul>
                        </div>
                        
                        <a href="product" class="btn btn-primary">Xem tất cả sản phẩm</a>
                    </div>
                <% } %>
            </div>
        <% } %>
    </div>
    
    <footer>
        <div class="container">
            <p>&copy; 2024 PhoneStore. All rights reserved.</p>
        </div>
    </footer>
    
    <script>
        function sortResults(sortBy) {
            // Implementation for sorting search results
            window.location.href = updateURLParameter(window.location.href, 'sortBy', sortBy);
        }
        
        function updateURLParameter(url, param, paramVal) {
            var newAdditionalURL = "";
            var tempArray = url.split("?");
            var baseURL = tempArray[0];
            var additionalURL = tempArray[1];
            var temp = "";
            if (additionalURL) {
                tempArray = additionalURL.split("&");
                for (var i = 0; i < tempArray.length; i++) {
                    if (tempArray[i].split('=')[0] != param) {
                        newAdditionalURL += temp + tempArray[i];
                        temp = "&";
                    }
                }
            }
            var rows_txt = temp + "" + param + "=" + paramVal;
            return baseURL + "?" + newAdditionalURL + rows_txt;
        }
    </script>
</body>
</html>