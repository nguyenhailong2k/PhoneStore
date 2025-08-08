package com.example.dao;

import com.example.model.Brand;
import com.example.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Lớp DAO (Data Access Object) dùng để thao tác với bảng `brands` trong cơ sở dữ liệu.
 */
@SuppressWarnings("unused") // Báo hiệu có thể có phương thức chưa dùng nhưng vẫn giữ lại cho tương lai
public class BrandDAO {

    /**
     * Lấy danh sách tất cả tên thương hiệu (brand name) từ bảng `brands`, không trùng lặp và sắp xếp theo thứ tự ABC.
     * @return danh sách tên thương hiệu.
     */
    public List<String> getAllBrandNames() {
        List<String> brands = new ArrayList<>();  // Danh sách lưu kết quả
        String sql = "SELECT DISTINCT name FROM brands ORDER BY name ASC";  // Truy vấn SQL
        
        // Kết nối DB và thực hiện truy vấn
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            // Duyệt qua từng dòng kết quả và thêm vào danh sách
            while (rs.next()) {
                brands.add(rs.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();  // In lỗi nếu có
        }

        return brands;  // Trả về danh sách tên thương hiệu
    }

    /**
     * Thêm thương hiệu mới vào bảng `brands` nếu nó chưa tồn tại.
     * @param brandName tên thương hiệu cần thêm.
     * @return true nếu thêm thành công, false nếu thất bại hoặc đã tồn tại.
     */
    public boolean insertBrandIfNotExists(String brandName) {
        // Nếu tên thương hiệu rỗng hoặc null thì không xử lý
        if (brandName == null || brandName.trim().isEmpty()) return false;

        String checkSql = "SELECT COUNT(*) FROM brands WHERE name = ?";  // Kiểm tra tồn tại
        String insertSql = "INSERT INTO brands (name) VALUES (?)";       // Câu lệnh thêm

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            // Gán tham số tên thương hiệu vào câu truy vấn
            checkStmt.setString(1, brandName);
            ResultSet rs = checkStmt.executeQuery();

            // Nếu đã tồn tại (COUNT > 0) thì không thêm nữa
            if (rs.next() && rs.getInt(1) > 0) {
                return false;
            }

            // Nếu chưa tồn tại thì thực hiện thêm
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, brandName);
                return insertStmt.executeUpdate() > 0;  // Trả true nếu thêm thành công
            }

        } catch (SQLException e) {
            e.printStackTrace();  // In lỗi nếu có
        }

        return false;  // Trường hợp lỗi hoặc không thêm được
    }
}
