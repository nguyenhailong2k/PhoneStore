package com.example.model;

/**
 * Lớp đại diện cho đối tượng Admin (quản trị viên) trong hệ thống.
 * Chứa các thuộc tính cơ bản như id, tên đăng nhập và mật khẩu.
 */
public class Admin {
    // Thuộc tính định danh duy nhất cho mỗi admin
    private int id;
    
    // Tên đăng nhập của admin
    private String username;
    
    // Mật khẩu của admin
    private String password;
    
    /**
     * Constructor không tham số.
     * Dùng khi cần tạo đối tượng Admin trống và gán giá trị sau.
     */
    public Admin() {}

    /**
     * Constructor khởi tạo với tên đăng nhập và mật khẩu.
     * @param username Tên đăng nhập của admin
     * @param password Mật khẩu của admin
     */
    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter và Setter cho thuộc tính id
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    // Getter và Setter cho thuộc tính username
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    // Getter và Setter cho thuộc tính password
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
