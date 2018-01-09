<%@ page import="model.Offer" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.mysql.jdbc.Blob" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.io.ByteArrayInputStream" %>
<%@ page import="java.awt.image.BufferedImage" %>
<%@ page import="java.io.ByteArrayOutputStream" %>
<%@ page import="javax.imageio.ImageIO" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
</head>
<body>

<div class="container">

    <nav class="navbar navbar-expand-lg navbar-light bg-light" style="margin-left: auto">
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="#">Главная<span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Мой кабинет</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Оплата и доставка</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Обратная связь</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Контакты</a>
                </li>
            </ul>
            <form class="form-inline my-2 my-lg-0">
                <input class="form-control mr-sm-2" type="text" placeholder="Search" aria-label="Search">
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
            </form>
        </div>
    </nav>

    <div class="masthead" style="margin-top: 10px">
        <div class="container">
            <div class="row">
                <div class="col">
                    <img src="images/seq_logo.png" class="rounded float-left" alt="Sequoia">
                </div>
                <div class="col-md-auto">
                    <p class="text-lg-left h4 text-muted">Семейная столярная мастерская</p>
                    <p class="text-xl-left h3 text-muted">Family joinery workshop Sequoia</p>
                    <h6 class="text-sm-left">+38(099)682-15-44</h6>
                    <h6 class="text-sm-left">+38(099)349-63-12</h6>
                    <h6 class="text-sm-left">fjwsequoia@gmail.com</h6>
                </div>
                <div class="col col-lg-2" style="align-content: flex-end">
                    Корзина
                </div>
            </div>
        </div>
    </div>

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
        <div class="card rounded float-left" style="width: 14rem;">
            <img class="card-img-top img-thumbnail" src="data:image/png;base64,<%= b64 %>"
                 alt="Card image cap" style="width: 220px;height: 160px">
            <div class="card-body">
                <h5 class="card-title"><%=offer1.getOfferName()%></h5>
                <p class="card-text"><%=offer1.getOfferDescription()%></p>
            </div>
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
