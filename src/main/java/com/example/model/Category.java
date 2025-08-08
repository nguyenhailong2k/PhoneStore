package com.example.model;

/**
 * Lớp Category đại diện cho một danh mục sản phẩm trong hệ thống.
 * Mỗi danh mục bao gồm tên, mô tả, hình ảnh, trạng thái hoạt động,...
 */
public class Category {
    // Mã định danh duy nhất của danh mục
    private int id;
    
    // Tên của danh mục (ví dụ: Điện thoại, Laptop)
    private String name;
    
    // Mô tả chi tiết về danh mục
    private String description;
    
    // Đường dẫn hình ảnh đại diện cho danh mục
    private String image;
    
    // Trạng thái hoạt động của danh mục (true = đang hoạt động, false = đã ẩn)
    private boolean active;

    /**
     * Constructor mặc định (không tham số)
     */
    public Category() {}

    /**
     * Constructor khởi tạo danh mục với đầy đủ thông tin (trừ ID – thường tự sinh)
     * @param name Tên danh mục
     * @param description Mô tả danh mục
     * @param image Hình ảnh đại diện danh mục
     * @param active Trạng thái hoạt động
     */
    public Category(String name, String description, String image, boolean active) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.active = active;
    }

    // Các phương thức getter và setter

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
