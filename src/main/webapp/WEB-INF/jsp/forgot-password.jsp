<%@ include file="/WEB-INF/jsp/header.jsp"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="container mt-5 mb-5 d-flex justify-content-center">
    <div style="max-width: 500px;">
        <h1 class="text-center">Forgot Password</h1>
        <p class="text-center">Enter your email address below to reset your password.</p>
        <%-- Display error message if any --%>
        <c:if test="${not empty error}">
            <p class="text-danger">${error}</p>
        </c:if>
        <%-- Display success message if any --%>
        <c:if test="${not empty success}">
            <p class="text-success">${success}</p>
        </c:if>
        <form action="/user/forgotPassword" method="post">
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" class="form-control" placeholder="Enter your email address" required>
            </div>
            <button type="submit" class="btn btn-primary btn-block">Submit</button>
        </form>
    </div>
</div>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>
