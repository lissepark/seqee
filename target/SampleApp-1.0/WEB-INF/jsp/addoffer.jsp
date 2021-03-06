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
</head>
<body>
    <%
    List<Category> categoryList1 = (List<Category>) request.getAttribute("categoryList");
    Iterator<Category> iterator1 = categoryList1.iterator();
    %>
    <div style="margin-left: 2%">
        <h4>Please, fill the data and load an image</h4>
        <form action="addoffer" method="post" enctype="multipart/form-data">
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
            </p>
            <h4 style="color:blue">Select image to upload:</h4>
            <br/>
            <input type="file" name="file"><br/>
            <input class="btn btn-primary btn-lg" type="submit" value="Upload Image">
        </form>
    </div>
</body>
</html>
