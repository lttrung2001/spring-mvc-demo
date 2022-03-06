<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>
	<h1>Account List</h1>
	<a class="btn btn-primary" href="${pageContext.request.contextPath}/register.htm">Register</a>
	<table class="table">
		<thead>
			<tr>
				<th scope="col">STT</th>
				<th scope="col">Tài khoản</th>
				<th scope="col">Mật khẩu</th>
				<th scope="col">Email</th>
				<th scope="col">Đã kích hoạt</th>
				<th scope="col">Sửa</th>
				<th scope="col">Xóa</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="item" items="${users }" varStatus="num">
				<tr>
					<th scope="row">${num.count }</th>
					<td>${item.username }</td>
					<td>${item.password }</td>
					<td>${item.email }</td>
					<td>${item.enabled }</td>
					<td><a class="btn btn-primary" role="button"
						href="${pageContext.request.contextPath}/${item.username }.htm?linkEdit">Edit</a>
					</td>
					<td><a class="btn btn-danger" role="button"
						href="${pageContext.request.contextPath}/${item.username }.htm?linkDelete">Delete</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>