<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register Complaint</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>

	<%
		if(session.getAttribute("customerName") == null) {
			response.sendRedirect("login.jsp");
			return;
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
                <h2>Register Complaint</h2>
                <form id="complaintForm" action="RegComp">
                    <label for="complaintType">Complaint Type:
                        <select id="complaintType" name="complaintType" onchange="updateCategoryOptions()" required>
                            <option value="" disabled selected>Select Type</option>
                            <option value="Billing">Billing Related</option>
                            <option value="Voltage">Voltage Related</option>
                            <option value="Disruption">Frequent Disruption</option>
                            <option value="StreetLight">Street Light Related</option>
                            <option value="Pole">Pole Related</option>
                        </select>
                    </label>
    
                    <label for="category">Category: 
                        <select id="category" name="category" required>
                            <option value="" disabled selected>Select Category</option>
                        </select>
                    </label>
                	
                	<%
                	String c_no = (String) session.getAttribute("consumerNo");
                	%>
    
                    <label for="contactPerson">Contact Person: <input type="text" id="contactPerson" name="contactPerson" required></label>
                    <label for="landmark">Landmark: <input type="text" id="landmark" name="landmark" required></label>
                    <label for="consumerNo">Consumer No: <input type="text" name="consumerNo" readonly="readonly" value="<%out.println(c_no);%>" placeholder="<%out.println(c_no);%>"></label>
                    <label for="description">Problem Description: <textarea id="description" name="description" rows="4" required></textarea></label>
                    <label for="mobile">Mobile Number: <input type="text" id="mobile" name="mobile" minlength="10" maxlength="10" required></label>
                    <label for="address">Address: <textarea id="address" name="address" rows="4" required></textarea></label>
                
    
                    <div class="btn">
                        <button class="reset" type="reset" onclick="resetForm()">Reset</button>
                        <button class="submit" type="submit" onclick="submitComplaint()">Submit Complaint</button>
                    </div>
                    
                </form>
            </main>
        </div>

    </div>
	<script src="regComp.js"></script>
</body>
</html>