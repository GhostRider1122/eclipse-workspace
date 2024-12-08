<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
	<%
		if(session.getAttribute("customerName") == null) {
			response.sendRedirect("login.jsp");
		}
	
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
	    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
	    response.setDateHeader("Expires", 0); // Proxies
	%>
	<div class="container">
        <div class="header">
            <h1>Electricity Bill Portal</h1>
            <div class="user-info">
                <span id="username"> 
                <%
                	String name = (String) session.getAttribute("customerName");
                	out.println("Welcome "+name);
                %>
                </span>
                <a href="LogoutServlet"><button>Logout</button></a>
            </div>
        </div>
        
        <nav class="navbar">
            <ul>
                <li><a href="home.jsp">Home</a></li>
                <li><a href="payBill.jsp">Pay Bill</a></li>
                <li><a href="registerComplaint.jsp">Register Complaint</a></li>
                <li><a href="complaintStatus.jsp">Complaint Status</a></li>
            </ul>
        </nav>

        <div class="center-content">
            <main class="welcome-page">
                <h1 style="color: blue;">Welcome to the Customer Portal</h1>
                <p>Select an option from the menu to proceed.</p>
            </main>
        </div>

    </div>
	
</body>
</html>