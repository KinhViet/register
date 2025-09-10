<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý danh mục</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .table-img {
            max-width: 50px;
            max-height: 50px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2 class="mt-4">Quản lý danh mục</h2>
        <c:if test="${not empty alert}">
            <div class="alert ${alertType}">${alert}</div>
        </c:if>
        <a href="${pageContext.request.contextPath}/admin/category/add" class="btn btn-primary mb-3">Thêm danh mục mới</a>
        <table class="table table-bordered table-hover">
            <thead class="thead-dark">
                <tr>
                    <th>ID</th>
                    <th>Tên danh mục</th>
                    <th>Biểu tượng</th>
                    <th>Hành động</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="category" items="${categories}">
                    <tr>
                        <td>${category.cateId}</td>
                        <td>${category.cateName}</td>
                        <td>
                            <c:if test="${not empty category.icons}">
                                <img src="${pageContext.request.contextPath}/image?fname=${category.icons}" class="table-img" alt="Icon">
                            </c:if>
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/category/edit?id=${category.cateId}" class="btn btn-sm btn-warning">Sửa</a>
                            <a href="${pageContext.request.contextPath}/admin/category/delete?id=${category.cateId}" class="btn btn-sm btn-danger" onclick="return confirm('Xác nhận xóa danh mục?')">Xóa</a>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty categories}">
                    <tr>
                        <td colspan="4" class="text-center">Không có danh mục nào.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
    </div>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>