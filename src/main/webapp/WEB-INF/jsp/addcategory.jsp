<%--
  Created by IntelliJ IDEA.
  User: seva0716
  Date: 1/11/2018
  Time: 4:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="model.Category" %>
<html>
<head>
    <title>Category adding page</title>
    <link rel="stylesheet" href="/css/bootstrap.css">
    <link href="/css/style.css" rel="stylesheet">
    <script src="/js/jquery-3.2.1.min.js"></script>
    <script src="/js/bootstrap.js"></script>

    <sec:csrfMetaTags/>
    <meta name="_csrf_parameter" content="_csrf" />
    <meta name="_csrf_header" content="X-CSRF-TOKEN" />
    <meta name="_csrf" content="e62835df-f1a0-49ea-bce7-bf96f998119c" />
</head>
<body>
<div style="margin-left: 2%">
    <h4>Please, fill the data and load an image</h4><br>
    <form action="addcategory?${_csrf.parameterName}=${_csrf.token}" method="post" enctype="multipart/form-data">
        <p>
            <input type="text" name="categoryName">Type Category name<br>
            <input type="text" name="categoryDescription" >Type Category description<br>
        </p>
        <h4 style="color:blue">Select image for Category to upload:</h4>
        <br/>
        <input type="file" name="file"><br/>
        <input class="btn btn-primary btn-lg" type="submit" value="Upload Image and send data">
    </form>
</div>
</body>
</html>