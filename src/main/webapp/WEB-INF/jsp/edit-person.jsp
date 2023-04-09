<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Person</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css" />
</head>
<body>
<h1>Edit Person</h1>
<form method="POST" action="${pageContext.request.contextPath}/person/edit/${person.id}">
    <input type="hidden" name="id" value="${person.id}" />
    <label for="firstName">First Name:</label>
    <input type="text" id="firstName" name="firstName" value="${person.firstName}" required />
    <br />
    <label for="lastName">Last Name:</label>
    <input type="text" id="lastName" name="lastName" value="${person.lastName}" required />
    <br />
    <label for="birthDate">Birth Date:</label>
    <input type="date" id="birthDate" name="birthDate" value="${person.birthDate}" required />
    <br />
    <label for="email">Email:</label>
    <input type="email" id="email" name="email" value="${person.email}" required />
    <br />
    <label for="website">Website:</label>
    <input type="url" id="website" name="website" value="${person.website}" required />
    <br />
    <select name="groupId" id="groupId">
        <c:forEach var="groupe" items="${groups}">
            <option value="${groupe.id}" ${person.groupe.id == groupe.id ? 'selected' : ''}>${groupe.name}</option>
        </c:forEach>
    </select>
    <input type="submit" value="Save" />
</form>
</body>
</html>
