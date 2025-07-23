package com.example.dao;

import com.example.model.Brand;
import com.example.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class BrandDAO {

    public List<String> getAllBrandNames() {
        List<String> brands = new ArrayList<>();
        String sql = "SELECT DISTINCT name FROM brands ORDER BY name ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                brands.add(rs.getString("name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return brands;
    }

    public boolean insertBrandIfNotExists(String brandName) {
        if (brandName == null || brandName.trim().isEmpty()) return false;

        String checkSql = "SELECT COUNT(*) FROM brands WHERE name = ?";
        String insertSql = "INSERT INTO brands (name) VALUES (?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {

            checkStmt.setString(1, brandName);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return false; // Brand already exists
            }

            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, brandName);
                return insertStmt.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
