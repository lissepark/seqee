<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="model.Category" %>
<%@ page import="java.sql.Blob" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.io.ByteArrayInputStream" %>
<%@ page import="java.awt.image.BufferedImage" %>
<%@ page import="java.io.ByteArrayOutputStream" %>
<%@ page import="javax.imageio.ImageIO" %><%--
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
<jsp:include page="header.jsp" />

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
                ByteArrayInputStream bais = new ByteArrayInputStream(b);
                BufferedImage image = ImageIO.read(bais);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "png", baos);
                baos.flush();
                byte[] imageInByteArray = baos.toByteArray();
                baos.close();
                b64 = javax.xml.bind.DatatypeConverter.printBase64Binary(imageInByteArray);
            }else {
                b64 = "http://www.fjwsequoia.com/images/too_big_image.jpg";
            }
        }else{
            b64 = "http://www.fjwsequoia.com/images/stolen_image.png";
        }
    %>

    <div style="margin-left: 2%">
        <a href="/"><button type="button" class="btn btn-outline-primary">Go to main page</button></a>
        <h4>Please, fill the data and load an image</h4><br>
        <form action="editcategory?${_csrf.parameterName}=${_csrf.token}&category_id=<%=categoryById.getId()%>" method="post" enctype="multipart/form-data">
            <p>
                <input type="hidden" name="category_id" value="<%=categoryById.getId()%>"><br>
                <input type="text" name="categoryName" value="<%=categoryById.getCategoryName()%>">Type Category name<br>
                <input type="text" name="categoryDescription" value="<%=categoryById.getCategoryDescription()%>">Type Category description<br>
            </p>
            <h4 style="color:blue">Select image for Category to upload:</h4>
            <br/>
            <input type="file" name="file"><br/>
            <input class="btn btn-primary btn-lg" type="submit" value="Upload Image and send data">
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
</body>
</html>
