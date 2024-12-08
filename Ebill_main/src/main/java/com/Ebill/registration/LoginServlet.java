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

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String customerId = request.getParameter("customerId");
        String password = request.getParameter("password");

        // Server-side validation (Check user existence in database)
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        
        
        try {
            conn = DatabaseConnection.getConnection();
            
            // Query to check if the user is a customer
            ps = conn.prepareStatement("SELECT * FROM customers WHERE customer_id = ? AND password = ?");
            ps.setString(1, customerId);
            ps.setString(2, password);
            rs = ps.executeQuery();

            if (rs.next()) {
                // Successful customer login
            	HttpSession session = request.getSession();
            	session.setAttribute("role", "customer");
                session.setAttribute("customerName", rs.getString("customer_name"));
                session.setAttribute("consumerNo", rs.getString("customer_id"));
                response.sendRedirect("home.jsp");
                
            } else {
            	// Close previous resources to reuse connection
                if (rs != null) rs.close();
                if (ps != null) ps.close();

                // Check if the user is an admin
                String adminQuery = "SELECT * FROM admin WHERE admin_id = ? AND password = ?";
                ps = conn.prepareStatement(adminQuery);
                ps.setString(1, customerId);
                ps.setString(2, password);
                rs = ps.executeQuery();

                if (rs.next()) {
                    // Successful admin login
                    HttpSession session = request.getSession();
                    session.setAttribute("role", "admin");
                    session.setAttribute("adminName", rs.getString("admin_name"));
                    session.setAttribute("adminId", rs.getString("admin_id"));
                    response.sendRedirect("adminDashboard.jsp");
                } else {
                    // Failed login
                    HttpSession session = request.getSession();
                    session.setAttribute("loginErrorMessage", "Invalid username or password.");
                    response.sendRedirect("login.jsp");
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=Database error");
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
