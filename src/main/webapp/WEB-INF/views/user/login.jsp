<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>
	<div class="alert alert-danger" role="alert">${message }</div>
	<h1>Login Page</h1>

	<form:form action="login.htm" method="post" modelAttribute="user">
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
		<button type="submit" class="btn btn-primary" name="btnLogin">Login</button>
	</form:form>

	<form class="mt-4" action="register.htm">
		<button class="btn btn-outline-primary" name="btnRegister2">Go
			to Register Page</button>
	</form>
</body>
</html>