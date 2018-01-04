<%@ page import="model.Offer" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Offers</title>
</head>
<body>
<div><span class="label" style="margin-left:15px;">Offers</span></div>
<div><span class="label" style="margin-left:15px;"><a href="/">Main</a></span></div>
<ul>
    <%
        List<Offer> offerList1 = (List<Offer>) request.getAttribute("offerList");
        Iterator<Offer> iterator1 = offerList1.iterator();
        while (iterator1.hasNext()) {
            Offer offer1 = (Offer) iterator1.next();%>
    <li><%=offer1.getId()%>, offering=<%=offer1.getOfferName()%>
        <span class="label" style="margin-left: 30px;"><%=offer1.getOfferDescription()%></span>
        <span class="label" style="margin-left: 30px;"><%=offer1.getOfferImagePath()%></span>
    </li>
    <%}%>
</ul>

<div class="row marketing">
    <h4>Please, fill the image path</h4>
    <form action="offers" method="post" enctype="multipart/form-data">
        <h3 style="color:blue">Select image to upload:</h3>
        <br/>
        <input type="file" name="file"><br/>
        <input class="btn btn-primary btn-lg" type="submit" value="Upload Image">
    </form>
</div>

<img src="images/img2326962996828624582.png" alt="...">
</body>
</html>
