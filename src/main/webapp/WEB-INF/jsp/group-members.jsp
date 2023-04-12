<%@ include file="/WEB-INF/jsp/header.jsp"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<div class="container text-center" style="max-width: 800px;">
    <h1 class="mt-3">Members of ${group.name}</h1>

    <div class="row mt-3 justify-content-center">
        <div class="col-md-6">
            <form action="${pageContext.request.contextPath}/search/${group.id}" method="POST"> <!-- Update the action URL to include group.id -->
                <div class="input-group">
                    <input type="text" class="form-control" name="query" placeholder="Search in ${group.name} members..." value="${query}">
                    <div class="input-group-append">
                        <button class="btn btn-primary" type="submit">Search</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <br/>

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
