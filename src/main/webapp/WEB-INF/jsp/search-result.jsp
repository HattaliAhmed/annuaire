<%@ include file="/WEB-INF/jsp/header.jsp"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<div class="container text-center" style="max-width: 800px;">
  <h1 class="mt-3">Search Results</h1>

  <br/>

  <h3>Persons</h3> <!-- Add section for Persons -->
  <c:if test="${not empty persons}">
    <table class="table table-striped mt-3">
      <thead>
      <tr>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Group</th> <!-- Add Group column -->
        <th>Actions</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="person" items="${persons}">
        <tr>
          <td>${person.firstName}</td>
          <td>${person.lastName}</td>
          <td>${person.groupe.name}</td> <!-- Display Group value -->
          <td>
            <a href="${pageContext.request.contextPath}/person/${person.id}" class="btn btn-primary">View profile</a>
          </td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </c:if>
  <c:if test="${empty persons}">
    <p>No results found</p>
  </c:if>

  <br/>

  <h3>Groups</h3> <!-- Add section for Groups -->
  <c:if test="${not empty groups}">
    <table class="table table-striped mt-3">
      <thead>
      <tr>
        <th>Group Name</th>
        <th>Actions</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="group" items="${groups}">
        <tr>
          <td>${group.name}</td> <!-- Display Group name -->
          <td>
            <a href="${pageContext.request.contextPath}/groups/${group.id}" class="btn btn-primary">View group</a> <!-- Link to group page -->
          </td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </c:if>
  <c:if test="${empty groups}">
    <p>No results found</p>
  </c:if>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>
