<%@ page import="model.Offer" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %><%--
  Created by IntelliJ IDEA.
  User: sergii
  Date: 8/21/17
  Time: 10:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Offers</title>
</head>
<body>
<div><span class="label" style="margin-left:15px;">Offers</span></div>
    <ul>
        <%
            List<Offer> offerList1 = (List<Offer>) request.getAttribute("offerList");
            Iterator<Offer> iterator1 = offerList1.iterator();
            while (iterator1.hasNext()) {
                Offer offer1 = (Offer) iterator1.next();%>
        <li><%=offer1.getId()%>, offering=<%=offer1.getOfferName()%>
            <span class="label" style="margin-left: 30px;"><%=offer1.getOfferDescription()%></span>
        </li>
        <%}%>
    </ul>
</body>
</html>
