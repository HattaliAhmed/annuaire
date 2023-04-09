<%@ include file="/WEB-INF/jsp/header.jsp"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<div class="container">
    <h1 class="mt-3">Members of ${group.name}</h1>
    <table class="table table-striped mt-3">
        <thead>
        <tr>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="member" items="${group.members}">
            <tr>
                <td>${member.firstName}</td>
                <td>${member.lastName}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/person/${member.id}" class="btn btn-primary">View profile</a>
                    <a href="${pageContext.request.contextPath}/person/edit/${member.id}" class="btn btn-secondary">Edit</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>