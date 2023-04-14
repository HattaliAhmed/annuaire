<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/header.jsp"%>

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <c:if test="${not empty success}">
                <div class="alert alert-success mt-3">${success}</div>
            </c:if>

            <h1 class="text-center mb-3">List of Groups</h1>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th class="text-center">ID</th>
                    <th class="text-center">Name</th>
                    <th class="text-center">Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="group" items="${groups}">
                    <tr>
                        <td class="text-center">${group.id}</td>
                        <td class="text-center">${group.name}</td>
                        <td class="text-center">
                            <a href="/groups/${group.id}" class="btn btn-primary">View</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>
