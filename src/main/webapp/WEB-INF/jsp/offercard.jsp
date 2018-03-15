<%@ page import="model.Offer" %>
<%@ page import="java.sql.Blob" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.io.ByteArrayInputStream" %>
<%@ page import="java.awt.image.BufferedImage" %>
<%@ page import="javax.imageio.ImageIO" %>
<%@ page import="java.io.ByteArrayOutputStream" %>
<%@ page import="model.Category" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<html>
<head>
    <link rel="stylesheet" href="/css/bootstrap.css">
    <link href="/css/style.css" rel="stylesheet">
    <script src="/js/jquery-3.2.1.min.js"></script>
    <script src="/js/bootstrap.js"></script>
    <title>Offer</title>
</head>
<body>
<div class="container">
    <jsp:include page="header.jsp"/>
    <div><h3>Page is under development</h3></div>
    <%
        String breadcrumb = (String) request.getAttribute("breadcrumb");
    %>
    <div style="margin-top: 10px"><h6><%=breadcrumb%></h6></div>
    <%
        Offer offerById = (Offer) request.getAttribute("offer");
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

        <div class="row">
            <div class="col">
                <div class="wrapdiv rounded card" style="width: 15rem;">
                    <%if (blob != null && blob.length() <= 1100000) {%>
                    <img class="card-img-top img-thumbnail" src="data:image/png;base64,<%= b64 %>" alt="Card image cap" style="width: 300px;height: 218px">
                    <%}else if(blob != null && blob.length() > 1100000)  {%>
                    <img class="card-img-top img-thumbnail" src="<%= b64 %>" alt="Card image cap" style="width: 300px;height: 218px">
                    <%} else {%>
                    <img class="card-img-top img-thumbnail" src="<%= b64 %>" alt="Card image cap" style="width: 300px;height: 218px">
                    <%}%>

                </div>
            </div>
            <div class="col-6">

                <div style="margin-left: 2%">
                    <div class="card-body" style="height: 50px;">
                        <h5 class="card-title" style="text-align: center"><%=offerById.getOfferName()%></h5>
                    </div>

                    <%--=offerById.getId()--%>
                    <%--=offerById.getOfferDescription()--%>
                </div>

            </div>
        </div>

    <footer class="footer">
        <p>&copy; Family joinery workshop Sequoia 2017-2018</p>
    </footer>
</div>
</body>
</html>