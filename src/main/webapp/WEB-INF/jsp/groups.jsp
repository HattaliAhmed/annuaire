<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<div class="container mt-5">
    <h1>List of Groups</h1>
    <table class="table">
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
                    <a href="/groups/${group.id}" class="btn btn-primary">View</a>
                </td>
                <td>
                    <a href="/groups/${group.id}/edit" class="btn btn-secondary">Edit</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<%@ include file="/WEB-INF/jsp/footer.jsp"%>