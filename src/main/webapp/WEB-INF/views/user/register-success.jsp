<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<base href="${pageContext.servletContext.contextPath }/">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>
	<div class="alert alert-success" role="alert">${message }</div>
	<h1>Information Page</h1>
	<h1>Username: ${user.username }</h1>
	<h1>Password: ${user.password }</h1>
	<h1>Email: ${user.email }</h1>
	<img width="400px" alt="" src="img/${user.photo }"/>
	<h1>Please check your email to verify this account!</h1>
	<a class="btn btn-danger" href="${pageContext.request.contextPath }/login.htm">Logout</a>
</body>
</html>