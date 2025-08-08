package com.example.model;

/**
 * Lớp Product đại diện cho một sản phẩm trong hệ thống.
 * Bao gồm các thuộc tính như: tên, giá, ảnh, số lượng tồn kho, danh mục, hãng, mô tả, thông số kỹ thuật, và trạng thái hoạt động.
 */
public class Product {
    // ID sản phẩm (duy nhất)
    private int id;

    // Tên sản phẩm
    private String name;

    // Giá bán của sản phẩm
    private double price;

    // Tên hoặc đường dẫn ảnh của sản phẩm
    private String image;

    // Số lượng sản phẩm còn trong kho
    private int stockQuantity;

    // ID danh mục sản phẩm (Category)
    private int categoryId;

    // Tên hãng sản xuất (brand) của sản phẩm
    private String brand;

    // Mô tả ngắn về sản phẩm
    private String description;

    // Thông số kỹ thuật của sản phẩm
    private String specs;

    // Trạng thái hoạt động của sản phẩm (mặc định là true - còn bán)
    private boolean active = true;

    // Constructor mặc định
    public Product() {}

    // Getter và Setter cho từng thuộc tính

    // ID sản phẩm
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    // Tên sản phẩm
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    // Giá sản phẩm
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    // Tên/đường dẫn ảnh sản phẩm
    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    // Số lượng còn trong kho
    public int getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(int stockQuantity) { this.stockQuantity = stockQuantity; }

    // ID của danh mục sản phẩm
    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    // Hãng sản xuất
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    // Mô tả sản phẩm
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    // Thông số kỹ thuật sản phẩm
    public String getSpecs() { return specs; }
    public void setSpecs(String specs) { this.specs = specs; }

    // Trạng thái còn bán (active = true) hay ngừng bán (active = false)
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
