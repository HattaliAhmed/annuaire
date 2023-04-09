<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Person Details</title>
</head>
<body>
<div class="container">
    <h1>Person Details</h1>
    <c:choose>
        <c:when test="${empty person}">
            <p>Person not found.</p>
        </c:when>
        <c:otherwise>
            <p>First Name: ${person.firstName}</p>
            <p>Last Name: ${person.lastName}</p>
            <p>Birth Date: ${person.birthDate}</p>
            <p>Email: ${person.email}</p>
            <p>Website: <a href="${person.website}">${person.website}</a></p>
            <p>Group : ${person.groupe.name}</p>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
