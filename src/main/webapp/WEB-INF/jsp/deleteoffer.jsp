<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="model.Category" %>
<%@ page import="java.sql.Blob" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.io.ByteArrayInputStream" %>
<%@ page import="java.awt.image.BufferedImage" %>
<%@ page import="java.io.ByteArrayOutputStream" %>
<%@ page import="javax.imageio.ImageIO" %>
<%@ page import="model.Offer" %><%--
  Created by IntelliJ IDEA.
  User: seva0716
  Date: 2/20/2018
  Time: 10:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Delete offer</title>
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
    </style>
    <sec:csrfMetaTags/>
    <meta name="_csrf_parameter" content="_csrf" />
    <meta name="_csrf_header" content="X-CSRF-TOKEN" />
    <meta name="_csrf" content="e62835df-f1a0-49ea-bce7-bf96f998119c" />
</head>
<body>
<div class="container">
    <jsp:include page="header.jsp" />

    <div class="wrap rounded" style="margin-top: 10px">
        <%
            Category categoryById = (Category) request.getAttribute("category");
            String offerNameToDelete = (String) request.getAttribute("offerNameToDelete");
        %>

        <div style="margin-left: 2%">
            <p>Offering "<%=offerNameToDelete%>" has been deleted from the "<%=categoryById.getCategoryName()%>" category</p>
            <a href="/offers?category_id=<%=categoryById.getId()%>"><button type="button" class="btn btn-outline-primary">Go to <%=categoryById.getCategoryName()%> category page</button></a>
            <a href="/"><button type="button" class="btn btn-outline-primary">Go to main page</button></a>
        </div>

    </div>
    <div style="clear: both"></div>
    <!-- Site footer -->
    <footer class="footer">
        <p>&copy; Family joinery workshop Sequoia 2017-2018</p>
    </footer>
</div>
</body>
</html>