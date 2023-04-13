<%@ include file="/WEB-INF/jsp/header.jsp"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">

    <h1 class="text-center">Login</h1>

    <br/>

    <form method="POST" action="${pageContext.request.contextPath}/user/login" class="mx-auto" style="max-width: 300px;">
        <div class="form-group">
            <label for="id">User ID:</label>
            <input type="text" id="id" name="id" class="form-control" required />
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" id="password" name="password" class="form-control" required />
        </div>
        <button type="submit" class="btn btn-primary btn-block">Login</button>

        <c:if test="${not empty error}">
            <p class="text-danger mt-3">${error}</p>
        </c:if>
    </form>

</div>
