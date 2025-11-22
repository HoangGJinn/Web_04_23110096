<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Danh mục - GiapWeb</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
        .category-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 20px;
            margin-top: 30px;
        }
        .category-card {
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 20px;
            background: white;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .category-card img {
            width: 100%;
            max-height: 200px;
            object-fit: cover;
            border-radius: 4px;
            margin-bottom: 15px;
        }
        .category-card h3 {
            color: #667eea;
            margin-bottom: 10px;
        }
        .category-card p {
            color: #666;
            margin: 5px 0;
        }
    </style>
</head>
<body>
    <jsp:include page="/common/web/header.jsp" />
    
    <div class="container">
        <h1>Danh sách Danh mục</h1>
        
        <c:if test="${empty categories}">
            <p>Chưa có danh mục nào.</p>
        </c:if>
        
        <div class="category-grid">
            <c:forEach var="category" items="${categories}">
                <div class="category-card">
                    <c:if test="${not empty category.images}">
                        <img src="${pageContext.request.contextPath}/uploads/${category.images}" 
                             alt="${category.categoryname}" />
                    </c:if>
                    <h3>${category.categoryname}</h3>
                    <p><strong>Code:</strong> ${category.categorycode}</p>
                    <p><strong>Status:</strong> ${category.status ? 'Active' : 'Inactive'}</p>
                    <p><strong>Số video:</strong> ${category.videos != null ? category.videos.size() : 0}</p>
                </div>
            </c:forEach>
        </div>
    </div>
    
    <jsp:include page="/common/web/footer.jsp" />
</body>
</html>

