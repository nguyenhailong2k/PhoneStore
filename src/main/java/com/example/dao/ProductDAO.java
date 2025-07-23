package com.example.dao;

import com.example.model.Product;
import com.example.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY id DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

               while (rs.next()) {
                   Product product = new Product();
                   product.setId(rs.getInt("id"));
                   product.setName(rs.getString("name"));
                   product.setPrice(rs.getDouble("price"));
                   product.setImage(rs.getString("image"));
                   product.setStockQuantity(rs.getInt("stock_quantity")); // ✅ PHẢI CÓ DÒNG NÀY
                   // thêm các trường khác nếu cần
                   products.add(product);
               }

           } catch (Exception e) {
               e.printStackTrace();
           }

           return products;
       }
    
    public List<Product> searchProducts(String keyword) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE (name LIKE ? OR description LIKE ? OR brand LIKE ?) AND active = 1 ORDER BY name";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            String searchTerm = "%" + keyword + "%";
            ps.setString(1, searchTerm);
            ps.setString(2, searchTerm);
            ps.setString(3, searchTerm);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setImage(rs.getString("image"));
                product.setDescription(rs.getString("description"));
                product.setCategoryId(rs.getInt("category_id"));
                product.setBrand(rs.getString("brand"));
                product.setStockQuantity(rs.getInt("stock_quantity"));
                product.setSpecs(rs.getString("specs"));
                product.setActive(rs.getBoolean("active"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    
    public Product getProductById(int id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getDouble("price"));
                p.setImage(rs.getString("image"));
                p.setBrand(rs.getString("brand"));
                p.setDescription(rs.getString("description"));
                p.setSpecs(rs.getString("specs"));
                p.setStockQuantity(rs.getInt("stock_quantity"));
                return p;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean addProduct(Product product) {
        String sql = "INSERT INTO products (name, price, image, stock_quantity, category_id, brand, description, specs) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setString(3, product.getImage());
            stmt.setInt(4, product.getStockQuantity());
            stmt.setInt(5, product.getCategoryId());
            stmt.setString(6, product.getBrand());
            stmt.setString(7, product.getDescription());
            stmt.setString(8, product.getSpecs());

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    
    public boolean updateProduct(Product product) {
        String sql = "UPDATE products SET name = ?, price = ?, image = ?, stock_quantity = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setString(3, product.getImage());
            stmt.setInt(4, product.getStockQuantity());
            stmt.setInt(5, product.getId());
            
            
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(">> DAO.updateProduct - tồn kho: " + product.getStockQuantity());

        return false;
    }

    public boolean updateProductWithoutImage(Product product) {
        String sql = "UPDATE products SET name = ?, price = ?, stock_quantity = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getStockQuantity());
            stmt.setInt(4, product.getId());

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(">> DAO.updateProductWithoutImage - tồn kho: " + product.getStockQuantity());

        return false;
    }


    public boolean deleteProduct(int id) {
        String sql = "DELETE FROM products WHERE id=?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public List<Product> getProductsByCategory(int categoryId) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE category_id = ? AND active = 1 ORDER BY name";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setImage(rs.getString("image"));
                product.setDescription(rs.getString("description"));
                product.setCategoryId(rs.getInt("category_id"));
                product.setBrand(rs.getString("brand"));
                product.setStockQuantity(rs.getInt("stock_quantity"));
                product.setSpecs(rs.getString("specs"));
                product.setActive(rs.getBoolean("active"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    
    public List<Product> getProductsByPriceRange(double minPrice, double maxPrice) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE price BETWEEN ? AND ? AND active = 1 ORDER BY price";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setDouble(1, minPrice);
            ps.setDouble(2, maxPrice);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getInt("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setImage(rs.getString("image"));
                product.setDescription(rs.getString("description"));
                product.setCategoryId(rs.getInt("category_id"));
                product.setBrand(rs.getString("brand"));
                product.setStockQuantity(rs.getInt("stock_quantity"));
                product.setSpecs(rs.getString("specs"));
                product.setActive(rs.getBoolean("active"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public boolean updateStock(int productId, int newStock) {
        String sql = "UPDATE products SET stock_quantity = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, newStock);
            ps.setInt(2, productId);
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

	public List<Product> getProductsByCategoryId(int categoryId) {
    List<Product> list = new ArrayList<>();
    String sql = "SELECT * FROM products WHERE category_id = ?";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, categoryId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Product p = new Product();
            p.setId(rs.getInt("id"));
            p.setName(rs.getString("name"));
            p.setPrice(rs.getDouble("price"));
            p.setImage(rs.getString("image"));
            list.add(p);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}

	// Sắp xếp theo giá tăng dần hoặc giảm dần
	public List<Product> getProductsSortedByPrice(String sort) {
	    List<Product> products = new ArrayList<>();
	    String sql = "SELECT * FROM products WHERE active = 1 ORDER BY price " + ("desc".equalsIgnoreCase(sort) ? "DESC" : "ASC");
	    
	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {
	        while (rs.next()) {
	            Product product = new Product();
	            product.setId(rs.getInt("id"));
	            product.setName(rs.getString("name"));
	            product.setPrice(rs.getDouble("price"));
	            product.setImage(rs.getString("image"));
	            product.setDescription(rs.getString("description"));
	            product.setCategoryId(rs.getInt("category_id"));
	            product.setBrand(rs.getString("brand"));
	            product.setStockQuantity(rs.getInt("stock_quantity"));
	            product.setSpecs(rs.getString("specs"));
	            product.setActive(rs.getBoolean("active"));
	            products.add(product);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return products;
	}

	public List<Product> filterProducts(String keyword, Double minPrice, Double maxPrice, String sort) {
	    List<Product> products = new ArrayList<>();
	    StringBuilder sql = new StringBuilder("SELECT * FROM products WHERE 1=1");

	    List<Object> params = new ArrayList<>();

	    if (keyword != null && !keyword.trim().isEmpty()) {
	        sql.append(" AND (name LIKE ? OR description LIKE ? OR brand LIKE ?)");
	        String kw = "%" + keyword.trim() + "%";
	        params.add(kw);
	        params.add(kw);
	        params.add(kw);
	    }

	    if (minPrice != null && maxPrice != null && minPrice <= maxPrice) {
	        sql.append(" AND price BETWEEN ? AND ?");
	        params.add(minPrice);
	        params.add(maxPrice);
	    }

	    if ("asc".equalsIgnoreCase(sort)) {
	        sql.append(" ORDER BY price ASC");
	    } else if ("desc".equalsIgnoreCase(sort)) {
	        sql.append(" ORDER BY price DESC");
	    } else {
	        sql.append(" ORDER BY id DESC");
	    }

	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql.toString())) {

	        for (int i = 0; i < params.size(); i++) {
	            ps.setObject(i + 1, params.get(i));
	        }

	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            Product product = new Product();
	            product.setId(rs.getInt("id"));
	            product.setName(rs.getString("name"));
	            product.setPrice(rs.getDouble("price"));
	            product.setImage(rs.getString("image"));
	            product.setDescription(rs.getString("description"));
	            product.setCategoryId(rs.getInt("category_id"));
	            product.setBrand(rs.getString("brand"));
	            product.setStockQuantity(rs.getInt("stock_quantity"));
	            product.setSpecs(rs.getString("specs"));
	            product.setActive(true);
	            products.add(product);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return products;
	}

	public List<Product> filterProductsWithCategory(String keyword, Double minPrice, Double maxPrice, String sort, Integer categoryId) {
	    List<Product> products = new ArrayList<>();
	    StringBuilder sql = new StringBuilder("SELECT * FROM products WHERE active = 1");

	    List<Object> params = new ArrayList<>();

	    if (keyword != null && !keyword.trim().isEmpty()) {
	        sql.append(" AND (name LIKE ? OR description LIKE ? OR brand LIKE ?)");
	        String kw = "%" + keyword.trim() + "%";
	        params.add(kw);
	        params.add(kw);
	        params.add(kw);
	    }

	    if (minPrice != null && maxPrice != null && minPrice <= maxPrice) {
	        sql.append(" AND price BETWEEN ? AND ?");
	        params.add(minPrice);
	        params.add(maxPrice);
	    }

	    if (categoryId != null) {
	        sql.append(" AND category_id = ?");
	        params.add(categoryId);
	    }

	    if ("asc".equalsIgnoreCase(sort)) {
	        sql.append(" ORDER BY price ASC");
	    } else if ("desc".equalsIgnoreCase(sort)) {
	        sql.append(" ORDER BY price DESC");
	    } else {
	        sql.append(" ORDER BY id DESC");
	    }

	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql.toString())) {

	        for (int i = 0; i < params.size(); i++) {
	            ps.setObject(i + 1, params.get(i));
	        }

	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            Product product = new Product();
	            product.setId(rs.getInt("id"));
	            product.setName(rs.getString("name"));
	            product.setPrice(rs.getDouble("price"));
	            product.setImage(rs.getString("image"));
	            product.setDescription(rs.getString("description"));
	            product.setCategoryId(rs.getInt("category_id"));
	            product.setBrand(rs.getString("brand"));
	            product.setStockQuantity(rs.getInt("stock_quantity"));
	            product.setSpecs(rs.getString("specs"));
	            product.setActive(rs.getBoolean("active"));
	            products.add(product);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return products;
	}

}