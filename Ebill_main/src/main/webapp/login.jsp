<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Page</title>
    <link rel="stylesheet" href="logReg.css">
</head>
<body>
    <div class="login_container">
        <h2>User Login</h2>
        <form action="LoginServlet" method="post">
            <div class="form-group">
                <label for="customerId">User ID</label>
                <input type="text" name="customerId" id="customerId" required>
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" name="password" id="password" required>
            </div>
            	<button type="submit" class="btn">Login</button>
            <div class="error" id="generalError"></div>
            <p class="register-link">New User? <a href="cusReg.jsp">Register Yourself</a></p>
        </form>
        
        <!-- Display error message if present -->
	    <div style="color: red; text-align: center;">
	        <% 
	        HttpSession s = request.getSession();
            String errorMessage = (String) session.getAttribute("loginErrorMessage");
            if (errorMessage != null) {
                out.println(errorMessage);
                s.removeAttribute("loginErrorMessage"); // Clear the error message
            }
	        %>
	    </div>
	    
    </div>
</body>
</html>
