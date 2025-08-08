package com.example.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Lớp Order đại diện cho một đơn hàng trong hệ thống.
 * Chứa thông tin chi tiết về khách hàng, địa chỉ giao hàng, 
 * trạng thái đơn hàng, ngày đặt hàng, danh sách sản phẩm, v.v.
 */
public class Order {
    private int id;                         // ID của đơn hàng (khóa chính)
    private String customerName;           // Tên khách hàng đặt hàng
    private String email;                  // Email khách hàng
    private String phone;                  // Số điện thoại khách hàng
    private String address;                // Địa chỉ giao hàng
    private double totalAmount;            // Tổng tiền đơn hàng
    private String status;                 // Trạng thái đơn hàng (vd: Chờ xác nhận, Đã giao, etc.)
    private Date orderDate;                // Ngày đặt hàng
    private List<OrderItem> items;         // Danh sách các sản phẩm trong đơn hàng
    private Timestamp createdAt;           // Thời gian tạo đơn hàng (timestamp trong CSDL)

    // Constructor mặc định
    public Order() {}

    // Các phương thức getter và setter cho từng thuộc tính:

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
