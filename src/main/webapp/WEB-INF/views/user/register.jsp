<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Register page</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>
	<div class="alert alert-danger" role="alert">${message }</div>
	<h1>Register Page</h1>

	<form:form action="register.htm" method="post" modelAttribute="user">
		<div class="mb-3">
			<label for="username" class="form-label">Username</label> <input
				name="username" required type="text" class="form-control col-md-6"
				id="username">
		</div>
		<div class="mb-3">
			<label for="password" class="form-label">Password</label> <input
				name="password" required type="password"
				class="form-control col-md-6" id="password">
		</div>
		<div class="mb-3">
			<label for="email" class="form-label">Email</label> <input
				name="email" required type="email" class="form-control col-md-6"
				id="email">
		</div>
		<button type="submit" class="btn btn-primary" name="btnRegister">Register</button>
	</form:form>

	<form class="mt-4" action="login.htm">
		<button class="btn btn-outline-primary" name="btnLogin2">Go
			to Login Page</button>
	</form>
</body>
</html>