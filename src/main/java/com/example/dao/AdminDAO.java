package com.example.dao;

import com.example.model.Admin;
import com.example.util.DatabaseConnection;
import java.sql.*;

public class AdminDAO {
    
    public Admin validateAdmin(String username, String password) {
        String sql = "SELECT * FROM admins WHERE username=? AND password=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                Admin admin = new Admin();
                admin.setId(rs.getInt("id"));
                admin.setUsername(rs.getString("username"));
                admin.setPassword(rs.getString("password"));
                return admin;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}