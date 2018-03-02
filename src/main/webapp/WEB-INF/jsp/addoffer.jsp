<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="model.Category" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %><%--
  User: sergii
  Date: 1/11/2018
  Time: 4:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Sequoia - Add offering</title>
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
    <%
    List<Category> categoryList1 = (List<Category>) request.getAttribute("categoryList");
    Iterator<Category> iterator1 = categoryList1.iterator();
    %>
    <div class="container">
        <jsp:include page="header.jsp"/>
        <div style="margin-left: 2%">
            <h4>Please, fill the data and load an image</h4>
            <form action="addoffer?${_csrf.parameterName}=${_csrf.token}" method="post" enctype="multipart/form-data">
                <p>
                    <input type="text" name="offerName">Type the name of the offer<br>
                    <input type="text" name="offerDescription">Type the description of the offer<br>
                    <select type="text" name="categoryId">
                        <option disabled>Choose the Category</option>
                        <%while (iterator1.hasNext()) {
                            Category category1 = (Category) iterator1.next();%>
                        <option value="<%=category1.getId()%>"><%=category1.getCategoryName()%></option>
                        <%}%>
                    </select>
                </p><br/>
                <h5 style="color:darkgreen">Select image to upload:</h5>
                <br/>
                <input type="file" name="file"><br/>
                <input class="btn btn-primary btn-lg" type="submit" value="Save Offering">
            </form>
        </div>
    </div>
</body>
</html>
