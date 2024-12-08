<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Complaint Status</title>
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
            <main class="complaint_container">
                <h2>Complaint Status</h2>
                <form id="complaintStatusForm">
                    <label for="complaintId">Enter Complaint ID:<input type="text" id="complaintId" required></label>
                    
    
                    <div class="btn">
                        <button class="submit" type="button" onclick="checkComplaintStatus()">Check Status</button>
                    </div>
        
                </form>
    
                <div id="statusResult" style="margin-top: 20px; text-align: center;"></div>
            </main>
        </div>
        
    </div>
    

</body>
</html>