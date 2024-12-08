package com.Ebill.admin;

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

import com.Ebill.database.DatabaseConnection;

@WebServlet("/EditCustomerServlet")
public class EditCustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		long customerId = Long.parseLong(request.getParameter("customerId"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String mobile = request.getParameter("mobile");
        
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
        	conn = DatabaseConnection.getConnection();
            String query = "UPDATE customers SET customer_name = ?, email = ?, mobile_number = ? WHERE customer_id = ?";
            ps = conn.prepareStatement(query); 
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, mobile);
                ps.setLong(4, customerId);
                ps.executeUpdate();
                response.sendRedirect("adminDashboard.jsp");
        
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
