package com.example.dao;

import com.example.model.Order;
import com.example.model.OrderItem;
import com.example.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Lớp DAO để thao tác với bảng `orders` và `order_items` trong cơ sở dữ liệu
public class OrderDAO {

    // Tạo một đơn hàng mới, trả về ID của đơn hàng vừa tạo
    public int createOrder(Order order) {
        String sql = "INSERT INTO orders(customer_name, email, phone, address, total_amount, status) VALUES(?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, order.getCustomerName());
            ps.setString(2, order.getEmail());
            ps.setString(3, order.getPhone());
            ps.setString(4, order.getAddress());
            ps.setDouble(5, order.getTotalAmount());
            ps.setString(6, "PENDING"); // Trạng thái mặc định khi tạo mới

            int result = ps.executeUpdate();
            if (result > 0) {
                ResultSet rs = ps.getGeneratedKeys(); // Lấy ID sinh tự động
                if (rs.next()) {
                    return rs.getInt(1); // Trả về ID đơn hàng
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Trả về 0 nếu thất bại
    }

    // Thêm một sản phẩm vào chi tiết đơn hàng
    public boolean addOrderItem(OrderItem item) {
        String sql = "INSERT INTO order_items(order_id, product_id, product_name, price, quantity, subtotal) VALUES(?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, item.getOrderId());
            ps.setInt(2, item.getProductId());
            ps.setString(3, item.getProductName());
            ps.setDouble(4, item.getPrice());
            ps.setInt(5, item.getQuantity());
            ps.setDouble(6, item.getSubtotal());

            return ps.executeUpdate() > 0; // Trả về true nếu thêm thành công
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lấy danh sách tất cả đơn hàng (sắp xếp theo ngày giảm dần)
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders ORDER BY order_date DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setCustomerName(rs.getString("customer_name"));
                order.setEmail(rs.getString("email"));
                order.setPhone(rs.getString("phone"));
                order.setAddress(rs.getString("address"));
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setStatus(rs.getString("status"));
                order.setOrderDate(rs.getTimestamp("order_date"));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    // Lấy thông tin một đơn hàng cụ thể theo ID (bao gồm cả chi tiết đơn hàng)
    public Order getOrderById(int id) {
        String sql = "SELECT * FROM orders WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setCustomerName(rs.getString("customer_name"));
                order.setEmail(rs.getString("email"));
                order.setPhone(rs.getString("phone"));
                order.setAddress(rs.getString("address"));
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setStatus(rs.getString("status"));
                order.setOrderDate(rs.getTimestamp("order_date"));

                // Lấy danh sách sản phẩm trong đơn hàng
                order.setItems(getOrderItems(id));
                return order;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Lấy danh sách chi tiết sản phẩm của một đơn hàng cụ thể
    public List<OrderItem> getOrderItems(int orderId) {
        List<OrderItem> items = new ArrayList<>();
        String sql = "SELECT * FROM order_items WHERE order_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrderItem item = new OrderItem();
                item.setId(rs.getInt("id"));
                item.setOrderId(rs.getInt("order_id"));
                item.setProductId(rs.getInt("product_id"));
                item.setProductName(rs.getString("product_name"));
                item.setPrice(rs.getDouble("price"));
                item.setQuantity(rs.getInt("quantity"));
                item.setSubtotal(rs.getDouble("subtotal"));
                items.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    // Cập nhật trạng thái của đơn hàng (VD: PENDING → COMPLETED)
    public boolean updateOrderStatus(int orderId, String status) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, orderId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa đơn hàng theo ID, bao gồm cả chi tiết sản phẩm
    public boolean deleteOrderById(int orderId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false); // Bắt đầu giao dịch

            try (
                PreparedStatement psItems = conn.prepareStatement("DELETE FROM order_items WHERE order_id = ?");
                PreparedStatement psOrder = conn.prepareStatement("DELETE FROM orders WHERE id = ?")
            ) {
                psItems.setInt(1, orderId);
                psItems.executeUpdate(); // Xóa chi tiết đơn hàng

                psOrder.setInt(1, orderId);
                int rowsAffected = psOrder.executeUpdate(); // Xóa đơn hàng chính

                conn.commit(); // Commit nếu mọi thứ OK
                return rowsAffected > 0;
            } catch (SQLException e) {
                conn.rollback(); // Hoàn tác nếu có lỗi
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lấy danh sách đơn hàng của một khách hàng theo email
    public List<Order> getOrdersByEmail(String email) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE email = ? ORDER BY created_at DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setCustomerName(rs.getString("customer_name"));
                order.setEmail(rs.getString("email"));
                order.setPhone(rs.getString("phone"));
                order.setAddress(rs.getString("address"));
                order.setTotalAmount(rs.getDouble("total_amount"));
                order.setStatus(rs.getString("status"));
                order.setCreatedAt(rs.getTimestamp("created_at"));
                orders.add(order);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return orders;
    }

}
