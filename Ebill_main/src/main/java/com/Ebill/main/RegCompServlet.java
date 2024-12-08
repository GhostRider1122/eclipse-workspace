package com.Ebill.main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Ebill.database.DatabaseConnection;


@WebServlet("/RegComp")
public class RegCompServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
        if (session.getAttribute("customerName") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String c_no = (String) session.getAttribute("consumerNo");
        // Retrieve form data
        String complaintType = request.getParameter("complaintType");
        String category = request.getParameter("category");
        String contactPerson = request.getParameter("contactPerson");
        String landmark = request.getParameter("landmark");
        long consumerNo = Long.parseLong(c_no);
        String description = request.getParameter("description");
        String mobile = request.getParameter("mobile");
        String address = request.getParameter("address");
        String status = "Pending";

        // Database insertion
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            // Establish the database connection
            connection = DatabaseConnection.getConnection();
            
            int complaintId = generateUniqueComplaintId(connection);

            // SQL query to insert complaint into the database
            String sql = "INSERT INTO complaints (complaintId, complaintType, category, landMark, customerName, problem, consumerId, address, mobileNumber, status) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, complaintId);
            preparedStatement.setString(2, complaintType);
            preparedStatement.setString(3, category);
            preparedStatement.setString(4, landmark);
            preparedStatement.setString(5, contactPerson);
            preparedStatement.setString(6, description);
            preparedStatement.setLong(7, consumerNo);
            preparedStatement.setString(8, address);
            preparedStatement.setString(9, mobile);
            preparedStatement.setString(10, status);

            // Execute the query
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                // Redirect to a success page
            	session.setAttribute("complaintId", complaintId);
                response.sendRedirect("complaintSuccess.jsp");
            } else {
                // Redirect to an error page
                response.sendRedirect("complaintError.jsp");
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response.sendRedirect("complaintError.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("complaintError.jsp");
        } finally {
            // Close the resources
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
	}
	
	private int generateUniqueComplaintId(Connection connection) throws SQLException {
        Random random = new Random();
        int complaintId;
        boolean isUnique;

        do {
            // Generate a random 5-digit number
            complaintId = 10000 + random.nextInt(90000);

            // Check if this ID already exists in the database
            String checkQuery = "SELECT complaintId FROM complaints WHERE complaintId = ?";
            try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
                checkStatement.setInt(1, complaintId);
                try (ResultSet resultSet = checkStatement.executeQuery()) {
                    isUnique = !resultSet.next(); // True if no record is found
                }
            }
        } while (!isUnique);

        return complaintId;
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
