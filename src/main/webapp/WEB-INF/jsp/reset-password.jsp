<%@ include file="/WEB-INF/jsp/header.jsp"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">

    <h1 class="text-center">Reset Password</h1>

    <br/>

    <form method="POST" action="${pageContext.request.contextPath}/user/reset" class="mx-auto" style="max-width: 300px; padding: 20px;">
        <input type="hidden" id="token" name="token" value="${token}" />
        <div class="form-group">
            <label for="password">New Password:</label>
            <input type="password" id="password" name="password" class="form-control"/>
        </div>
        <div class="form-group">
            <label for="confirmPassword">Confirm Password:</label>
            <input type="password" id="confirmPassword" name="confirmPassword" class="form-control"/>
        </div>
        <button type="submit" class="btn btn-primary btn-block">Reset Password</button>

        <div class="text-center mt-3">
            <a href="${pageContext.request.contextPath}/user/login" class="btn btn-link">Back to Login</a>
        </div>

        <c:if test="${not empty error}">
            <div class="alert alert-danger mt-3">
                    ${error}
            </div>
        </c:if>
    </form>


</div>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>
