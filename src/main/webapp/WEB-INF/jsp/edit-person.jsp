<%@ include file="/WEB-INF/jsp/header.jsp"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="container">

    <h1>Edit Person</h1>

    <form:form method="POST" action="${pageContext.request.contextPath}/person/edit/${person.id}" modelAttribute="person">

        <input type="hidden" name="id" value="${person.id}" />

        <div class="form-group">
            <label for="firstName">First Name:</label>
            <form:input type="text" path="firstName" id="firstName" cssClass="form-control"/>
            <form:errors path="firstName" cssClass="alert alert-danger" element="div" />
        </div>

        <div class="form-group">
            <label for="lastName">Last Name:</label>
            <form:input type="text" path="lastName" id="lastName" cssClass="form-control" />
            <form:errors path="lastName" cssClass="alert alert-danger" element="div" />
        </div>

        <div class="form-group">
            <label for="birthDate">Birth Date:</label>
            <form:input type="date" path="birthDate" id="birthDate" cssClass="form-control"/>
            <form:errors path="birthDate" cssClass="alert alert-danger" element="div" />
        </div>

        <div class="form-group">
            <label for="email">Email:</label>
            <form:input path="email" id="email" cssClass="form-control"/>
            <form:errors path="email" cssClass="alert alert-danger" element="div" />
        </div>

        <div class="form-group">
            <label for="website">Website:</label>
            <form:input type="url" path="website" id="website" cssClass="form-control"/>
            <form:errors path="website" cssClass="alert alert-danger" element="div" />
        </div>

        <div class="form-group">
            <label for="groupId">Group:</label>
            <select name="groupId" id="groupId" class="form-control">
                <c:forEach var="groupe" items="${groups}">
                    <option value="${groupe.id}" ${person.groupe.id == groupe.id ? 'selected' : ''}>${groupe.name}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label>New Password:</label>
            <input type="password" name="newPassword" id="newPassword" class="form-control" />
        </div>

        <div class="form-group">
            <label for="password">Old Password</label>
            <form:password path="password" id="newPassword" cssClass="form-control"/>
            <form:errors path="password" cssClass="alert alert-danger" element="div" />
        </div>

        <button type="submit" class="btn btn-primary">Save</button>
    </form:form>

</div>
