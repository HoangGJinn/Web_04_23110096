<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><decorator:title default="Admin - GiapWeb" /></title>
    <decorator:head />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin.css">
    <style>
        .admin-container {
            display: flex;
            min-height: calc(100vh - 100px);
        }
        .admin-sidebar {
            width: 250px;
            background: #f8f9fa;
            padding: 20px;
        }
        .admin-content {
            flex: 1;
            padding: 20px;
        }
        .admin-menu {
            list-style: none;
            padding: 0;
        }
        .admin-menu li {
            margin-bottom: 10px;
        }
        .admin-menu a {
            display: block;
            padding: 10px;
            background: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 4px;
        }
        .admin-menu a:hover {
            background: #0056b3;
        }
        .admin-header {
            background: #343a40;
            color: white;
            padding: 15px 0;
        }
        .admin-footer {
            background: #343a40;
            color: white;
            padding: 15px 0;
            margin-top: auto;
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
            <decorator:body />
        </div>
    </div>
    <jsp:include page="/common/admin/footer.jsp" />
</body>
</html>

