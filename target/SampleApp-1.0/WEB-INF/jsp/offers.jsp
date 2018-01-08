<%@ page import="model.Offer" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.awt.image.BufferedImage" %>
<%@ page import="javax.imageio.ImageIO" %>
<%@ page import="java.io.ByteArrayInputStream" %>
<%@ page import="java.io.ByteArrayOutputStream" %>
<%@ page import="com.mysql.jdbc.Blob" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.FileOutputStream" %>
<%@ page import="java.awt.image.RenderedImage" %>
<%@ page import="com.sun.org.apache.xerces.internal.impl.dv.util.Base64" %>
<%@ page import="javax.xml.bind.DatatypeConverter" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <link rel="stylesheet" href="/css/bootstrap.css">
    <link href="/css/style.css" rel="stylesheet">
    <script src="/js/jquery-3.2.1.min.js"></script>
    <script src="/js/bootstrap.js"></script>
    <title>Offers</title>
</head>
<body>
<div><span class="label" style="margin-left:15px;">Offers</span></div>
<div><span class="label" style="margin-left:15px;"><a href="/">Main</a></span></div>
<div class="card-deck rounded mx-auto d-block" style="margin: auto">
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
/*
            File file = new File("/home/sergii/Documents/"+offer1.getId()+"_image.png");//for the local host
            FileOutputStream fos = new FileOutputStream(file);            
            fos.write(b);
            fos.close();
            */
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
    <%}%>
</div>
<div style="clear: both"></div>
<div class="row marketing">
    <h4>Please, fill the image path</h4>
    <form action="offers" method="post" enctype="multipart/form-data">
        <h3 style="color:blue">Select image to upload:</h3>
        <br/>
        <input type="file" name="file"><br/>
        <input class="btn btn-primary btn-lg" type="submit" value="Upload Image">
    </form>
</div>

</body>
</html>
