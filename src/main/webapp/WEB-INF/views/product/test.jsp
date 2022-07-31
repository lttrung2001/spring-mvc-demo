<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Test</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="description" content="Colo Shop Template">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Khai báo base path (mỗi trang đều phải khai báo cái này) -->
<base href="${pageContext.servletContext.contextPath }/">
<!-- Khai báo đường dẫn các file css, js dùng thẻ c:url -->
<!-- Tài liệu về c:url -> https://www.tutorialspoint.com/jsp/jstl_core_url_tag.htm -->
<!-- T chưa hiểu rõ tại sao phải xài cái này. M có thể xài hoặc không  -->
<!-- Lưu ý: Khi muốn dùng c:url thì phải khai báo thư viện như dòng số 3 -->
<link rel="stylesheet" type="text/css"
	href="<c:url value='styles/bootstrap4/bootstrap.min.css'/>">
<link
	href="<c:url value='plugins/font-awesome-4.7.0/css/font-awesome.min.css'/>" 
	rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css"
	href="<c:url value='plugins/OwlCarousel2-2.2.1/owl.carousel.css'/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='plugins/OwlCarousel2-2.2.1/owl.theme.default.css'/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='plugins/OwlCarousel2-2.2.1/animate.css'/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='plugins/jquery-ui-1.12.1.custom/jquery-ui.css'/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='styles/categories_styles.css'/>">
<link rel="stylesheet" type="text/css"
	href="<c:url value='styles/categories_responsive.css'/>">
</head>
<body>
<h1>Test</h1>
<!-- 	Dòng lệnh include 1 file đã tách vào 1 page -->
	<%@include file="/WEB-INF/views/product/mainnavigation.jsp" %>
</body>
</html>