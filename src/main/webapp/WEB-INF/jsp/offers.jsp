<%@ page import="model.Offer" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.awt.image.BufferedImage" %>
<%@ page import="javax.imageio.ImageIO" %>
<%@ page import="java.io.ByteArrayInputStream" %>
<%@ page import="java.io.ByteArrayOutputStream" %>
<%@ page import="com.mysql.jdbc.Blob" %>
<%@ page import="java.sql.SQLException" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <link rel="stylesheet" href="/css/bootstrap.css">
    <link href="/css/style.css" rel="stylesheet">
    <script src="/js/jquery-3.2.1.min.js"></script>
    <script src="/js/bootstrap.js"></script>
    <title>Offers</title>
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

<div><span class="label" style="margin-left:15px;">Offers</span></div>
<div><span class="label" style="margin-left:15px;"><a href="/">Main</a></span></div>
<div class="wrap rounded" style="margin-top: 10px">
    <%
        List<Offer> offerList1 = (List<Offer>) request.getAttribute("offerList");
        Iterator<Offer> iterator1 = offerList1.iterator();
        while (iterator1.hasNext()) {
            Offer offer1 = (Offer) iterator1.next();%>
        <%
            Blob blob = (Blob) offer1.getOfferMainImage();
            String b64 = "";
            if (blob != null) {
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
            } else {
                b64 = "images/stolen_image.png";
            }
        %>
    <div class="wrapdiv rounded card" style="width: 15rem;">
        <%if (blob != null) {%>
        <img class="card-img-top img-thumbnail" src="data:image/png;base64,<%= b64 %>"
             alt="Card image cap" style="width: 238px;height: 172px">
        <%}else{%>
        <img class="card-img-top img-thumbnail" src="<%= b64 %>"
             alt="Card image cap" style="width: 238px;height: 172px">
        <%}%>
        <div class="card-body" style="height: 50px;">
            <h5 class="card-title" style="text-align: center"><%=offer1.getOfferName()%></h5>
            <p class="card-text"><%=offer1.getOfferDescription()%></p>
        </div>
    </div>
    <%}%>
</div>
<div style="clear: both"></div>
    <!-- Site footer -->
    <footer class="footer">
        <p>&copy; Family joinery workshop Sequoia 2017-2018</p>
    </footer>
</div>
</body>
</html>
