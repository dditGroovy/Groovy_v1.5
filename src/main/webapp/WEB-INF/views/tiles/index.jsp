<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html lang="ko">
<head>
    <meta charset="utf-8">
    <title>GROOVY</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/reset.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/commonStyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <link rel="icon" type="image/svg+xml" href="${pageContext.request.contextPath}/resources/favicon.svg">
<%--    <meta name="_csrf" content="${_csrf.token}"/>--%>
<%--    <meta name="_csrf_header" content="${_csrf.headerName}"/>--%>
<%--    <script>--%>
<%--        $(document).ready(function () {--%>
<%--            var token = $("meta[name='_csrf']").attr("content");--%>
<%--            var header = $("meta[name='_csrf_header']").attr("content");--%>
<%--            $(document).ajaxSend(function (e,xhr,options) {--%>
<%--                xhr.setRequestHeader(header, token);--%>
<%--            })--%>
<%--        })--%>
<%--    </script>--%>
</head>
<body>
<div id="loading">
    <div class="loading-img-wrap">
        <div class="loading-img img1"></div>
        <div class="loading-img img2"></div>
        <div class="loading-img img3"></div>
        <div class="loading-img img4"></div>
        <div class="loading-img img5"></div>
        <div class="loading-img img6"></div>
    </div>
</div>
<div class="wrapper">
    <tiles:insertAttribute name="aside"/>
    <div class="container">
        <tiles:insertAttribute name="floating"/>
        <tiles:insertAttribute name="body"/>
    </div>
    <tiles:insertAttribute name="serviceBar"/>
</div>
<script>
    const loading_page = document.getElementById("loading");
    window.onload = function(){
        setTimeout(function() {
            window.scrollTo(0, 0);
            loading_page.style.display = 'none';
            document.body.style.overflowY = 'auto';
        }, 100);

    }
</script>
</body>
</html>

