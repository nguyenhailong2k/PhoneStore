// Khai báo package của servlet
package com.example.controller;

// Import các lớp cần thiết
import com.example.dao.ProductDAO; // DAO để thao tác với bảng Product
import javax.servlet.*;            // Gói cho các đối tượng Servlet
import javax.servlet.annotation.WebServlet; // Để ánh xạ servlet với URL
import javax.servlet.http.*;      // Gói cho các đối tượng HTTP
import java.io.IOException;       // Để xử lý ngoại lệ vào/ra

/**
 * Servlet xử lý việc xóa sản phẩm dựa trên ID được truyền qua URL.
 */
@WebServlet("/delete") // Servlet này sẽ xử lý các request gửi đến "/delete"
public class DeleteProductServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // DAO dùng để thao tác với dữ liệu sản phẩm
    private ProductDAO productDAO;

    /**
     * Phương thức init() được gọi một lần khi servlet được khởi tạo.
     * Khởi tạo đối tượng ProductDAO để dùng trong suốt vòng đời servlet.
     */
    @Override
    public void init() {
        productDAO = new ProductDAO();
    }

    /**
     * Phương thức xử lý các yêu cầu HTTP GET (thường từ liên kết hoặc form).
     * Dùng để xóa sản phẩm theo ID lấy từ request parameter.
     */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            // Lấy ID sản phẩm từ request parameter và chuyển sang kiểu int
            int id = Integer.parseInt(req.getParameter("id"));

            // Gọi DAO để xóa sản phẩm theo ID
            boolean success = productDAO.deleteProduct(id);

            // Tạo thông báo để hiển thị trên giao diện admin
            if (success) {
                req.getSession().setAttribute("message", "Xóa sản phẩm thành công!");
            } else {
                req.getSession().setAttribute("error", "Có lỗi xảy ra khi xóa sản phẩm!");
            }

        } catch (Exception e) {
            // Nếu có lỗi xảy ra trong quá trình xóa
            e.printStackTrace();
            req.getSession().setAttribute("error", "Có lỗi xảy ra: " + e.getMessage());
        }

        // Sau khi xử lý, chuyển hướng về trang admin để hiển thị danh sách sản phẩm
        resp.sendRedirect("admin");
    }

    /**
     * Phương thức xử lý các yêu cầu HTTP POST.
     * Ở đây, POST được chuyển tiếp xử lý giống như GET.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); // Gọi lại doGet để xử lý logic tương tự
    }
}
