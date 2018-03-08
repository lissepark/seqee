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
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
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
<%
    List<Category> categoryList1 = (List<Category>) request.getAttribute("categoryList");
    Iterator<Category> iterator1 = categoryList1.iterator();
%>
<div class="container">
    <jsp:include page="header.jsp"/>
            <%
                if (request.getAttribute("success") != null) {
                    int success = (int) request.getAttribute("success");
                    if (success > 0) {%>
                <h4 style="color: darkgreen">Category has been successfully added. Would you like to add more categories?</h4>
                <%
                    } else if (success <= 0) { %>
                <h4 style="color: red">Category hasn't been added or has been added incompletely. Please check.</h4>
            <% }
                }
            %>
        <div class="row">
            <div class="col-8">
    <h4>Please, fill the data and load an image</h4><br>
    <form action="addcategory?${_csrf.parameterName}=${_csrf.token}" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label for="inputCategoryName">Type Category name</label>
            <input type="text" name="categoryName" id="inputCategoryName" class="form-control">
        </div>
        <div class="form-group">
            <label for="inputCategoryDescription">Type Category description</label>
            <input type="text" name="categoryDescription" id="inputCategoryDescription" class="form-control">
        </div>
        <div class="form-group">
            <label for="selectParentCategory">Choose the parent category (leave the same if you want it has to be 'Top level' category)</label>
            <select type="text" name="categoryId" id="selectParentCategory" class="form-control">
                <option disabled>Choose the parent category</option>
                <option value="0" selected="selected">Top level category</option>
                <%while (iterator1.hasNext()) {
                    Category category1 = (Category) iterator1.next();%>
                <option value="<%=category1.getId()%>"><%=category1.getCategoryName()%></option>
                <%}%>
            </select>
        </div>
        <div class="form-check">
            <label class="form-check-label">
                <input type="checkbox" name="isCategoryHide" value="1" class="form-check-input">
                Hide Category (Check checkbox if you want to hide this category)
            </label>
        </div>
        <div class="form-group">
            <label for="mainCategoryImage">Select image to upload:</label>
            <input type="file" name="file" class="form-control-file" id="mainCategoryImage">
        </div>
        <input class="btn btn-primary btn-lg" type="submit" value="Save category">
    </form>
    </div>
    <div class="col">
        <a href="/adminruslan/addoffer" class="btn btn-primary btn-lg active" role="button" aria-pressed="true">Add an Offering</a>
    </div>
</div>
</body>
</html>