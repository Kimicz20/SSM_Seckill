<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" language="java" %>
<%@include file="common/tag.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>秒杀列表</title>
    <!-- 静态包含 只有一个servlet-->
    <%@include file="common/head.jsp" %>
</head>
<body>
    <div class="container">
        <div class="panel panel-default text-center">
            <div class="panel-heading text-center">${seckill.name}</div>
            <div class="panel-body"></div>
        </div>
    </div>
</body>
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</html>