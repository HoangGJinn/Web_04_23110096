<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý User</title>
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
        <h1>Quản lý User</h1>
        
        <c:if test="${not empty param.message}">
            <div class="message">
                <c:choose>
                    <c:when test="${param.message == 'create_success'}">Thêm user thành công!</c:when>
                    <c:when test="${param.message == 'edit_success'}">Cập nhật user thành công!</c:when>
                    <c:when test="${param.message == 'delete_success'}">Xóa user thành công!</c:when>
                </c:choose>
            </div>
        </c:if>
        
        <div class="action-bar">
            <a href="${pageContext.request.contextPath}/admin/user?action=create" class="btn">Thêm mới</a>
        </div>
        
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Username</th>
                    <th>Fullname</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Ảnh</th>
                    <th>Admin</th>
                    <th>Active</th>
                    <th>Số Category</th>
                    <th>Thao tác</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.username}</td>
                        <td>${user.fullname}</td>
                        <td>${user.email}</td>
                        <td>${user.phone}</td>
                        <td>
                            <c:if test="${not empty user.images}">
                                <img src="${pageContext.request.contextPath}/uploads/${user.images}" 
                                     alt="User image" class="image-preview" />
                            </c:if>
                        </td>
                        <td>${user.admin ? 'Yes' : 'No'}</td>
                        <td>${user.active ? 'Yes' : 'No'}</td>
                        <td>${user.categories != null ? user.categories.size() : 0}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/user?action=edit&username=${user.username}" class="btn">Sửa</a>
                            <a href="${pageContext.request.contextPath}/admin/user?action=delete&username=${user.username}" 
                               class="btn btn-danger" 
                               onclick="return confirm('Bạn có chắc muốn xóa user này?')">Xóa</a>
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

