<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sản phẩm - GiapWeb</title>
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
        .search-bar {
            margin: 20px 0;
            display: flex;
            gap: 10px;
        }
        .search-bar input {
            flex: 1;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .search-bar button {
            padding: 10px 20px;
            background: #667eea;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .video-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 20px;
            margin-top: 30px;
        }
        .video-card {
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 20px;
            background: white;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .video-card img {
            width: 100%;
            max-height: 200px;
            object-fit: cover;
            border-radius: 4px;
            margin-bottom: 15px;
        }
        .video-card h3 {
            color: #667eea;
            margin-bottom: 10px;
        }
        .video-card p {
            color: #666;
            margin: 5px 0;
        }
    </style>
</head>
<body>
    <jsp:include page="/common/web/header.jsp" />
    
    <div class="container">
        <h1>Danh sách Video</h1>
        
        <div class="search-bar">
            <form method="get" action="${pageContext.request.contextPath}/product">
                <input type="text" name="keyword" placeholder="Tìm kiếm video..." 
                       value="${keyword}" />
                <button type="submit">Tìm kiếm</button>
                <c:if test="${not empty keyword}">
                    <a href="${pageContext.request.contextPath}/product">Xóa tìm kiếm</a>
                </c:if>
            </form>
        </div>
        
        <c:if test="${empty videos}">
            <p>Không tìm thấy video nào.</p>
        </c:if>
        
        <div class="video-grid">
            <c:forEach var="video" items="${videos}">
                <div class="video-card">
                    <c:if test="${not empty video.poster}">
                        <img src="${pageContext.request.contextPath}/uploads/${video.poster}" 
                             alt="${video.title}" />
                    </c:if>
                    <h3>${video.title}</h3>
                    <p><strong>Lượt xem:</strong> ${video.views}</p>
                    <p><strong>Status:</strong> ${video.active ? 'Active' : 'Inactive'}</p>
                    <c:if test="${not empty video.description}">
                        <p>${video.description.length() > 100 ? video.description.substring(0, 100) + '...' : video.description}</p>
                    </c:if>
                </div>
            </c:forEach>
        </div>
    </div>
    
    <jsp:include page="/common/web/footer.jsp" />
</body>
</html>

