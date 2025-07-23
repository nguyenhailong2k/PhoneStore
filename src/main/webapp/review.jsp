<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Đánh giá sản phẩm - PhoneStore</title>
    <link rel="stylesheet" href="css/style.css?v=<%= System.currentTimeMillis() %>">

</head>
<body>
    <header><h1>📝 Gửi đánh giá sản phẩm</h1></header>
    <div class="container">
        <form action="review" method="post">
            <input type="hidden" name="productId" value="<%= request.getParameter("productId") %>" />
            <div class="form-group">
                <label>Họ tên:</label>
                <input type="text" name="name" required />
            </div>
            <div class="form-group">
                <label>Nội dung đánh giá:</label>
                <textarea name="content" required></textarea>
            </div>
            <button type="submit" class="btn btn-primary">📩 Gửi đánh giá</button>
        </form>
    </div>
</body>
</html>
