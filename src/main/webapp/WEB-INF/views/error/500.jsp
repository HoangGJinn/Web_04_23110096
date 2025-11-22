<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>500 - Lỗi máy chủ</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        body {
            font-family: 'Arial', sans-serif;
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
            height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            color: white;
        }
        .error-container {
            text-align: center;
            padding: 40px;
        }
        .error-code {
            font-size: 150px;
            font-weight: bold;
            line-height: 1;
            margin-bottom: 20px;
            text-shadow: 2px 2px 4px rgba(0,0,0,0.3);
        }
        .error-message {
            font-size: 30px;
            margin-bottom: 20px;
        }
        .error-description {
            font-size: 18px;
            margin-bottom: 40px;
            opacity: 0.9;
        }
        .btn {
            display: inline-block;
            padding: 15px 30px;
            background: white;
            color: #f5576c;
            text-decoration: none;
            border-radius: 5px;
            font-weight: bold;
            margin: 0 10px;
            transition: transform 0.3s;
        }
        .btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.2);
        }
        .icon {
            font-size: 80px;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <div class="icon">⚠️</div>
        <div class="error-code">500</div>
        <div class="error-message">Lỗi máy chủ</div>
        <div class="error-description">
            Xin lỗi, đã xảy ra lỗi trong quá trình xử lý yêu cầu của bạn.
            Vui lòng thử lại sau hoặc liên hệ với quản trị viên.
        </div>
        <div>
            <a href="${pageContext.request.contextPath}/" class="btn">Về trang chủ</a>
            <a href="javascript:history.back()" class="btn">Quay lại</a>
        </div>
    </div>
</body>
</html>

