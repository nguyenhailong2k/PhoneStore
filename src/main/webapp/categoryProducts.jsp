<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.example.model.Product" %>
<%@ page import="com.example.model.Category" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>
        <%
            Category category = (Category) request.getAttribute("category");
            if (category != null) {
                out.print(category.getName() + " - PhoneStore");
            } else {
                out.print("Danh mục - PhoneStore");
            }
        %>
    </title>
    <link rel="stylesheet" href="css/style.css?v=<%= System.currentTimeMillis() %>">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
    <header>
        <div class="container">
            <% if (category != null) { %>
                <h1>📱 <%= category.getName() %></h1>
                <% if (category.getDescription() != null && !category.getDescription().trim().isEmpty()) { %>
                    <p><%= category.getDescription() %></p>
                <% } %>
            <% } else { %>
                <h1>📱 Sản phẩm</h1>
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
        <div class="breadcrumb">
            <a href="product">Trang chủ</a> / 
            <a href="category?action=list">Danh mục</a> / 
            <% if (category != null) { %>
                <span><%= category.getName() %></span>
            <% } %>
        </div>
        
        <%
            @SuppressWarnings("unchecked")
            List<Product> products = (List<Product>) request.getAttribute("products");
            if (products != null && !products.isEmpty()) {
        %>
        <div class="filter-bar">
            <div class="sort-options">
                <label for="sortBy">Sắp xếp theo:</label>
                <select id="sortBy" onchange="sortProducts()">
                    <option value="name">Tên A-Z</option>
                    <option value="price-asc">Giá thấp đến cao</option>
                    <option value="price-desc">Giá cao đến thấp</option>
                </select>
            </div>
            
            <div class="view-options">
                <span>Hiển thị:</span>
                <button onclick="toggleView('grid')" class="view-btn active" id="grid-btn">⊞ Grid</button>
                <button onclick="toggleView('list')" class="view-btn" id="list-btn">☰ List</button>
            </div>
        </div>
        
        <div class="products-container" id="products-container">
            <% for (Product product : products) { %>
                <div class="product-card" data-name="<%= product.getName() %>" data-price="<%= product.getPrice() %>">
                    <div class="product-image">
                        <% if (product.getImage() != null && !product.getImage().trim().isEmpty()) { %>
                            <img src="<%= product.getImage() %>" alt="<%= product.getName() %>" 
                                 onerror="this.src='images/no-image.png'"/>
                        <% } else { %>
                            <img src="images/no-image.png" alt="<%= product.getName() %>"/>
                        <% } %>
                        
                        <% if (product.getStockQuantity() <= 0) { %>
                            <div class="stock-badge out-of-stock">Hết hàng</div>
                        <% } else if (product.getStockQuantity() <= 5) { %>
                            <div class="stock-badge low-stock">Còn <%= product.getStockQuantity() %></div>
                        <% } %>
                    </div>
                    
                    <div class="product-info">
                        <h3><%= product.getName() %></h3>
                        
                        <% if (product.getBrand() != null && !product.getBrand().trim().isEmpty()) { %>
                            <p class="brand">Thương hiệu: <%= product.getBrand() %></p>
                        <% } %>
                        
                        <div class="price">
                            <%= String.format("%,.0f", product.getPrice()) %> VND
                        </div>
                        
                        <% if (product.getDescription() != null && !product.getDescription().trim().isEmpty()) { %>
                            <p class="description">
                                <%= product.getDescription().length() > 100 ? 
                                    product.getDescription().substring(0, 100) + "..." : 
                                    product.getDescription() %>
                            </p>
                        <% } %>
                        
                        <div class="product-actions">
                            <a href="product?action=view&id=<%= product.getId() %>" 
                               class="btn btn-secondary">Chi tiết</a>
                            
                            <% if (product.getStockQuantity() > 0) { %>
                                <form method="post" action="cart" style="display: inline;">
                                    <input type="hidden" name="action" value="add"/>
                                    <input type="hidden" name="productId" value="<%= product.getId() %>"/>
                                    <input type="hidden" name="quantity" value="1"/>
                                    <button type="submit" class="btn btn-primary">🛒 Thêm vào giỏ</button>
                                </form>
                            <% } else { %>
                                <button class="btn btn-disabled" disabled>Hết hàng</button>
                            <% } %>
                        </div>
                    </div>
                </div>
            <% } %>
        </div>
        <%
            } else {
        %>
        <div class="no-products">
            <h3>Không có sản phẩm nào</h3>
            <% if (category != null) { %>
                <p>Danh mục "<%= category.getName() %>" hiện chưa có sản phẩm.</p>
            <% } %>
            <a href="product" class="btn btn-primary">Xem tất cả sản phẩm</a>
        </div>
        <%
            }
        %>
    </div>
    
    <footer>
        <div class="container">
            <p>&copy; 2024 PhoneStore. All rights reserved.</p>
        </div>
    </footer>
    
    <script>
        function sortProducts() {
            const sortBy = document.getElementById('sortBy').value;
            const container = document.getElementById('products-container');
            const products = Array.from(container.children);
            
            products.sort((a, b) => {
                switch(sortBy) {
                    case 'name':
                        return a.dataset.name.localeCompare(b.dataset.name);
                    case 'price-asc':
                        return parseFloat(a.dataset.price) - parseFloat(b.dataset.price);
                    case 'price-desc':
                        return parseFloat(b.dataset.price) - parseFloat(a.dataset.price);
                    default:
                        return 0;
                }
            });
            
            products.forEach(product => container.appendChild(product));
        }
        
        function toggleView(viewType) {
            const container = document.getElementById('products-container');
            const gridBtn = document.getElementById('grid-btn');
            const listBtn = document.getElementById('list-btn');
            
            if (viewType === 'list') {
                container.classList.add('list-view');
                gridBtn.classList.remove('active');
                listBtn.classList.add('active');
            } else {
                container.classList.remove('list-view');
                listBtn.classList.remove('active');
                gridBtn.classList.add('active');
            }
        }
    </script>
</body>
</html>