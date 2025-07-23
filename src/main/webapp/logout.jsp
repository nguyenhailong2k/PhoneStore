<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%
    session.removeAttribute("customer");
    response.sendRedirect("product");
%>
