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

@WebServlet("/add")
@MultipartConfig
public class AddProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductDAO productDAO;
    private BrandDAO brandDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
        brandDAO = new BrandDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        // Hiển thị form thêm sản phẩm
        RequestDispatcher dispatcher = req.getRequestDispatcher("addProduct.jsp");
        dispatcher.forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        try {
            String name = req.getParameter("name");
            double price = Double.parseDouble(req.getParameter("price"));
            int stock = Integer.parseInt(req.getParameter("stock"));
            int categoryId = Integer.parseInt(req.getParameter("categoryId"));
            String brand = req.getParameter("brand");
            String brandNew = req.getParameter("brandNew");
            String description = req.getParameter("description");
            String specs = req.getParameter("specs");

            if (brandNew != null && !brandNew.trim().isEmpty()) {
                brandDAO.insertBrandIfNotExists(brandNew);
                brand = brandNew;
            }

            Part filePart = req.getPart("image");
            String fileName = "";

            if (filePart != null && filePart.getSize() > 0) {
                String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                fileName = System.currentTimeMillis() + "_" + originalFileName;

                String imagesDirPath = req.getServletContext().getRealPath("/images");
                File imagesDir = new File(imagesDirPath);
                if (!imagesDir.exists()) {
                    imagesDir.mkdirs();
                }

                File file = new File(imagesDir, fileName);
                try (InputStream input = filePart.getInputStream()) {
                    Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
            }

            Product product = new Product();
            product.setName(name);
            product.setPrice(price);
            product.setImage(fileName);
            product.setStockQuantity(stock);
            product.setCategoryId(categoryId);
            product.setBrand(brand);
            product.setDescription(description);
            product.setSpecs(specs);

            boolean success = productDAO.addProduct(product);

            if (success) {
                req.getSession().setAttribute("message", "Thêm sản phẩm thành công!");
            } else {
                req.getSession().setAttribute("error", "Có lỗi xảy ra khi thêm sản phẩm!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            req.getSession().setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }

        res.sendRedirect("admin");
    }
}