<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<h1>Forgot Password</h1>
<%-- Display error message if any --%>
<c:if test="${not empty error}">
    <p style="color: red">${error}</p>
</c:if>
<%-- Display success message if any --%>
<c:if test="${not empty success}">
    <p style="color: green">${success}</p>
</c:if>
<form action="/forgotPassword" method="post">
    <label for="email">Email:</label>
    <input type="email" id="email" name="email" required>
    <br>
    <input type="submit" value="Submit">
</form>
