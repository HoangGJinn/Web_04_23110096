<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Trang chủ - GiapWeb</title>
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
        .hero-section {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 80px 20px;
            text-align: center;
        }
        .hero-section h1 {
            font-size: 3em;
            margin-bottom: 20px;
        }
        .hero-section p {
            font-size: 1.2em;
            margin-bottom: 30px;
        }
        .btn {
            display: inline-block;
            padding: 12px 30px;
            background: white;
            color: #667eea;
            text-decoration: none;
            border-radius: 5px;
            font-weight: bold;
            margin: 0 10px;
        }
        .btn:hover {
            background: #f0f0f0;
        }
        .features {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 30px;
            margin: 50px 0;
        }
        .feature-card {
            padding: 30px;
            border: 1px solid #ddd;
            border-radius: 8px;
            text-align: center;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .feature-card h3 {
            color: #667eea;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>
    <jsp:include page="/common/web/header.jsp" />
    
    <div class="hero-section">
        <div class="container">
            <h1>Chào mừng đến với GiapWeb</h1>
            <p>Hệ thống quản lý danh mục và video hiện đại</p>
            <c:choose>
                <c:when test="${sessionScope.account != null}">
                    <a href="${pageContext.request.contextPath}/profile" class="btn">Xem Profile</a>
                    <c:if test="${sessionScope.account.admin}">
                        <a href="${pageContext.request.contextPath}/admin/user" class="btn">Quản trị</a>
                    </c:if>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/login" class="btn">Đăng nhập</a>
                    <a href="${pageContext.request.contextPath}/register" class="btn">Đăng ký</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    
    <div class="container">
        <div class="features">
            <div class="feature-card">
                <h3>Quản lý Danh mục</h3>
                <p>Tổ chức và quản lý các danh mục một cách hiệu quả</p>
                <a href="${pageContext.request.contextPath}/category" class="btn">Xem danh mục</a>
            </div>
            <div class="feature-card">
                <h3>Quản lý Video</h3>
                <p>Xem và quản lý video trong hệ thống</p>
                <a href="${pageContext.request.contextPath}/product" class="btn">Xem video</a>
            </div>
            <div class="feature-card">
                <h3>Quản trị viên</h3>
                <p>Hệ thống quản trị toàn diện cho admin</p>
                <c:if test="${sessionScope.account != null && sessionScope.account.admin}">
                    <a href="${pageContext.request.contextPath}/admin/user" class="btn">Vào admin</a>
                </c:if>
            </div>
        </div>
    </div>
    
    <jsp:include page="/common/web/footer.jsp" />
</body>
</html>

