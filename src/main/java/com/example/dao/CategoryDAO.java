package com.example.dao;

import com.example.model.Category;
import com.example.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    /**
     * Lấy danh sách tất cả các danh mục đang hoạt động (active = 1)
     * @return List<Category> danh sách danh mục
     */
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM categories WHERE active = 1 ORDER BY name";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // Duyệt kết quả và thêm vào danh sách
            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                category.setDescription(rs.getString("description"));
                category.setImage(rs.getString("image"));
                category.setActive(rs.getBoolean("active"));
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    /**
     * Lấy thông tin danh mục theo ID
     * @param id mã danh mục
     * @return Category hoặc null nếu không tìm thấy
     */
    public Category getCategoryById(int id) {
        String sql = "SELECT * FROM categories WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Category c = new Category();
                c.setId(rs.getInt("id"));
                c.setName(rs.getString("name"));
                c.setDescription(rs.getString("description"));
                c.setImage(rs.getString("image"));
                return c;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Thêm mới một danh mục vào cơ sở dữ liệu
     * @param category đối tượng danh mục
     * @return true nếu thêm thành công, ngược lại false
     */
    public boolean addCategory(Category category) {
        String sql = "INSERT INTO categories(name, description, image, active) VALUES(?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, category.getName());
            ps.setString(2, category.getDescription());
            ps.setString(3, category.getImage());
            ps.setBoolean(4, category.isActive());

            return ps.executeUpdate() > 0; // Trả về true nếu có ít nhất 1 dòng được chèn
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Cập nhật thông tin của một danh mục
     * @param category đối tượng danh mục chứa thông tin cần cập nhật
     * @return true nếu cập nhật thành công, ngược lại false
     */
    public boolean updateCategory(Category category) {
        String sql = "UPDATE categories SET name=?, description=?, image=?, active=? WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, category.getName());
            ps.setString(2, category.getDescription());
            ps.setString(3, category.getImage());
            ps.setBoolean(4, category.isActive());
            ps.setInt(5, category.getId());

            return ps.executeUpdate() > 0; // Trả về true nếu có dòng được cập nhật
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Xóa mềm danh mục bằng cách đặt active = 0
     * @param id mã danh mục
     * @return true nếu cập nhật thành công
     */
    public boolean deleteCategory(int id) {
        String sql = "UPDATE categories SET active = 0 WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0; // Trả về true nếu có dòng bị "xóa"
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
