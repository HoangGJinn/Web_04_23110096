<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Profile</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .profile-container {
            max-width: 800px;
            margin: 50px auto;
            padding: 20px;
        }
        .profile-form {
            background: #f9f9f9;
            padding: 30px;
            border-radius: 8px;
        }
        .form-group {
            margin-bottom: 20px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input[type="text"], input[type="file"] {
            width: 100%;
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
        }
        .btn:hover {
            background: #0056b3;
        }
        .message {
            padding: 10px;
            margin-bottom: 20px;
            border-radius: 4px;
        }
        .message.success {
            background: #d4edda;
            color: #155724;
        }
        .message.error {
            background: #f8d7da;
            color: #721c24;
        }
        .current-image {
            margin-top: 10px;
        }
        .current-image img {
            max-width: 200px;
            max-height: 200px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
    </style>
</head>
<body>
    <div class="profile-container">
        <h1>Cập nhật Profile</h1>
        
        <c:if test="${not empty message}">
            <div class="message success">${message}</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="message error">${error}</div>
        </c:if>
        
        <form action="${pageContext.request.contextPath}/profile" method="post" enctype="multipart/form-data" class="profile-form">
            <div class="form-group">
                <label>Username:</label>
                <input type="text" value="${user.username}" disabled />
            </div>
            
            <div class="form-group">
                <label>Email:</label>
                <input type="text" value="${user.email}" disabled />
            </div>
            
            <div class="form-group">
                <label for="fullname">Họ và tên:</label>
                <input type="text" id="fullname" name="fullname" value="${user.fullname}" required />
            </div>
            
            <div class="form-group">
                <label for="phone">Số điện thoại:</label>
                <input type="text" id="phone" name="phone" value="${user.phone}" />
            </div>
            
            <div class="form-group">
                <label for="image">Ảnh đại diện:</label>
                <input type="file" id="image" name="image" accept="image/*" />
                <c:if test="${not empty user.images}">
                    <div class="current-image">
                        <p>Ảnh hiện tại:</p>
                        <img src="${pageContext.request.contextPath}/uploads/${user.images}" alt="Current image" />
                    </div>
                </c:if>
            </div>
            
            <button type="submit" class="btn">Cập nhật</button>
        </form>
    </div>
</body>
</html>

