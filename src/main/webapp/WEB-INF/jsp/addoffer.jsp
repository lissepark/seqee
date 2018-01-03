<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Sequoia</title>
</head>
<body>
    <div class="row marketing">
        <h4>Please, fill the data and load an image</h4>
        <form action="addoffer" method="post" enctype="multipart/form-data">
            <p>
                <input type="text" name="offerName"><Br>
                <input type="text" name="offerDescription" ><Br>
            </p>
            <h3 style="color:blue">Select image to upload:</h3>
            <br/>
            <input type="file" name="file"><br/>
            <input class="btn btn-primary btn-lg" type="submit" value="Upload Image">
        </form>
    </div>
</body>
</html>
