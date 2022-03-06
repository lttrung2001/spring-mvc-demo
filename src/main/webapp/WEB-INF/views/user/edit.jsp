<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit page</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>
	<h1>Edit Page</h1>
	<h1>${message }</h1>
	<form:form action="show-all.htm" method="post" modelAttribute="user">
		<div class="mb-3">
			<label for="username" class="form-label">Username</label> <input
				readonly value="${user.username }" name="username" type="text"
				class="form-control col-md-6" id="username">
		</div>
		<div class="mb-3">
			<label for="password" class="form-label">Password</label> <input
				value="${user.password }" name="password" required type="text"
				class="form-control col-md-6" id="password">
		</div>
		<div class="mb-3">
			<label for="email" class="form-label">Email</label> <input
				value="${user.email }" name="email" type="email" required
				type="email" class="form-control col-md-6" id="password">
			<div id="emailHelp" class="form-text">We'll never share your
				account with anyone else.</div>
		</div>
		<button type="submit" class="btn btn-primary" name="btnEdit">Edit</button>
	</form:form>

	<form class="mt-4" action="login.htm">
		<button class="btn btn-outline-primary" name="btnLogin2">Go
			to Login Page</button>
	</form>
</body>
</html>