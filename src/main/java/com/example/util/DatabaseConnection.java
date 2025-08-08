package com.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Lớp tiện ích để quản lý kết nối đến cơ sở dữ liệu MySQL.
 * Sử dụng mô hình Singleton để cấp phát kết nối mỗi khi cần.
 */
public class DatabaseConnection {

    // URL kết nối đến cơ sở dữ liệu (tên DB là "phonestore", chạy ở cổng 4406)
    private static final String URL = "jdbc:mysql://localhost:4406/phonestore";

    // Tên người dùng để kết nối đến MySQL
    private static final String USERNAME = "root";

    // Mật khẩu tương ứng với USERNAME
    private static final String PASSWORD = "Ab@123456";

    /**
     * Khối static dùng để nạp driver MySQL khi class được load vào bộ nhớ.
     * Driver này cần thiết để kết nối đến MySQL thông qua JDBC.
     */
    static {
        try {
            // Nạp driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // In lỗi nếu không tìm thấy driver
            e.printStackTrace();
        }
    }

    /**
     * Phương thức cung cấp đối tượng Connection để làm việc với cơ sở dữ liệu.
     * @return Connection đối tượng kết nối đến MySQL
     * @throws SQLException nếu không kết nối được với cơ sở dữ liệu
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
