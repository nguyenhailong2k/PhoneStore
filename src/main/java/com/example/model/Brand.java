package com.example.model;

/**
 * Lớp đại diện cho đối tượng Thương hiệu (Brand) trong hệ thống.
 * Mỗi thương hiệu có một mã định danh (id) và tên (name).
 */
public class Brand {
    // Mã định danh duy nhất của thương hiệu
    private int id;

    // Tên của thương hiệu
    private String name;

    /**
     * Constructor không tham số.
     * Dùng để khởi tạo đối tượng Brand trống.
     */
    public Brand() {}

    /**
     * Constructor khởi tạo đối tượng Brand với id và tên.
     * @param id Mã định danh của thương hiệu
     * @param name Tên của thương hiệu
     */
    public Brand(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter và Setter cho thuộc tính id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter và Setter cho thuộc tính name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
