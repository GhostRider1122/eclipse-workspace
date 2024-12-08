<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.sql.*, com.Ebill.database.DatabaseConnection"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>

    <%  
        if (session.getAttribute("adminName") == null) {
            response.sendRedirect("login.jsp");
        }
    %>

    <div class="container">
        <div class="header">
            <h1>Ebill Admin Portal</h1>
            <div class="user-info">
                <span>Welcome, <%= session.getAttribute("adminName") %>!</span>
                <a href="LogoutServlet"><button>Logout</button></a>
            </div>
        </div>

        <nav class="navbar">
            <ul>
                <li><a href="#customersTab" onclick="showTab('customersTab')">Registered Customers</a></li>
                <li><a href="#complaintsTab" onclick="showTab('complaintsTab')">Registered Complaints</a></li>
            </ul>
        </nav>

        <div id="customersTab" class="tab-content">
            <h2>Registered Customers</h2>
            <table id="customersTable">
                <thead>
                    <tr>
                        <th>Customer ID</th>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Mobile</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        // Fetch customers from the database
                        Connection connection = com.Ebill.database.DatabaseConnection.getConnection();
                        PreparedStatement ps = connection.prepareStatement("SELECT * FROM customers");
                        ResultSet rs = ps.executeQuery();
                        while (rs.next()) {
                            long customerId = rs.getLong("customer_id");
                            String name = rs.getString("customer_name");
                            String email = rs.getString("email");
                            String mobile = rs.getString("mobile_number");
                    %>
                        <tr>
                            <td><%= customerId %></td>
                            <td><%= name %></td>
                            <td><%= email %></td>
                            <td><%= mobile %></td>
                            <td>
                                <a href="EditCustomerServlet?customerId=<%= customerId %>">Edit</a> | 
                                <a href="DeleteCustomerServlet?customerId=<%= customerId %>">Delete</a>
                            </td>
                        </tr>
                    <%
                        }
                        rs.close();
                        ps.close();
                        connection.close();
                    %>
                </tbody>
            </table>
        </div>

        <div id="complaintsTab" class="tab-content" style="display: none;">
            <h2>Registered Complaints</h2>
            <table id="complaintsTable">
                <thead>
                    <tr>
                        <th>Complaint ID</th>
                        <th>Type</th>
                        <th>Category</th>
                        <th>Description</th>
                        <th>Status</th>
                        <th>Update Status</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        // Fetch complaints from the database
                        connection = com.Ebill.database.DatabaseConnection.getConnection();
                        ps = connection.prepareStatement("SELECT * FROM complaints");
                        rs = ps.executeQuery();
                        while (rs.next()) {
                            int complaintId = rs.getInt("complaintId");
                            String type = rs.getString("complaintType");
                            String category = rs.getString("category");
                            String description = rs.getString("problem");
                            String status = rs.getString("status");
                    %>
                        <tr>
                            <td><%= complaintId %></td>
                            <td><%= type %></td>
                            <td><%= category %></td>
                            <td><%= description %></td>
                            <td><%= status %></td>
                            <td>
                                <form action="UpdateComplaintStatusServlet" method="post">
                                    <input type="hidden" name="complaintId" value="<%= complaintId %>">
                                    <select name="status" required>
                                        <option value="Active" <%= "Active".equals(status) ? "selected" : "" %>>Active</option>
                                        <option value="Pending" <%= "Pending".equals(status) ? "selected" : "" %>>Pending</option>
                                        <option value="Resolved" <%= "Resolved".equals(status) ? "selected" : "" %>>Resolved</option>
                                    </select>
                                    <button type="submit">Update</button>
                                </form>
                            </td>
                        </tr>
                    <%
                        }
                        rs.close();
                        ps.close();
                        connection.close();
                    %>
                </tbody>
            </table>
        </div>
    </div>

    <script>
        function showTab(tabId) {
            document.querySelectorAll('.tab-content').forEach(tab => tab.style.display = 'none');
            document.getElementById(tabId).style.display = 'block';
        }
    </script>

</body>
</html>
