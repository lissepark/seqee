<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.mysql.jdbc.Blob" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.io.ByteArrayInputStream" %>
<%@ page import="java.awt.image.BufferedImage" %>
<%@ page import="java.io.ByteArrayOutputStream" %>
<%@ page import="javax.imageio.ImageIO" %>
<%@ page import="model.Category" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<html>
<head>
    <link rel="stylesheet" href="/css/bootstrap.css">
    <link href="/css/style.css" rel="stylesheet">
    <script src="/js/jquery-3.2.1.min.js"></script>
    <script src="/js/bootstrap.js"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="Резной декор, Мебель из дерева, Посуда из дерева, Торцевые разделочные доски, Рамки
        для фото, Индивидуальные проекты Family joinery workshop Sequoia">
    <title>Резной декор, Мебель из дерева, Посуда из дерева, Торцевые разделочные доски, Рамки
        для фото, Индивидуальные проекты Family joinery workshop Sequoia</title>
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

    <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel" style="width: 100%; height: 200px;margin-top: 6px">
        <ol class="carousel-indicators">
            <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
            <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
            <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
        </ol>
        <div class="carousel-inner">
            <div class="carousel-item active">
                <img src="images/002.png" alt="...">
                <div class="carousel-caption d-none d-md-block">
                    <h3 style="color: #0c5460">Type here what you want. Ex.: 'The first image'</h3>
                    <p style="color: #0c5460">image 1 will be changed</p>
                </div>
            </div>
            <div class="carousel-item">
                <img src="images/008.png" alt="...">
                <div class="carousel-caption d-none d-md-block">
                    <h3 style="color: #0c5460">Type here what you want. Ex.: 'The second image'</h3>
                    <p style="color: #0c5460">image 2 will be changed</p>
                </div>
            </div>
            <div class="carousel-item">
                <img src="images/019.png" alt="...">
                <div class="carousel-caption d-none d-md-block">
                    <h3 style="color: #0c5460">Type here what you want. Ex.: 'The third image'</h3>
                    <p style="color: #0c5460">image 3 will be changed</p>
                </div>
            </div>
        </div>
        <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
        </a>
        <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
        </a>
    </div>

    <div class="wrap rounded" style="margin-top: 10px">
        <%
            List<Category> categoryList1 = (List<Category>) request.getAttribute("categoryList");
            Iterator<Category> iterator1 = categoryList1.iterator();
            while (iterator1.hasNext()) {
                Category category1 = (Category) iterator1.next();%>
        <%
            Blob blob = (Blob) category1.getCategoryMainImage();
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
        <div class="wrapdiv rounded card" style="width: 15rem;">
            <%if (blob != null && blob.length() <= 1100000) {%>
            <a href="/offers?category_id=<%=category1.getId()%>"><img class="card-img-top img-thumbnail" src="data:image/png;base64,<%= b64 %>"
                                                                      alt="Card image cap" style="width: 238px;height: 172px"></a>
            <c:choose>
                <c:when test="${authenticated}">
                    <a href="/adminruslan/editcategory?category_id=<%=category1.getId()%>"><button type="button" class="btn btn-primary">Edit</button></a>
                </c:when>
            </c:choose>
            <%}else if(blob != null && blob.length() > 1100000)  {%>
            <a href="/offers?category_id=<%=category1.getId()%>"><img class="card-img-top img-thumbnail" src="<%= b64 %>"
                                                                      alt="Card image cap" style="width: 238px;height: 172px"></a>
            <c:choose>
                <c:when test="${authenticated}">
                    <a href="/adminruslan/editcategory?category_id=<%=category1.getId()%>"><button type="button" class="btn btn-primary">Edit</button></a>
                </c:when>
            </c:choose>
            <%} else {%>
            <a href="/offers?category_id=<%=category1.getId()%>"><img class="card-img-top img-thumbnail" src="<%= b64 %>"
                                                                      alt="Card image cap" style="width: 238px;height: 172px"></a>
            <c:choose>
                <c:when test="${authenticated}">
                    <a href="/adminruslan/editcategory?category_id=<%=category1.getId()%>"><button type="button" class="btn btn-primary">Edit</button></a>
                </c:when>
            </c:choose>
            <%}%>
            <a href="/offers?category_id=<%=category1.getId()%>"><div class="card-body" style="height: 50px;">
                <h5 class="card-title" style="text-align: center"><%=category1.getCategoryName()%></h5>
            </div></a>
        </div>
        <%}%>
    </div>
    <div style="clear: both"></div>

    <!-- Jumbotron -->
    <div class="jumbotron">
        <h1>Family joinery workshop Sequoia</h1>
        <p class="lead">Резной декор, Мебель из дерева, Посуда из дерева, Торцевые разделочные доски, Рамки
            для фото, Индивидуальные проекты</p>
        <p><a class="btn btn-lg btn-success" href="#" role="button">Get started</a></p>
    </div>

    <!-- Example row of columns -->
    <div class="row">
        <div class="col-lg-4">
            <h2>Block 1</h2>
            <p>Резной декор, Мебель из дерева, Посуда из дерева, Торцевые разделочные доски, Рамки для фото,  Индивидуальные проекты
                Резной декор
                3Д панели
                Арки
                Багет
                Гербы
                Двери

                Декор
                Вертикальный декор
                Горизонтальный декор
                Центральный декор
                Угловой декор
                Лица
                Корона
                Животные
            </p>
            <p><a class="btn btn-primary" href="#" role="button">View details &raquo;</a></p>
        </div>
        <div class="col-lg-4">
            <h2>Block 2</h2>
            <p>Декоративные панели
                Декоративные решетки
                Капители
                Колонны
                Кронштейны
                Лестницы и все для них
                Балясины
                Заходные столбы
                Окончания перил
                Шишки
                Мебельные фасады
                Ножки для мебели
                Панно
                Пилястра</p>
            <p><a class="btn btn-primary" href="#" role="button">View details &raquo;</a></p>
        </div>
        <div class="col-lg-4">
            <h2>Block 3</h2>
            <p>Православное
                Апостолы
                Богородица
                Декор
                Пророки
                Святые
                Святые Ветхозаветные
                Рамы и зеркала
                Розетки

                Мебель из дерева
                Спальни
                Столы
                Стулья

                Посуда из дерева
                Доски для дегустации и аператива
                Тарелки</p>
            <p><a class="btn btn-primary" href="#" role="button">View details &raquo;</a></p>
        </div>
    </div>

    <!-- Site footer -->
    <footer class="footer">
        <p>&copy; Family joinery workshop Sequoia 2017-2018</p>
    </footer>

</div> <!-- /container -->


<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script>window.jQuery || document.write('<script src="../../../../assets/js/vendor/jquery.min.js"><\/script>')</script>
<script src="../../../../assets/js/vendor/popper.min.js"></script>
<script src="../../../../dist/js/bootstrap.min.js"></script>
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="../../../../assets/js/ie10-viewport-bug-workaround.js"></script>

</body>
</html>