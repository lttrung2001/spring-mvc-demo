<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body id="body">
<a style="text-decoration: none; font-size: 32px;" href="${pageContext.request.contextPath}/login.htm">Go to login page</a>
<br>
<a style="text-decoration: none; font-size: 32px;" href="${pageContext.request.contextPath}/register.htm">Go to register page</a>
<a href="${pageContext.request.contextPath}/TestServlet?id=123">Test verify</a>
</body>
</html>