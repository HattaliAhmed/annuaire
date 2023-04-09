<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Members of ${group.name}</title>
</head>
<body>
<h1>Members of ${group.name}</h1>
<table>
    <thead>
    <tr>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Profile</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="member" items="${group.members}">
        <tr>
            <td>${member.firstName}</td>
            <td>${member.lastName}</td>
            <td><a href="${pageContext.request.contextPath}/person/${member.id}">View profile</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
