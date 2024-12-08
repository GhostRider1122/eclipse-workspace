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

@WebServlet("/UpdateComplaintStatusServlet")
public class UpdateComplaintStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int complaintId = Integer.parseInt(request.getParameter("complaintId"));
        String status = request.getParameter("status");
        
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
        	conn = DatabaseConnection.getConnection();
            String query = "UPDATE complaints SET status = ? WHERE complaintId = ?";
            
            ps = conn.prepareStatement(query);
                ps.setString(1, status);
                ps.setInt(2, complaintId);
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
		doGet(request, response);
	}

}
