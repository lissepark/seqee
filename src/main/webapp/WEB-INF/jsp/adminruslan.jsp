<%--
  User: sergii
  Date: 1/11/2018
  Time: 4:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Administrator page</title>
    <link rel="stylesheet" href="/css/bootstrap.css">
    <link href="/css/style.css" rel="stylesheet">
    <script src="/js/jquery-3.2.1.min.js"></script>
    <script src="/js/bootstrap.js"></script>
</head>
<body>
    <div class="container">
        <jsp:include page="header.jsp" />
        <a href="/adminruslan/addcategory"><button type="button" class="btn btn-primary btn-lg">Add a Category</button></a>
        <br>
        <a href="/adminruslan/addoffer"><button type="button" class="btn btn-primary btn-lg">Add an Offering</button></a>
        <br>
        <a href="/"><button type="button" class="btn btn-outline-primary">Go to main page</button></a>
    </div>
</body>
</html>
