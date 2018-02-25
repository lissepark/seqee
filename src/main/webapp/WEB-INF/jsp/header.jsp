<%--
  Created by IntelliJ IDEA.
  User: seva0716
  Date: 1/16/2018
  Time: 11:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<html>
<head>
    <link rel="stylesheet" href="/css/bootstrap.css">
    <link href="/css/style.css" rel="stylesheet">
    <script src="/js/jquery-3.2.1.min.js"></script>
    <script src="/js/bootstrap.js"></script>
    <script src="/js/general.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
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
    </style>
</head>
<body>
<sec:authorize access="authenticated" var="authenticated"/>
<nav class="navbar navbar-expand-lg navbar-light bg-light" style="margin-left: auto">
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
                <a class="nav-link" href="/">Главная<span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">Мой кабинет</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">Оплата и доставка</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">Обратная связь</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">Контакты</a>
            </li>
            <c:choose>
                <c:when test="${authenticated}">
                    <li class="nav-item">
                        <a id="logout" class="nav-link" href="#">Logout</a>
                        <form id="logout-form" action="<c:url value="/logout"/>" method="post">
                            <sec:csrfInput/>
                        </form>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="nav-item">
                        <a class="nav-link" href="/login">Login</a>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
        <form class="form-inline my-2 my-lg-0">
            <input class="form-control mr-sm-2" type="text" placeholder="Search" aria-label="Search">
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
        </form>
    </div>
</nav>

<div class="masthead" style="margin-top: 10px">
    <div class="container">
        <div class="row">
            <div class="col">
                <img src="http://www.fjwsequoia.com/images/seq_logo.png" class="rounded float-left" alt="Sequoia">
            </div>
            <div class="col-md-auto">
                <p class="text-lg-left h4 text-muted">Семейная столярная мастерская</p>
                <p class="text-xl-left h3 text-muted">Family joinery workshop Sequoia</p>
                <h6 class="text-sm-left">+38(099)682-15-44</h6>
                <h6 class="text-sm-left">+38(099)349-63-12</h6>
                <h6 class="text-sm-left">fjwsequoia@gmail.com</h6>
            </div>
            <div class="col col-lg-2" style="align-content: flex-end">
                Корзина
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script>window.jQuery || document.write('<script src="../../../../assets/js/vendor/jquery.min.js"><\/script>')</script>
<script src="../../../../assets/js/vendor/popper.min.js"></script>
<script src="../../../../dist/js/bootstrap.min.js"></script>
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="../../../../assets/js/ie10-viewport-bug-workaround.js"></script>

</body>
</html>