<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<b>Complaint Registered Successfully!!</b> <br>
	<p>Your Complaint ID is: <%session.getAttribute("complaintId"); %></p>
	<a href="home.jsp">Go back to home page</a>
</body>
</html>