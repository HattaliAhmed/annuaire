<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>List of Groups</title>
</head>
<body>
<h1>List of Groups</h1>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Description</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="group" items="${groups}">
        <tr>
            <td>${group.id}</td>
            <td>${group.name}</td>
            <td>
                <a href="/groups/${group.id}">View</a>
            </td>
            <td>
                <a href="/groups/${group.id}">Edit</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
