<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:url var="bootstrap_css"
       value="/webjars/bootstrap/4.6.0-1/css/bootstrap.min.css" />
<c:url var="bootstrap_js"
       value="/webjars/bootstrap/4.6.0-1/js/bootstrap.min.js" />
<c:url var="jquery_js" value="/webjars/jquery/3.5.1/jquery.min.js" />
<c:url var="css" value="/style.css" />

<html>
<head>
    <meta charset="UTF-8">
    <title>Annuaire</title>
    <link rel="stylesheet" href="${css}">
    <link rel="stylesheet" href="${bootstrap_css}">
    <script src="${jquery_js}"></script>
    <script src="${bootstrap_js}"></script>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/">Annuaire</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <form action="${pageContext.request.contextPath}/search" method="POST"> <!-- Update the method to POST -->
            <div class="input-group">
                <input type="text" class="form-control" name="query" placeholder="Search ..." value="${query}">
                <div class="input-group-append">
                    <button class="btn btn-primary" type="submit">Search</button>
                </div>
            </div>
        </form>

        <ul class="navbar-nav ml-auto">
            <c:if test="${!empty user && user.userId != null && user.userId != -1}">
            <li class="nav-item">
                    <a class="nav-link" href="/person/${user.userId}">Hello, ${user.firstName}</a>
            </li>
            <li>
                <a class="nav-link text-danger" href="/user/logout">Logout</a>
            </li>
            </c:if>
            <c:if test="${empty user || user.userId == -1 || user.userId == null}">
                <li class="nav-item">
                    <a class="nav-link" href="/user/login">Login</a>
                </li>
            </c:if>
        </ul>
    </div>
</nav>




