<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>
	<div class="alert alert-success" role="alert">${message }</div>
	<h1>Information Page</h1>
	<h1>Username: ${user.username }</h1>
	<h1>Password: ${user.password }</h1>
	<h1>Email: ${user.email }</h1>
	<a class="btn btn-primary mb-4" role="button"
		href="${pageContext.request.contextPath}/${user.username }.htm?linkEdit">Edit</a>
	<form action="login.htm">
		<button class="btn btn-danger" name="btnLogin2">Log out</button>
	</form>
</body>
</html>