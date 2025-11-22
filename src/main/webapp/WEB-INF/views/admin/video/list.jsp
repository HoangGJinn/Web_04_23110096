<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý Video</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
        }
        .admin-container {
            display: flex;
            min-height: calc(100vh - 100px);
        }
        .admin-sidebar {
            width: 250px;
            background: #f8f9fa;
            padding: 20px;
            border-right: 1px solid #ddd;
        }
        .admin-menu {
            list-style: none;
            padding: 0;
            margin: 0;
        }
        .admin-menu li {
            margin-bottom: 10px;
        }
        .admin-menu a {
            display: block;
            padding: 12px 15px;
            background: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            transition: background 0.3s;
        }
        .admin-menu a:hover {
            background: #0056b3;
        }
        .admin-menu a.active {
            background: #0056b3;
            font-weight: bold;
        }
        .admin-header {
            background: #343a40;
            color: white;
            padding: 15px 20px;
        }
        .admin-header h2 {
            margin: 0;
            display: inline-block;
        }
        .admin-header-top {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .admin-content {
            flex: 1;
            padding: 20px;
        }
        .action-bar {
            margin-bottom: 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .search-bar {
            display: flex;
            gap: 10px;
        }
        .search-bar input {
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .btn {
            padding: 10px 20px;
            background: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
        }
        .btn:hover {
            background: #0056b3;
        }
        .btn-danger {
            background: #dc3545;
        }
        .btn-danger:hover {
            background: #c82333;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #f8f9fa;
            font-weight: bold;
        }
        tr:hover {
            background-color: #f5f5f5;
        }
        .message {
            padding: 10px;
            margin-bottom: 20px;
            border-radius: 4px;
            background: #d4edda;
            color: #155724;
        }
        .image-preview {
            max-width: 100px;
            max-height: 100px;
        }
    </style>
</head>
<body>
    <jsp:include page="/common/admin/header.jsp" />
    <div class="admin-container">
        <div class="admin-sidebar">
            <jsp:include page="/common/admin/left.jsp" />
        </div>
        <div class="admin-content">
        <h1>Quản lý Video</h1>
        
        <c:if test="${not empty param.message}">
            <div class="message">
                <c:choose>
                    <c:when test="${param.message == 'create_success'}">Thêm video thành công!</c:when>
                    <c:when test="${param.message == 'edit_success'}">Cập nhật video thành công!</c:when>
                    <c:when test="${param.message == 'delete_success'}">Xóa video thành công!</c:when>
                </c:choose>
            </div>
        </c:if>
        
        <div class="action-bar">
            <a href="${pageContext.request.contextPath}/admin/video?action=create" class="btn">Thêm mới</a>
            <div class="search-bar">
                <form action="${pageContext.request.contextPath}/admin/video" method="get">
                    <input type="text" name="keyword" placeholder="Tìm kiếm video..." 
                           value="${keyword}" />
                    <button type="submit" class="btn">Tìm kiếm</button>
                    <c:if test="${not empty keyword}">
                        <a href="${pageContext.request.contextPath}/admin/video" class="btn">Xóa tìm kiếm</a>
                    </c:if>
                </form>
            </div>
        </div>
        
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Title</th>
                    <th>Poster</th>
                    <th>Category</th>
                    <th>Views</th>
                    <th>Active</th>
                    <th>Thao tác</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="video" items="${videos}">
                    <tr>
                        <td>${video.videold}</td>
                        <td>${video.title}</td>
                        <td>
                            <c:if test="${not empty video.poster}">
                                <img src="${pageContext.request.contextPath}/uploads/${video.poster}" 
                                     alt="Video poster" class="image-preview" />
                            </c:if>
                        </td>
                        <td>${video.categoryld}</td>
                        <td>${video.views}</td>
                        <td>${video.active ? 'Yes' : 'No'}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/video?action=edit&id=${video.videold}" class="btn">Sửa</a>
                            <a href="${pageContext.request.contextPath}/admin/video?action=delete&id=${video.videold}" 
                               class="btn btn-danger" 
                               onclick="return confirm('Bạn có chắc muốn xóa video này?')">Xóa</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        </div>
    </div>
    <jsp:include page="/common/admin/footer.jsp" />
</body>
</html>

