<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><c:choose><c:when test="${action == 'edit'}">Sửa Video</c:when><c:otherwise>Thêm Video</c:otherwise></c:choose></title>
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
        .form-group {
            margin-bottom: 20px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input[type="text"], select, textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        textarea {
            min-height: 100px;
        }
        .btn {
            padding: 10px 20px;
            background: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .btn:hover {
            background: #0056b3;
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
        <h1><c:choose><c:when test="${action == 'edit'}">Sửa Video</c:when><c:otherwise>Thêm Video</c:otherwise></c:choose></h1>
        
        <form action="${pageContext.request.contextPath}/admin/video" method="post" enctype="multipart/form-data">
            <input type="hidden" name="action" value="${action == 'edit' ? 'edit' : 'create'}" />
            <c:if test="${action == 'edit'}">
                <input type="hidden" name="id" value="${video.videold}" />
            </c:if>
            
            <div class="form-group">
                <label for="title">Title:</label>
                <input type="text" id="title" name="title" 
                       value="${video.title}" required />
            </div>
            
            <div class="form-group">
                <label for="description">Description:</label>
                <textarea id="description" name="description">${video.description}</textarea>
            </div>
            
            <div class="form-group">
                <label for="categoryld">Category:</label>
                <select id="categoryld" name="categoryld" required>
                    <option value="">-- Chọn Category --</option>
                    <c:forEach var="category" items="${categories}">
                        <option value="${category.categoryld}" 
                                <c:if test="${video.categoryld == category.categoryld}">selected</c:if>>
                            ${category.categoryname}
                        </option>
                    </c:forEach>
                </select>
            </div>
            
            <div class="form-group">
                <label for="poster">Poster:</label>
                <input type="file" id="poster" name="poster" accept="image/*" />
                <c:if test="${action == 'edit' && not empty video.poster}">
                    <p>Poster hiện tại: ${video.poster}</p>
                </c:if>
            </div>
            
            <div class="form-group">
                <label>
                    <input type="checkbox" name="active" 
                           <c:if test="${video.active}">checked</c:if> />
                    Active
                </label>
            </div>
            
            <button type="submit" class="btn">Lưu</button>
            <a href="${pageContext.request.contextPath}/admin/video" class="btn">Hủy</a>
        </form>
        </div>
    </div>
    <jsp:include page="/common/admin/footer.jsp" />
</body>
</html>

