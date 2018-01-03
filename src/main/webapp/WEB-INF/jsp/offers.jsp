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
</body>
</html>
