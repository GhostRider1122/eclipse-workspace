<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Customer Registration</title>
    <link rel="stylesheet" href="logReg.css">
</head>
<body>

   <div class="container">
        
        <form action="CusRegServlet" id="registrationForm">
            <h2>Register New User</h2>

            <div class="section">
                <h3>Consumer Details</h3>
                <hr>
                <label> Consumer ID: <input name="C_id" type="number" id="consumerId" min="1000000000000" max="9999999999999" pattern="\d{13}" required placeholder="Consumer Number"></label>    
                <label> Bill Number: <input name="bill_num" type="number" id="billNumber" min="10000" max="99999" pattern="\d{5}" required placeholder="last 5 digits"></label>
            </div>
            
            <div class="section">
                <h3>User Details</h3>
                <hr>
                <label>Title:
                    <select id="title" name="title" required>
                        <option value="" disabled selected>Please Select</option>
                        <option value="Mr">Mr</option>
                        <option value="Ms">Ms</option>
                        <option value="Mrs">Mrs</option>
                        <option value="Others">Others</option>
                    </select>
                </label>
                
                <label> Customer Name: <input name="customer_name" type="text" id="name" maxlength="50" required placeholder="Enter your name"></label>
                <label for="emailId"> Email: <input name="emailId" type="email" id="email" required placeholder="Email address"></label>

                <label> Mobile Number:
                    <select id="countryCode" name="country_code">
                        <option value="91">+91 India</option>
                        <option value="1">+1 USA</option>
                    </select>
                    <input name="mobile_num" type="text" id="mobile" minlength="10" maxlength="10" required placeholder="Enter your mobile number (10 digits)">
                </label>
            </div>
            
            <div class="section">
                <h3>Login Details</h3>
                <hr>
                <label> User ID: <input name="user_id" type="text" id="userId" minlength="5" maxlength="20" required placeholder="Create your user name"></label>
                <label> Password: <input name="pass" type="password" id="password" minlength="8" maxlength="30" required placeholder="Create password"></label>
                <label> Confirm Password: <input type="password" id="confirmPassword" minlength="8" maxlength="30" required placeholder="Retype your password"></label>
            </div>
            
            <div class="buttons">
                <button class="reset" type="reset">Reset</button>
                <button class="submit" type="submit">Register</button>
            </div>

            <div>
                <p class="login-link">Already have an account? <a href="login.jsp">Log in </a></p>
            </div>
            
        </form>
        
        <div style="color: red; text-align: center;">
            <!-- Display Error Message -->
            <%
            HttpSession s = request.getSession();
            String errorMessage = (String) session.getAttribute("registrationErrorMessage");
                if (errorMessage != null) {
            
                out.println(errorMessage);
                s.removeAttribute("registrationErrorMessage");
                }
            %>
        </div>
        
    </div>
    
</body>
</html>
