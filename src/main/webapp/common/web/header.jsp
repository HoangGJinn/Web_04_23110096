<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header class="header">
    <div class="header-top">
        <div class="container">
            <div class="row">
                <div class="col-md-6">
                    <span>GIAO DIỆN</span>
                </div>
                <div class="col-md-6 text-right">
                    <c:choose>
                        <c:when test="${sessionScope.account != null}">
                            <span>Xin chào, ${sessionScope.account.fullname != null ? sessionScope.account.fullname : sessionScope.account.username}</span> | 
                            <a href="${pageContext.request.contextPath}/profile">PROFILE</a>
                            <c:if test="${sessionScope.account.admin == true}">
                                | <a href="${pageContext.request.contextPath}/admin/user">QUẢN TRỊ</a>
                            </c:if>
                            | <a href="${pageContext.request.contextPath}/logout">ĐĂNG XUẤT</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/login">ĐĂNG NHẬP</a> | 
                            <a href="${pageContext.request.contextPath}/register">ĐĂNG KÝ</a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
    <nav class="navbar">
        <div class="container">
            <div class="navbar-brand">
                <a href="${pageContext.request.contextPath}/">
                    <span class="logo-text">GiapWeb</span>
                </a>
            </div>
            <ul class="navbar-nav">
                <li><a href="${pageContext.request.contextPath}/">TRANG CHỦ</a></li>
                <li><a href="${pageContext.request.contextPath}/category">DANH MỤC</a></li>
                <li><a href="${pageContext.request.contextPath}/product">SẢN PHẨM</a></li>
                <c:if test="${sessionScope.account != null && sessionScope.account.admin == true}">
                    <li><a href="${pageContext.request.contextPath}/admin/user" style="color: #ffc107; font-weight: bold;">QUẢN TRỊ</a></li>
                </c:if>
            </ul>
        </div>
    </nav>
</header>

