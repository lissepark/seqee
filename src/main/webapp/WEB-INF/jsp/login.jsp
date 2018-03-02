<%--
  Created by IntelliJ IDEA.
  User: seva0716
  Date: 2/16/2018
  Time: 10:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<html>
<head>
    <title>Sequoia admin page Login</title>
    <link rel="stylesheet" href="/css/bootstrap.css">
    <link href="/css/style.css" rel="stylesheet">
    <script src="/js/jquery-3.2.1.min.js"></script>
    <script src="/js/bootstrap.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="Резной декор, Мебель из дерева, Посуда из дерева, Торцевые разделочные доски, Рамки
        для фото, Индивидуальные проекты Family joinery workshop Sequoia">
    <title>Резной декор, Мебель из дерева, Посуда из дерева, Торцевые разделочные доски, Рамки
        для фото, Индивидуальные проекты Family joinery workshop Sequoia</title>
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
<div class="container">
    <jsp:include page="header.jsp"/>
    <div class="row">
        <h1>Login</h1>
    </div>
    <c:url value="/login" var="loginVar"/>
    <form id="appointment-form" action="/login" method="POST">
        <div class="form-group">
            <label>Username</label>
            <input name="custom_username" class="form-control" />
        </div>
        <div class="form-group">
            <label>Password</label>
            <input type="password" name="custom_password" class="form-control" />
        </div>
        <sec:csrfInput/>
        <c:if test="${param.logout != null }">
            <h3 style="color: darkgreen">You have successfully been logged out.</h3>
        </c:if>
        <c:if test="${param.error != null }">
            <h3 style="color: red">Invalid Username or Password.</h3>
        </c:if>
        <button type="submit" id="btn-save" class="btn btn-primary">Login</button>
    </form>
</div>
</body>
</html>