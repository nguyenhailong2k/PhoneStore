package com.example.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.dao.ProductDAO;

/**
 * Servlet test để kiểm tra việc cập nhật số lượng tồn kho (stock) cho sản phẩm.
 * Chạy servlet này sẽ cập nhật tồn kho của sản phẩm có ID = 1 về số lượng 77.
 * 
 * URL mapping: /test-stock
 */
@WebServlet("/test-stock")
public class UpdateStockTestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor mặc định.
     */
    public UpdateStockTestServlet() {
        super();
    }

    /**
     * Xử lý yêu cầu HTTP GET.
     * - Gọi DAO để cập nhật số lượng tồn kho của sản phẩm có ID = 1 về 77.
     * - In ra kết quả thành công hoặc thất bại trên trình duyệt.
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ProductDAO dao = new ProductDAO();              // Khởi tạo DAO để thao tác với CSDL
        boolean success = dao.updateStock(1, 77);       // Ép cập nhật tồn kho của sản phẩm ID = 1 thành 77
        resp.getWriter().println(success ? "✅ OK" : "❌ FAIL");  // In ra kết quả cập nhật
    }

    /**
     * Xử lý yêu cầu HTTP POST.
     * - Ở đây, POST được xử lý giống như GET bằng cách gọi lại doGet().
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}
