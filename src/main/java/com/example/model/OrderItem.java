package com.example.model;

/**
 * Lớp OrderItem đại diện cho một mục (sản phẩm) cụ thể trong một đơn hàng.
 * Mỗi OrderItem liên kết với một sản phẩm, số lượng, đơn giá và tổng phụ (subtotal).
 */
public class OrderItem {
    // ID của mục đơn hàng (OrderItem)
    private int id;

    // ID của đơn hàng chứa mục này
    private int orderId;

    // ID của sản phẩm được đặt trong mục này
    private int productId;

    // Tên sản phẩm (được lưu trữ để hiển thị, tránh join bảng sản phẩm)
    private String productName;

    // Giá của sản phẩm tại thời điểm đặt hàng
    private double price;

    // Số lượng sản phẩm đã đặt
    private int quantity;

    // Thành tiền cho mục này (price * quantity)
    private double subtotal;

    // Constructor mặc định
    public OrderItem() {}

    // Getter & Setter cho từng thuộc tính

    // ID mục đơn hàng
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    // ID đơn hàng chứa mục này
    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }

    // ID sản phẩm
    public int getProductId() { return productId; }
    public void setProductId(int productId) { this.productId = productId; }

    // Tên sản phẩm
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    // Giá sản phẩm
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    // Số lượng sản phẩm
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    // Tổng phụ (subtotal) = price * quantity
    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
}
