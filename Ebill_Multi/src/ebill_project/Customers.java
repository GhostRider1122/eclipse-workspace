package ebill_project;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Customers {
	static Scanner scanner = new Scanner(System.in);
	
	// Customer Management Methods
    static void manageCustomers() {
    	boolean exit = false;
        while (!exit) {
	        System.out.println("1- Add Customer");
	        System.out.println("2- Update Customer");
	        System.out.println("3- Delete Customer");
	        System.out.println("4- Display All Customers");
	        System.out.println("5- Exit");
	        int choice = scanner.nextInt();
	
	        switch (choice) {
	            case 1 -> addCustomer();
	            case 2 -> updateCustomer();
	            case 3 -> deleteCustomer();
	            case 4 -> displayAllCustomers();
	            case 5 -> exit=true;
	            default -> System.out.println("Invalid choice. Try Again!!");
	        }
        }
    }
    
    static void addCustomer() {
        System.out.println("Enter Consumer ID:");
        int consumerId = scanner.nextInt();
        System.out.println("Enter Bill Number:");
        int billNumber = scanner.nextInt();
        scanner.nextLine(); // Clear the newline
        System.out.println("Enter Title:");
        String title = scanner.nextLine();
        System.out.println("Enter Customer Name:");
        String customerName = scanner.nextLine();
        System.out.println("Enter Email:");
        String email = scanner.nextLine();
        System.out.println("Enter Mobile Number:");
        String mobileNumber = scanner.nextLine();
        System.out.println("Enter User ID:");
        String userId = scanner.nextLine();
        System.out.println("Enter Password:");
        String password = scanner.nextLine();
        System.out.println("Enter Confirm Password:");
        String confirmPassword = scanner.nextLine();
        
	     // Check if passwords match
        if (!password.equals(confirmPassword)) {
            System.out.println("Passwords do not match. Please try again.");
            return;
        }
        
        String sql = "INSERT INTO customers (customer_id, bill_number, title, customer_name, email, mobile_number, user_id, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = Conn.connection.prepareStatement(sql)) {
            pstmt.setInt(1, consumerId);
            pstmt.setInt(2, billNumber);
            pstmt.setString(3, title);
            pstmt.setString(4, customerName);
            pstmt.setString(5, email);
            pstmt.setString(6, mobileNumber);
            pstmt.setString(7, userId);
            pstmt.setString(8, password);
            pstmt.executeUpdate();
            System.out.println("Customer added successfully.");
        } catch (SQLException e) {
        System.out.println("Error adding customer: " + e.getMessage());
        }
    }

    static void updateCustomer() {
        System.out.println("Enter Consumer ID:");
        int customerId = scanner.nextInt();
        scanner.nextLine(); // Clear the newline
        System.out.println("Enter New Email:");
        String newEmail = scanner.nextLine();

        String sql = "UPDATE customers SET email = ? WHERE customer_id = ?";
        try (PreparedStatement pstmt = Conn.connection.prepareStatement(sql)) {
            pstmt.setString(1, newEmail);
            pstmt.setInt(2, customerId);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Customer email updated successfully.");
            } 
            
            else {
                System.out.println("Customer not found.");
            }
        } catch (SQLException e) {
        System.out.println("Error updating email: " + e.getMessage());
    }
 }

    static void deleteCustomer() {
    	try {
            System.out.println("Enter Customer ID:");
            int customerId = scanner.nextInt();

            String sql = "DELETE FROM customers WHERE customer_id = ?";
            try (PreparedStatement pstmt = Conn.connection.prepareStatement(sql)) {
                pstmt.setInt(1, customerId);
                int rowsDeleted = pstmt.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Customer deleted successfully.");
                } else {
                    System.out.println("Customer not found.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error deleting customer: " + e.getMessage());
        }
    }
    
    static void displayAllCustomers() {
    	String sql = "SELECT * FROM customers";
        try (Statement stmt = Conn.connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
        	
            System.out.println("Customer Details:");
            System.out.println("\nConsumer ID  | Customer Name        | Email           | Mobile Number");
            System.out.println("---------------------------------------------------------------------------");
            while (rs.next()) {
            	System.out.printf("%-12d | %-20s | %-15s | %-14s\n", rs.getInt("customer_id"), rs.getString("customer_name"), rs.getString("email"), rs.getString("mobile_number"));
//                System.out.println("ID: " + rs.getInt("customer_id") +
//                        ", Name: " + rs.getString("customer_name") +
//                        ", Email: " + rs.getString("email") +
//                        ", Mobile: " + rs.getString("mobile_number"));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving customers: " + e.getMessage());
        }
    }
	

}
