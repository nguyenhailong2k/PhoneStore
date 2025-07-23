package com.example.controller;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Nếu cần khởi tạo gì trước khi filter hoạt động thì xử lý ở đây
        // Nếu không cần có thể để trống
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);

        // Kiểm tra đăng nhập (ví dụ: session phải có thuộc tính "admin")
        if (session != null && session.getAttribute("admin") != null) {
            // Cho phép request tiếp tục nếu đã đăng nhập
            chain.doFilter(request, response);
        } else {
            // Chuyển hướng về trang đăng nhập nếu chưa đăng nhập
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
        }
    }

    @Override
    public void destroy() {
        // Cleanup khi filter bị xóa khỏi server (nếu cần)
    }
}
