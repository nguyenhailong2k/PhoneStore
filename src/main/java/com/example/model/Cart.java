package com.example.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Lớp Cart đại diện cho giỏ hàng của người dùng.
 * Lưu trữ danh sách các sản phẩm đã chọn (CartItem) và cung cấp các thao tác thêm, xóa, cập nhật số lượng, tính tổng tiền,...
 */
public class Cart {
    // Danh sách các mục (CartItem) trong giỏ hàng
    private List<CartItem> items;

    /**
     * Constructor khởi tạo một giỏ hàng mới, rỗng.
     */
    public Cart() {
        this.items = new ArrayList<>();
    }

    /**
     * Thêm sản phẩm vào giỏ hàng. Nếu sản phẩm đã tồn tại, tăng số lượng lên.
     * @param product Sản phẩm cần thêm
     * @param quantity Số lượng cần thêm
     */
    public void addItem(Product product, int quantity) {
        for (CartItem item : items) {
            if (item.getProduct().getId() == product.getId()) {
                // Nếu sản phẩm đã có trong giỏ, cập nhật số lượng
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        // Nếu sản phẩm chưa có, thêm mới vào giỏ
        items.add(new CartItem(product, quantity));
    }

    /**
     * Xóa sản phẩm khỏi giỏ hàng dựa vào ID sản phẩm.
     * @param productId ID của sản phẩm cần xóa
     */
    public void removeItem(int productId) {
        items.removeIf(item -> item.getProduct().getId() == productId);
    }

    /**
     * Cập nhật số lượng sản phẩm trong giỏ hàng.
     * @param productId ID của sản phẩm cần cập nhật
     * @param quantity Số lượng mới
     */
    public void updateQuantity(int productId, int quantity) {
        for (CartItem item : items) {
            if (item.getProduct().getId() == productId) {
                item.setQuantity(quantity);
                return;
            }
        }
    }

    /**
     * Tính tổng số tiền của toàn bộ sản phẩm trong giỏ hàng.
     * @return Tổng giá tiền
     */
    public double getTotalPrice() {
        return items.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }

    /**
     * Tính tổng số lượng sản phẩm trong giỏ hàng.
     * @return Tổng số sản phẩm
     */
    public int getTotalItems() {
        return items.stream().mapToInt(CartItem::getQuantity).sum();
    }

    /**
     * Lấy danh sách tất cả các mục trong giỏ hàng.
     * @return Danh sách CartItem
     */
    public List<CartItem> getItems() {
        return items;
    }

    /**
     * Xóa toàn bộ giỏ hàng (xóa tất cả sản phẩm đã chọn).
     */
    public void clear() {
        items.clear();
    }
}
