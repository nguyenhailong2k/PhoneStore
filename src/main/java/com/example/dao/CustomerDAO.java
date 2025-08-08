package com.example.dao;

import com.example.model.Customer;
import com.example.util.DatabaseConnection;
import java.sql.*;

// Lớp CustomerDAO dùng để thao tác với bảng `customers` trong cơ sở dữ liệu
public class CustomerDAO {

    // Đăng ký tài khoản khách hàng mới
    public boolean register(Customer customer) {
        String sql = "INSERT INTO customers (name, email, password, phone, address) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            // Gán các giá trị từ đối tượng Customer vào câu lệnh SQL
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getPassword()); // Lưu ý: mật khẩu nên mã hóa (hash) trong thực tế
            ps.setString(4, customer.getPhone());
            ps.setString(5, customer.getAddress());

            // Thực thi lệnh SQL và trả về true nếu thêm thành công
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi nếu xảy ra
        }
        return false;
    }

    // Đăng nhập: kiểm tra email và password, trả về thông tin khách hàng nếu đúng
    public Customer login(String email, String password) {
        String sql = "SELECT * FROM customers WHERE email=? AND password=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            // Nếu có bản ghi phù hợp, tạo và trả về đối tượng Customer
            if (rs.next()) {
                Customer c = new Customer();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
                c.setEmail(rs.getString("email"));
                c.setPhone(rs.getString("phone"));
                c.setAddress(rs.getString("address"));
                return c;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Trả về null nếu không tìm thấy
    }

    // Một phương thức đăng nhập khác tương tự login(), có thêm lấy password
    public Customer checkLogin(String email, String password) {
        String sql = "SELECT * FROM customers WHERE email = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            // Nếu tìm thấy khách hàng, trả về đối tượng chứa đầy đủ thông tin
            if (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setName(rs.getString("name"));
                customer.setEmail(rs.getString("email"));
                customer.setPassword(rs.getString("password")); // Lấy luôn password
                return customer;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Kiểm tra xem email đã tồn tại trong cơ sở dữ liệu chưa
    public boolean isEmailExists(String email) {
        String sql = "SELECT * FROM customers WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            return rs.next(); // Trả về true nếu có kết quả
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Thêm một khách hàng mới vào cơ sở dữ liệu (dùng cho admin hoặc đăng ký)
    public void addCustomer(Customer customer) {
        String sql = "INSERT INTO customers(name, email, password, phone, address) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, customer.getName());
            ps.setString(2, customer.getEmail());
            ps.setString(3, customer.getPassword());
            ps.setString(4, customer.getPhone());
            ps.setString(5, customer.getAddress());

            ps.executeUpdate(); // Thực hiện thêm khách hàng
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
