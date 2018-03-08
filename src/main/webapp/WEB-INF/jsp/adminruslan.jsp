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
    <style>
        .wrap {
            text-align: justify;
        }
        .wrap .wrapdiv {
            display: inline-block;
            vertical-align: top;
        }
        .wrap:after {
            display: inline-block;
            content: "";
            width: 100%;
        }
        a {
            margin-bottom: 2%;
        }
    </style>
</head>
<body>
    <div class="container">
        <jsp:include page="header.jsp" />
            <div class="row">
                <div class="col">
                    <a href="/adminruslan/addcategory" class="btn btn-primary btn-lg active" role="button" aria-pressed="true">Add a Category</a>
                    <br/>
                    <a href="/adminruslan/addoffer" class="btn btn-primary btn-lg active" role="button" aria-pressed="true">Add an Offering</a>
                    <!--a href="/adminruslan/addcategory"><button type="button" class="btn btn-primary btn-lg">Add a Category</button></a-->
                    <!--a href="/adminruslan/addoffer"><button type="button" class="btn btn-primary btn-lg">Add an Offering</button></a-->
                    <br/>
                    <a href="/" class="btn btn-primary btn-lg active" role="button" aria-pressed="true">Go to main page</a>
                    <!--a href="/"><button type="button" class="btn btn-outline-primary">Go to main page</button></a-->
                </div>
                <div class="col">
                    Hidden categories:
                </div>
            </div>
    </div>
</body>
</html>
