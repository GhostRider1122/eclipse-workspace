package com.Ebill.registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Ebill.database.DatabaseConnection;

@WebServlet("/CusRegServlet")
public class CusRegServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	 	long consumerId = Long.parseLong(request.getParameter("C_id"));
        int billNum = Integer.parseInt(request.getParameter("bill_num"));
        String title = request.getParameter("title");
        String customerName = request.getParameter("customer_name");
        String emailId = request.getParameter("emailId");
        long mobileNum = Long.parseLong(request.getParameter("country_code")+request.getParameter("mobile_num"));
        String userId = request.getParameter("user_id");
        String password = request.getParameter("pass");

        ;
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DatabaseConnection.getConnection();
            
            // Check for duplicate email, customer ID, or username
            String checkQuery = "SELECT * FROM customers WHERE email = ? OR customer_id = ? OR user_id = ?";
            ps = conn.prepareStatement(checkQuery);
            ps.setString(1, emailId);
            ps.setDouble(2, consumerId);
            ps.setString(3, userId);

            rs = ps.executeQuery();

            if (rs.next()) {
                // Duplicate found, send back to registration.jsp with an error message
            	HttpSession session = request.getSession();
            	// Determine which field caused the duplication
                String errorMessage = "Duplicate entry detected: ";
                if (rs.getString("customer_id").equals(String.valueOf(consumerId))) {
                    errorMessage += "Consumer ID ";
                }
                if (rs.getString("email").equals(emailId)) {
                    errorMessage += "Email ";
                }
                if (rs.getString("user_id").equals(userId)) {
                    errorMessage += "Username ";
                }
                session.setAttribute("registrationErrorMessage", errorMessage + "already exists.");
                response.sendRedirect("cusReg.jsp");
            } else {
            
	            String query = "INSERT INTO customers (customer_id, bill_number, title, customer_name, email, mobile_number, user_id, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	            ps = conn.prepareStatement(query);
	            
	       
	            ps.setLong(1, consumerId);
	            ps.setLong(2, billNum);
	            ps.setString(3, title);
	            ps.setString(4, customerName);
	            ps.setString(5, emailId);
	            ps.setLong(6, mobileNum);
	            ps.setString(7, userId);
	            ps.setString(8, password);
	
	            int result = ps.executeUpdate();
	            if (result > 0) {
	                response.sendRedirect("login.jsp");
	            } else {
	                response.sendRedirect("regError.jsp");
	            }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("regError.jsp");
        }
         finally {
	        try {
	        	if (rs != null) rs.close();
	            if (ps != null) ps.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
        }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
