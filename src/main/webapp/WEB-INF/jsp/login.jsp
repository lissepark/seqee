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
<!DOCTYPE html>
<html lang="en">
<head>

    <title>Sequoia admin page Login</title>

    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">

    <!-- Optional theme -->
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">

    <!-- Latest Jquery -->
    <script
            src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"
            type="text/javascript"></script>
    <!-- Latest compiled and minified JavaScript -->
    <script
            src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

</head>
<body>
<jsp:include page="header.jsp" />
<div class="container">
    <div class="row">
        <h1>Login</h1>
    </div>
    <c:url value="/login" var="loginVar"/>
    <form id="appointment-form" action="/login" method="POST">
        <div class="form-group">
            <label for="make">Username</label>
            <input name="custom_username" class="form-control" />
        </div>
        <div class="form-group">
            <label for="model">Password</label>
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