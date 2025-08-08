package com.example.dao;

import com.example.model.Admin;
import com.example.util.DatabaseConnection;
import java.sql.*;

/**
 * Lớp DAO (Data Access Object) dùng để truy xuất dữ liệu liên quan đến bảng `admins` trong cơ sở dữ liệu.
 */
public class AdminDAO {

    /**
     * Phương thức kiểm tra tài khoản admin có hợp lệ hay không.
     * @param username tên đăng nhập (username) nhập từ form.
     * @param password mật khẩu (password) nhập từ form.
     * @return đối tượng Admin nếu hợp lệ, ngược lại trả về null.
     */
    public Admin validateAdmin(String username, String password) {
        // Câu truy vấn SQL kiểm tra thông tin đăng nhập
        String sql = "SELECT * FROM admins WHERE username=? AND password=?";
        
        // Sử dụng try-with-resources để tự động đóng kết nối
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            // Truyền tham số vào câu lệnh SQL
            ps.setString(1, username);
            ps.setString(2, password);
            
            // Thực thi câu lệnh truy vấn
            ResultSet rs = ps.executeQuery();
            
            // Nếu có bản ghi phù hợp, tạo đối tượng Admin và trả về
            if (rs.next()) {
                Admin admin = new Admin();
                admin.setId(rs.getInt("id"));
                admin.setUsername(rs.getString("username"));
                admin.setPassword(rs.getString("password"));
                return admin;
            }
        } catch (SQLException e) {
            e.printStackTrace();  // In ra lỗi nếu có vấn đề với cơ sở dữ liệu
        }

        // Trả về null nếu không tìm thấy tài khoản phù hợp
        return null;
    }
}
