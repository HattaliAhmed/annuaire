<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/jsp/header.jsp"%>

<div class="container">
    <div class="card">
        <div class="card-header">
            <h1>Person Details</h1>
        </div>
        <div class="card-body">
            <c:choose>
                <c:when test="${empty person}">
                    <p>Person not found.</p>
                </c:when>
                <c:otherwise>
                    <p><strong>First Name:</strong> ${person.firstName}</p>
                    <p><strong>Last Name:</strong> ${person.lastName}</p>
                    <p><strong>Website:</strong> <a href="${person.website}">${person.website}</a></p>
                    <p><strong>Group:</strong> ${person.groupe.name}</p>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>
