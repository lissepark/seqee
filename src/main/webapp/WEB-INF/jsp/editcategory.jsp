<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="model.Category" %>
<%@ page import="java.sql.Blob" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.io.ByteArrayInputStream" %>
<%@ page import="java.awt.image.BufferedImage" %>
<%@ page import="java.io.ByteArrayOutputStream" %>
<%@ page import="javax.imageio.ImageIO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %><%--
  Created by IntelliJ IDEA.
  User: seva0716
  Date: 2/20/2018
  Time: 10:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit category</title>
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
    <%
        List<Category> categoryList1 = (List<Category>) request.getAttribute("categoryList");
        Iterator<Category> iterator1 = categoryList1.iterator();
    %>

    <div class="wrap rounded" style="margin-top: 10px">
        <%
            Category categoryById = (Category) request.getAttribute("categoryById");

            Blob blob = (Blob) categoryById.getCategoryMainImage();
            String b64 = "";
            if (blob != null) {
                if (blob.length() <= 1100000) {
                    byte b[] = new byte[(int) blob.length()];
                    try {
                        b = blob.getBytes(1, (int) blob.length());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    ByteArrayInputStream bais = null;
                    ByteArrayOutputStream baos = null;
                    try {
                        bais = new ByteArrayInputStream(b);
                        BufferedImage image = ImageIO.read(bais);
                        baos = new ByteArrayOutputStream();
                        ImageIO.write(image, "png", baos);
                        baos.flush();
                        byte[] imageInByteArray = baos.toByteArray();
                        b64 = javax.xml.bind.DatatypeConverter.printBase64Binary(imageInByteArray);
                    } finally {
                        if (bais != null) {
                            bais.close();
                        }
                        if (baos != null) {
                            baos.close();
                        }
                    }

                }else {
                    b64 = "http://www.fjwsequoia.com/images/too_big_image.jpg";
                }
            }else{
                b64 = "http://www.fjwsequoia.com/images/stolen_image.png";
            }
        %>

        <div style="margin-left: 2%">
            <a href="/"><button type="button" class="btn btn-outline-primary">Go to main page</button></a><br/>
            <h4>Please, fill the data and load an image</h4><br>
            <form action="editcategory?${_csrf.parameterName}=${_csrf.token}&category_id=<%=categoryById.getId()%>" method="post" enctype="multipart/form-data">
                <input type="hidden" name="category_id" value="<%=categoryById.getId()%>">
                <div class="form-group">
                    <label for="inputCategoryName">Type Category name (leave the same if you don't want to change)</label>
                    <input type="text" name="categoryName" value="<%=categoryById.getCategoryName()%>" id="inputCategoryName" class="form-control">
                </div>
                <div class="form-group">
                    <label for="inputCategoryDescription">Type Category description (leave the same if you don't want to change)</label>
                    <input type="text" name="categoryDescription" value="<%=categoryById.getCategoryDescription()%>" id="inputCategoryDescription" class="form-control">
                </div>
                <div class="form-group">
                    <label for="selectParentCategory">Choose the parent category (leave the same if you want it has to be 'Top level' category)</label>
                    <select type="text" id="categoryId" name="categoryId" id="selectParentCategory" class="form-control">
                        <option disabled>Choose the parent category</option>
                        <option value="<%=categoryById.getParentCategory()%>" selected="selected">Current category</option>
                        <option value="0">Top category</option>
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
                <input class="btn btn-primary btn-lg" type="submit" value="Save changes">
                <input type="hidden"  name="${_csrf.parameterName}"   value="${_csrf.token}"/>
            </form>
        </div>
        <div class="wrapdiv rounded card" style="width: 15rem;">
            <%if (blob != null && blob.length() <= 1100000) {%>
            <img class="card-img-top img-thumbnail" src="data:image/png;base64,<%= b64 %>" alt="Card image cap" style="width: 238px;height: 172px">
            <%}else if(blob != null && blob.length() > 1100000)  {%>
            <img class="card-img-top img-thumbnail" src="<%= b64 %>" alt="Card image cap" style="width: 238px;height: 172px">
            <%} else {%>
            <img class="card-img-top img-thumbnail" src="<%= b64 %>" alt="Card image cap" style="width: 238px;height: 172px">
            <%}%>
            <div class="card-body" style="height: 50px;">
                <h5 class="card-title" style="text-align: center"><%=categoryById.getCategoryName()%></h5>
            </div>
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
