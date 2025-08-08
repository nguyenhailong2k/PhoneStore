package com.example.controller;

// Import các lớp DAO và model cần thiết
import com.example.dao.BrandDAO;
import com.example.dao.ProductDAO;
import com.example.model.Product;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.*;
import java.nio.file.*;

// Servlet xử lý chỉnh sửa sản phẩm, ánh xạ tới đường dẫn "/edit"
@WebServlet("/edit")
@MultipartConfig // Cho phép xử lý upload file
public class EditProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private ProductDAO productDAO; // Đối tượng DAO xử lý dữ liệu sản phẩm
    private BrandDAO brandDAO;     // Đối tượng DAO xử lý thương hiệu

    // Phương thức khởi tạo servlet, gọi khi servlet được load lần đầu
    @Override
    public void init() {
        productDAO = new ProductDAO();
        brandDAO = new BrandDAO();
    }

    // Xử lý yêu cầu GET để hiển thị form chỉnh sửa sản phẩm
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            // Lấy id sản phẩm từ URL
            int id = Integer.parseInt(req.getParameter("id"));
            // Lấy thông tin sản phẩm từ DB
            Product product = productDAO.getProductById(id);
            if (product != null) {
                // Nếu có sản phẩm, đính kèm dữ liệu và chuyển tiếp tới trang JSP chỉnh sửa
                req.setAttribute("product", product);
                req.getRequestDispatcher("editProduct.jsp").forward(req, resp);
            } else {
                // Nếu không có, quay lại trang admin
                resp.sendRedirect("admin");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("admin");
        }
    }

    // Xử lý yêu cầu POST khi người dùng submit form cập nhật sản phẩm
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            // Lấy dữ liệu từ form
            int id = Integer.parseInt(req.getParameter("id"));
            String name = req.getParameter("name");
            double price = Double.parseDouble(req.getParameter("price").replaceAll(",", ""));
            int stock = Integer.parseInt(req.getParameter("stock"));
            int categoryId = Integer.parseInt(req.getParameter("categoryId"));
            String brand = req.getParameter("brand");
            String brandNew = req.getParameter("brandNew");
            String description = req.getParameter("description");
            String specs = req.getParameter("specs");

            // Nếu người dùng nhập thương hiệu mới, thêm vào DB nếu chưa có
            if (brandNew != null && !brandNew.trim().isEmpty()) {
                brandDAO.insertBrandIfNotExists(brandNew);
                brand = brandNew; // Sử dụng thương hiệu mới
            }

            // Lấy file ảnh nếu có
            Part filePart = req.getPart("image");

            // Tạo đối tượng sản phẩm và thiết lập thông tin
            Product product = new Product();
            product.setId(id);
            product.setName(name);
            product.setPrice(price);
            product.setStockQuantity(stock);
            product.setCategoryId(categoryId);
            product.setBrand(brand);
            product.setDescription(description);
            product.setSpecs(specs);

            boolean success;

            // Nếu có file ảnh mới được upload
            if (filePart != null && filePart.getSize() > 0) {
                // Tạo tên file duy nhất (timestamp + tên gốc)
                String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                String uniqueFileName = System.currentTimeMillis() + "_" + originalFileName;

                // Tạo thư mục chứa ảnh nếu chưa có
                String imagesDirPath = req.getServletContext().getRealPath("/images");
                File imagesDir = new File(imagesDirPath);
                if (!imagesDir.exists()) {
                    imagesDir.mkdirs();
                }

                // Lưu file ảnh vào thư mục
                File file = new File(imagesDir, uniqueFileName);
                try (InputStream input = filePart.getInputStream()) {
                    Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }

                // Gán ảnh cho sản phẩm
                product.setImage(uniqueFileName);
                // Cập nhật sản phẩm (có ảnh)
                success = productDAO.updateProduct(product);
            } else {
                // Cập nhật sản phẩm không thay đổi ảnh
                success = productDAO.updateProductWithoutImage(product);
            }

            // Thông báo kết quả cập nhật
            if (success) {
                req.getSession().setAttribute("message", "Cập nhật sản phẩm thành công!");
            } else {
                req.getSession().setAttribute("error", "Có lỗi xảy ra khi cập nhật sản phẩm!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            req.getSession().setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }

        // Sau khi xử lý, chuyển hướng về trang quản lý admin
        resp.sendRedirect("admin");
    }
}
