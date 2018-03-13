<%@ page import="model.Offer" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.awt.image.BufferedImage" %>
<%@ page import="javax.imageio.ImageIO" %>
<%@ page import="java.io.ByteArrayInputStream" %>
<%@ page import="java.io.ByteArrayOutputStream" %>
<%@ page import="com.mysql.jdbc.Blob" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="model.Category" %>
<%@ page import="database.DataService" %>
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
    <sec:authorize access="authenticated" var="authenticated"/>
    <%
    Category categById = (Category) request.getAttribute("categoryById");
    %>
    <div style="text-align: center;margin: auto"><span class="label"><h3><%=categById.getCategoryName()%></h3></span></div>
    <%
        int cid = (int) request.getAttribute("category_id");
        String breadcrumb = "main";
        String path1 = "";
        Category categoryCrumb = (new DataService()).getCategoryById(cid);
        String pathLast = "/" + categoryCrumb.getCategoryName();
        while (categoryCrumb.getParentCategory() != 0) {
            categoryCrumb = (new DataService()).getCategoryById(categoryCrumb.getParentCategory());
            path1 = "/" + categoryCrumb.getCategoryName() + path1;
        }
        breadcrumb = breadcrumb + path1;
    %>
    <div><h3><%=breadcrumb%></h3></div>
    <div class="wrap rounded card-grid" style="margin-top: 10px">
        <%
        List<Category> categoryList = (List<Category>) request.getAttribute("categoryList");
        Iterator<Category> iterator = categoryList.iterator();
        while (iterator.hasNext()) {
            Category category = (Category) iterator.next();
            if(category.getParentCategory() == cid) {%>
        <%
            Blob blob = (Blob) category.getCategoryMainImage();
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
        <div class="wrapdiv rounded card_local">
            <%if (blob != null && blob.length() <= 1100000) {%>
            <a href="/offers?category_id=<%=category.getId()%>"><img class="card-img-top img-thumbnail" src="data:image/png;base64,<%= b64 %>"
                                                                      alt="Card image cap" style="width: 238px;height: 172px"></a>
            <c:choose>
                <c:when test="${authenticated}">
                    <a href="/adminruslan/editcategory?category_id=<%=category.getId()%>"><button type="button" class="btn btn-primary">Edit</button></a>
                </c:when>
            </c:choose>
            <%}else if(blob != null && blob.length() > 1100000)  {%>
            <a href="/offers?category_id=<%=category.getId()%>"><img class="card-img-top img-thumbnail" src="<%= b64 %>"
                                                                      alt="Card image cap" style="width: 238px;height: 172px"></a>
            <c:choose>
                <c:when test="${authenticated}">
                    <a href="/adminruslan/editcategory?category_id=<%=category.getId()%>"><button type="button" class="btn btn-primary">Edit</button></a>
                </c:when>
            </c:choose>
            <%} else {%>
            <a href="/offers?category_id=<%=category.getId()%>"><img class="card-img-top img-thumbnail" src="<%= b64 %>"
                                                                      alt="Card image cap" style="width: 238px;height: 172px"></a>
            <c:choose>
                <c:when test="${authenticated}">
                    <a href="/adminruslan/editcategory?category_id=<%=category.getId()%>"><button type="button" class="btn btn-primary">Edit</button></a>
                </c:when>
            </c:choose>
            <%}%>
            <a href="/offers?category_id=<%=category.getId()%>">
                <div class="card-body" style="height: 50px;">
                    <h5 class="card-title" style="text-align: center"><%=category.getCategoryName()%></h5>
                </div>
            </a>
        </div>
        <%}}%>
        <div style="border: none;width: 238px;"></div>
    </div>
    <div style="clear: both"></div>

    <div class="wrap rounded card-grid" style="margin-top: 10px">
        <%
        List<Offer> offerList1 = (List<Offer>) request.getAttribute("offerList");
        Iterator<Offer> iterator1 = offerList1.iterator();
        while (iterator1.hasNext()) {
            Offer offer1 = (Offer) iterator1.next();%>
        <%
            Blob blob = (Blob) offer1.getOfferMainImage();
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
            } else {
                b64 = "images/stolen_image.png";
            }
        %>
        <div class="wrapdiv rounded card_local">
            <%if (blob != null && blob.length() <= 1100000) {%>
            <img class="card-img-top img-thumbnail" src="data:image/png;base64,<%= b64 %>" alt="Card image cap" style="width: 238px;height: 172px">
            <c:choose>
                <c:when test="${authenticated}">
                    <a href="/adminruslan/editoffer?offer_id=<%=offer1.getId()%>"><button type="button" class="btn btn-primary">Edit</button></a>
                    <a href="/adminruslan/deleteoffer?offer_id=<%=offer1.getId()%>&category_id=<%=cid%>"><button type="button" class="btn btn-danger">Delete</button></a>
                </c:when>
            </c:choose>
            <%}else if(blob != null && blob.length() > 1100000)  {%>
            <img class="card-img-top img-thumbnail" src="<%= b64 %>" alt="Card image cap" style="width: 238px;height: 172px">
            <c:choose>
            <c:when test="${authenticated}">
                <a href="/adminruslan/editoffer?offer_id=<%=offer1.getId()%>"><button type="button" class="btn btn-primary">Edit</button></a>
                <a href="/adminruslan/deleteoffer?offer_id=<%=offer1.getId()%>&category_id=<%=cid%>"><button type="button" class="btn btn-danger">Delete</button></a>
            </c:when>
            </c:choose>
            <%} else {%>
            <img class="card-img-top img-thumbnail" src="<%= b64 %>" alt="Card image cap" style="width: 238px;height: 172px">
            <c:choose>
            <c:when test="${authenticated}">
                <a href="/adminruslan/editoffer?offer_id=<%=offer1.getId()%>"><button type="button" class="btn btn-primary">Edit</button></a>
                <a href="/adminruslan/deleteoffer?offer_id=<%=offer1.getId()%>&category_id=<%=cid%>"><button type="button" class="btn btn-danger">Delete</button></a>
            </c:when>
            </c:choose>
            <%}%>
            <div class="card-body" style="height: 50px;">
                <h5 class="card-title" style="text-align: center"><%=offer1.getOfferName()%></h5>
                <p class="card-text"><%=offer1.getOfferDescription()%></p>
            </div>
        </div>
        <%}%>
        <div style="border: none;width: 238px;"></div>
    </div>
    <div style="clear: both"></div>
    <!-- Site footer -->
    <footer class="footer">
        <p>&copy; Family joinery workshop Sequoia 2017-2018</p>
    </footer>
</div>
</body>
</html>
