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

@WebServlet("/edit")
@MultipartConfig
public class EditProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductDAO productDAO;
    private BrandDAO brandDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
        brandDAO = new BrandDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            Product product = productDAO.getProductById(id);
            if (product != null) {
                req.setAttribute("product", product);
                req.getRequestDispatcher("editProduct.jsp").forward(req, resp);
            } else {
                resp.sendRedirect("admin");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("admin");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            String name = req.getParameter("name");
            double price = Double.parseDouble(req.getParameter("price").replaceAll(",", ""));
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

            if (filePart != null && filePart.getSize() > 0) {
                String originalFileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                String uniqueFileName = System.currentTimeMillis() + "_" + originalFileName;

                String imagesDirPath = req.getServletContext().getRealPath("/images");
                File imagesDir = new File(imagesDirPath);
                if (!imagesDir.exists()) {
                    imagesDir.mkdirs();
                }

                File file = new File(imagesDir, uniqueFileName);
                try (InputStream input = filePart.getInputStream()) {
                    Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }

                product.setImage(uniqueFileName);
                success = productDAO.updateProduct(product);
            } else {
                success = productDAO.updateProductWithoutImage(product);
            }

            if (success) {
                req.getSession().setAttribute("message", "Cập nhật sản phẩm thành công!");
            } else {
                req.getSession().setAttribute("error", "Có lỗi xảy ra khi cập nhật sản phẩm!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            req.getSession().setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }

        resp.sendRedirect("admin");
    }
}
