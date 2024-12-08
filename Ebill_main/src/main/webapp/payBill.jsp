<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pay Bill</title>
    <link rel="stylesheet" href="payBill.css">
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
            <main class="bill-payment">
                <h2>View and Select Bills to Pay</h2>
                
                <!-- Bill selection table -->
                <table>
                    <thead>
                        <tr>
                            <th>Month</th>
                            <th>Amount</th>
                            <th>Select</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>January</td>
                            <td>Rs: 1000</td>
                            <td><input type="checkbox" name="bill" value="1000" onclick="calculateTotal()"></td>
                        </tr>
                        <tr>
                            <td>February</td>
                            <td>Rs: 1500</td>
                            <td><input type="checkbox" name="bill" value="1500" onclick="calculateTotal()"></td>
                        </tr>
                        <tr>
                            <td>March</td>
                            <td>Rs: 1200</td>
                            <td><input type="checkbox" name="bill" value="1200" onclick="calculateTotal()"></td>
                        </tr>
                    </tbody>
                </table>
                    
                    <!-- Total Amount and Proceed to Pay -->
                <div class="payment-info">
                    <p class="total-amount">Total Amount: <span id="totalAmount">₹0</span></p>
                    <button class="submit" type="button" onclick="proceedToPay()">Proceed to Pay</button>
                </div>
                
            </main>
        </div>
        
        <!-- Hidden Form for Payment Gateway -->
        <form id="paymentForm" action="https://sandbox.payu.in/_payment" method="POST" style="display: none;">
            <input type="hidden" name="key" value="your_payment_gateway_key">
            <input type="hidden" name="txnid" value="unique_transaction_id">
            <input type="hidden" name="amount" id="paymentAmount">
            <input type="hidden" name="productinfo" value="Electricity Bill Payment">
            <input type="hidden" name="firstname" id="customerName" value="Customer Name">
            <input type="hidden" name="email" id="customerEmail" value="customer@example.com">
            <input type="hidden" name="phone" id="customerPhone" value="9876543210">
            <input type="hidden" name="surl" value="http://localhost:8080/success">
            <input type="hidden" name="furl" value="http://localhost:8080/failure">
        </form>
        
    </div>
	<script src="app.js"></script>
</body>
</html>