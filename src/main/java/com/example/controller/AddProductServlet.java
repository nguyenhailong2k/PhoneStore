// Khai báo package và các thư viện cần thiết
package com.example.controller;

import com.example.dao.BrandDAO;
import com.example.dao.ProductDAO;
import com.example.model.Product;
import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.nio.file.*;

// Servlet xử lý yêu cầu thêm sản phẩm, ánh xạ tới đường dẫn "/add"
@WebServlet("/add")
@MultipartConfig  // Cho phép xử lý dữ liệu upload file (multipart/form-data)
public class AddProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    // Khai báo DAO để thao tác với CSDL
    private ProductDAO productDAO;
    private BrandDAO brandDAO;

    // Hàm khởi tạo servlet, gọi khi servlet được load lần đầu
    @Override
    public void init() {
        productDAO = new ProductDAO();  // Khởi tạo DAO sản phẩm
        brandDAO = new BrandDAO();      // Khởi tạo DAO thương hiệu
    }

    // Xử lý yêu cầu GET: hiển thị trang thêm sản phẩm
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        // Gửi yêu cầu tới trang JSP chứa form thêm sản phẩm
        RequestDispatcher dispatcher = req.getRequestDispatcher("addProduct.jsp");
        dispatcher.forward(req, res);
    }

    // Xử lý yêu cầu POST: thêm sản phẩm mới vào hệ thống
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            // Lấy dữ liệu từ form
            String name = req.getParameter("name");
            double price = Double.parseDouble(req.getParameter("price"));
            int stock = Integer.parseInt(req.getParameter("stock"));
            int categoryId = Integer.parseInt(req.getParameter("categoryId"));
            String brand = req.getParameter("brand");
            String brandNew = req.getParameter("brandNew");  // Thương hiệu mới nếu có nhập
            String description = req.getParameter("description");
            String specs = req.getParameter("specs");

            // Nếu có nhập thương hiệu mới thì thêm vào CSDL (nếu chưa tồn tại)
            if (brandNew != null && !brandNew.trim().isEmpty()) {
                brandDAO.insertBrandIfNotExists(brandNew);  // Chèn nếu chưa có
                brand = brandNew; // Gán giá trị mới
            }

            // Xử lý file ảnh (image)
            Part filePart = req.getPart("image");  // Lấy phần file từ form
            String fileName = "";  // Tên file sẽ lưu

            // Nếu có file ảnh
            if (filePart != null && filePart.getSize() > 0) {
                // Lấy tên file gốc và tạo tên mới tránh trùng (dùng timestamp)
                String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                fileName = System.currentTimeMillis() + "_" + originalFileName;

                // Đường dẫn thư mục chứa ảnh trên server
                String imagesDirPath = req.getServletContext().getRealPath("/images");
                File imagesDir = new File(imagesDirPath);
                if (!imagesDir.exists()) {
                    imagesDir.mkdirs();  // Tạo thư mục nếu chưa có
                }

                // Lưu file ảnh vào thư mục images
                File file = new File(imagesDir, fileName);
                try (InputStream input = filePart.getInputStream()) {
                    Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
            }

            // Tạo đối tượng Product và set dữ liệu
            Product product = new Product();
            product.setName(name);
            product.setPrice(price);
            product.setImage(fileName);
            product.setStockQuantity(stock);
            product.setCategoryId(categoryId);
            product.setBrand(brand);
            product.setDescription(description);
            product.setSpecs(specs);

            // Gọi DAO để thêm sản phẩm vào CSDL
            boolean success = productDAO.addProduct(product);

            // Đặt thông báo trạng thái cho người dùng
            if (success) {
                req.getSession().setAttribute("message", "Thêm sản phẩm thành công!");
            } else {
                req.getSession().setAttribute("error", "Có lỗi xảy ra khi thêm sản phẩm!");
            }

        } catch (Exception e) {
            // Bắt lỗi nếu có và đặt thông báo lỗi
            e.printStackTrace();
            req.getSession().setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }

        // Chuyển hướng về trang admin (hoặc danh sách sản phẩm)
        res.sendRedirect("admin");
    }
}
