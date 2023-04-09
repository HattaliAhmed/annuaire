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
    <a class="navbar-brand" href="#">Annuaire</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="/groups">Groups</a>
            </li>
        </ul>
        <form class="form-inline my-2 my-lg-0 mr-3">
            <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
            <button class="btn btn-outline-light my-2 my-sm-0" type="submit">Search</button>
        </form>
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" href="#">Login</a>
            </li>
        </ul>
    </div>
</nav>




