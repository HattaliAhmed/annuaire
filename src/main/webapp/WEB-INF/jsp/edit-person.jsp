<%@ include file="/WEB-INF/jsp/header.jsp"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">

<h1>Edit Person</h1>

<form method="POST" action="${pageContext.request.contextPath}/person/edit/${person.id}">
    <input type="hidden" name="id" value="${person.id}" />
    <div class="form-group">
        <label for="firstName">First Name:</label>
        <input type="text" id="firstName" name="firstName" value="${person.firstName}" class="form-control" required />
    </div>
    <div class="form-group">
        <label for="lastName">Last Name:</label>
        <input type="text" id="lastName" name="lastName" value="${person.lastName}" class="form-control" required />
    </div>
    <div class="form-group">
        <label for="birthDate">Birth Date:</label>
        <input type="date" id="birthDate" name="birthDate" value="${person.birthDate}" class="form-control" required />
    </div>
    <div class="form-group">
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" value="${person.email}" class="form-control" required />
    </div>
    <div class="form-group">
        <label for="website">Website:</label>
        <input type="url" id="website" name="website" value="${person.website}" class="form-control" required />
    </div>
    <div class="form-group">
        <label for="groupId">Group:</label>
        <select name="groupId" id="groupId" class="form-control">
            <c:forEach var="groupe" items="${groups}">
                <option value="${groupe.id}" ${person.groupe.id == groupe.id ? 'selected' : ''}>${groupe.name}</option>
            </c:forEach>
        </select>
    </div>
    <button type="submit" class="btn btn-primary">Save</button>
</form>

</div>