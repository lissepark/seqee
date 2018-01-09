<%@ page import="model.Offer" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.mysql.jdbc.Blob" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.io.ByteArrayInputStream" %>
<%@ page import="java.awt.image.BufferedImage" %>
<%@ page import="java.io.ByteArrayOutputStream" %>
<%@ page import="javax.imageio.ImageIO" %><%--
  Created by IntelliJ IDEA.
  User: sergii
  Date: 1/9/18
  Time: 10:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <link rel="stylesheet" href="/css/bootstrap.css">
    <link href="/css/style.css" rel="stylesheet">
    <script src="/js/jquery-3.2.1.min.js"></script>
    <script src="/js/bootstrap.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
</head>
<body>
<div class="card-deck rounded mx-auto d-block">
    <%
        List<Offer> offerList1 = (List<Offer>) request.getAttribute("offerList");
        Iterator<Offer> iterator1 = offerList1.iterator();
        while (iterator1.hasNext()) {
            Offer offer1 = (Offer) iterator1.next();%>
    <%
        Blob blob = (Blob) offer1.getOfferMainImage();
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
        String b64 = javax.xml.bind.DatatypeConverter.printBase64Binary(imageInByteArray);
    %>
    <div class="card rounded float-left" style="width: 18rem;">
        <img class="card-img-top img-thumbnail" src="data:image/png;base64,<%= b64 %>"
             alt="Card image cap" style="width: 300px;height: 300px">
        <div class="card-body">
            <h5 class="card-title"><%=offer1.getOfferName()%></h5>
            <p class="card-text"><%=offer1.getOfferDescription()%></p>
        </div>
    </div>
    <%}
    out.flush();%>
</div>
<div style="clear: both"></div>
</body>
</html>
