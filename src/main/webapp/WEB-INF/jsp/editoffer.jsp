<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="model.Offer" %>
<%@ page import="java.sql.Blob" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.io.ByteArrayInputStream" %>
<%@ page import="java.awt.image.BufferedImage" %>
<%@ page import="java.io.ByteArrayOutputStream" %>
<%@ page import="javax.imageio.ImageIO" %>
<%@ page import="model.Category" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="database.DataService" %><%--
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
            Offer offerById = (Offer) request.getAttribute("offerById");
            Category category = (new DataService()).getCategoryById(offerById.getOfferCategory());
            Blob blob = (Blob) offerById.getOfferMainImage();
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
            <form action="editoffer?${_csrf.parameterName}=${_csrf.token}&offer_id=<%=offerById.getId()%>" method="post" enctype="multipart/form-data">
                <p>
                    <input type="hidden" name="offer_id" value="<%=offerById.getId()%>"><br>
                    <input type="text" name="offerName" value="<%=offerById.getOfferName()%>">Type Offer name<br>
                    <input type="text" name="offerDescription" value="<%=offerById.getOfferDescription()%>">Type Offer description<br>
                    <select type="text" name="categoryId">
                        <option disabled>Choose the Category</option>
                        <option value="<%=offerById.getOfferCategory()%>" selected="selected"><%=category.getCategoryName()%></option>
                        <%while (iterator1.hasNext()) {
                            Category category1 = (Category) iterator1.next();%>
                        <option value="<%=category1.getId()%>"><%=category1.getCategoryName()%></option>
                        <%}%>
                    </select>
                </p><br/>
                <input type="file" name="file"><h5 style="color:darkgreen">Select image for Offering to upload:</h5><br/>
                <input class="btn btn-primary btn-lg" type="submit" value="Save changes">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
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
                <h5 class="card-title" style="text-align: center"><%=offerById.getOfferName()%></h5>
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