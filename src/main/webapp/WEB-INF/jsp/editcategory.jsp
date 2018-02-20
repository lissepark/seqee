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
                //BufferedImage image = new BufferedImage(600,400,BufferedImage.TYPE_4BYTE_ABGR);
                BufferedImage image = ImageIO.read(bais);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "png", baos);
                baos.flush();
                byte[] imageInByteArray = baos.toByteArray();
                baos.close();
                b64 = javax.xml.bind.DatatypeConverter.printBase64Binary(imageInByteArray);
            }else {
                b64 = "images/too_big_image.jpg";
            }
        }else{
            b64 = "images/stolen_image.png";
        }
    %>

    <div style="margin-left: 2%">
        <h4>Please, fill the data and load an image</h4><br>
        <form action="editcategory" method="post" enctype="multipart/form-data">
            <p>
                <input type="text" name="categoryName" value="<%=categoryById.getCategoryName()%>">Type Category name<br>
                <input type="text" name="categoryDescription" value="<%=categoryById.getCategoryDescription()%>">Type Category description<br>
            </p>
            <h4 style="color:blue">Select image for Category to upload:</h4>
            <br/>
            <input type="file" name="file"><br/>
            <input class="btn btn-primary btn-lg" type="submit" value="Upload Image and send data">
        </form>
    </div>

    <div class="wrapdiv rounded card" style="width: 15rem;">
        <%if (blob != null && blob.length() <= 1100000) {%>
        <a href="/offers?category_id=<%=categoryById.getId()%>"><img class="card-img-top img-thumbnail" src="data:image/png;base64,<%= b64 %>"
                                                                  alt="Card image cap" style="width: 238px;height: 172px"></a>
        <c:choose>
            <c:when test="${authenticated}">
                <a href="/editcategory?category_id=<%=categoryById.getId()%>"><button type="button" class="btn btn-primary">Edit</button></a>
            </c:when>
        </c:choose>
        <%}else if(blob != null && blob.length() > 1100000)  {%>
        <a href="/offers?category_id=<%=categoryById.getId()%>"><img class="card-img-top img-thumbnail" src="<%= b64 %>"
                                                                  alt="Card image cap" style="width: 238px;height: 172px"></a>
        <c:choose>
            <c:when test="${authenticated}">
                <a href="/editcategory?category_id=<%=categoryById.getId()%>"><button type="button" class="btn btn-primary">Edit</button></a>
            </c:when>
        </c:choose>
        <%} else {%>
        <a href="/offers?category_id=<%=categoryById.getId()%>"><img class="card-img-top img-thumbnail" src="<%= b64 %>"
                                                                  alt="Card image cap" style="width: 238px;height: 172px"></a>
        <c:choose>
            <c:when test="${authenticated}">
                <a href="/editcategory?category_id=<%=categoryById.getId()%>"><button type="button" class="btn btn-primary">Edit</button></a>
            </c:when>
        </c:choose>
        <%}%>
        <a href="/offers?category_id=<%=categoryById.getId()%>"><div class="card-body" style="height: 50px;">
            <h5 class="card-title" style="text-align: center"><%=categoryById.getCategoryName()%></h5>
        </div></a>
    </div>

</div>
<div style="clear: both"></div>
<!-- Site footer -->
<footer class="footer">
    <p>&copy; Family joinery workshop Sequoia 2017-2018</p>
</footer>
</body>
</html>
