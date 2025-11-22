<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header class="admin-header">
    <div class="admin-header-top">
        <div class="container">
            <div class="row">
                <div class="col-md-6">
                    <h2>Admin Panel - GiapWeb</h2>
                </div>
                <div class="col-md-6 text-right">
                    <c:if test="${sessionScope.account != null}">
                        <span>Xin chào, ${sessionScope.account.fullname}</span>
                        <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</header>

