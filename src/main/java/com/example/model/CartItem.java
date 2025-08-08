package com.example.model;

/**
 * Lớp CartItem đại diện cho một mục trong giỏ hàng.
 * Mỗi mục gồm một sản phẩm (Product) và số lượng tương ứng.
 */
public class CartItem {
    // Sản phẩm trong mục giỏ hàng
    private Product product;
    
    // Số lượng sản phẩm được thêm vào giỏ
    private int quantity;
    
    /**
     * Constructor mặc định (không truyền tham số).
     */
    public CartItem() {}
    
    /**
     * Constructor khởi tạo CartItem với sản phẩm và số lượng cụ thể.
     * @param product Sản phẩm
     * @param quantity Số lượng sản phẩm
     */
    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // Getter và Setter cho product
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    // Getter và Setter cho quantity
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    /**
     * Tính tổng tiền (thành tiền) của mục này = đơn giá × số lượng.
     * @return Giá trị thành tiền
     */
    public double getSubtotal() {
        return product.getPrice() * quantity;
    }
}
