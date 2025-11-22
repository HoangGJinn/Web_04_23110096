<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><decorator:title default="GiapWeb" /></title>
    <decorator:head />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <jsp:include page="/common/web/header.jsp" />
    <div class="container">
        <decorator:body />
    </div>
    <jsp:include page="/common/web/footer.jsp" />
</body>
</html>

