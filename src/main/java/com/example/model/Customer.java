package com.example.model;

/**
 * Lớp Customer đại diện cho thông tin của một khách hàng trong hệ thống.
 * Bao gồm các thông tin như họ tên, email, mật khẩu, số điện thoại, địa chỉ.
 */
public class Customer {
    // Mã định danh duy nhất của khách hàng (tự tăng trong CSDL)
    private int id;

    // Tên đầy đủ của khách hàng
    private String name;

    // Email của khách hàng (cũng có thể dùng làm tên đăng nhập)
    private String email;

    // Mật khẩu đăng nhập của khách hàng (nên được mã hóa khi lưu trong CSDL)
    private String password;

    // Số điện thoại liên hệ của khách hàng
    private String phone;

    // Địa chỉ nhận hàng hoặc địa chỉ liên hệ của khách hàng
    private String address;

    // Getter và Setter cho ID
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    // Getter và Setter cho tên khách hàng
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    // Getter và Setter cho email
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter và Setter cho mật khẩu
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    // Getter và Setter cho số điện thoại
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Getter và Setter cho địa chỉ
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Phương thức kiểm tra xem khách hàng có phải là quản trị viên hay không.
     * Giả định rằng nếu email là "admin" (không phân biệt hoa thường) thì là admin.
     * @return true nếu là admin, ngược lại là false.
     */
    public boolean isAdmin() {
        return "admin".equalsIgnoreCase(this.email);
    }
}
