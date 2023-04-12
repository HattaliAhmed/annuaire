<%@ include file="/WEB-INF/jsp/header.jsp"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<div class="container text-center" style="max-width: 800px;">
  <h1 class="mt-3">Search Results</h1>

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
    <c:forEach var="person" items="${persons}">
      <tr>
        <td>${person.firstName}</td>
        <td>${person.lastName}</td>
        <td>
          <a href="${pageContext.request.contextPath}/person/${person.id}" class="btn btn-primary">View profile</a>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</div>

<%@ include file="/WEB-INF/jsp/footer.jsp"%>
