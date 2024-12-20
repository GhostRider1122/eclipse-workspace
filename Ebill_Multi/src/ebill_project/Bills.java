package ebill_project;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Bills {
	static Scanner scanner = new Scanner(System.in);
	
	// Bill Management Methods
    static void manageBills() {
    	boolean exit = false;
        while (!exit) {
	        System.out.println("1- Add Bill");
	        System.out.println("2- Update Bill Amount");
	        System.out.println("3- Delete Bill");
	        System.out.println("4- Return to main menu");
	        int choice = scanner.nextInt();
	
	        switch (choice) {
	            case 1 -> addBill();
	            case 2 -> updateBill();
	            case 3 -> deleteBill();
	            case 4 -> exit=true;
	            default -> System.out.println("Invalid choice.");
	        }
        }
    }

    static void addBill() {
    	try {
	        System.out.println("Enter Consumer ID:");
	        int consumerId = scanner.nextInt();
	        System.out.println("Enter Due Amount:");
	        double dueAmount = scanner.nextDouble();
	        System.out.println("Enter Payable Amount:");
	        double payableAmount = scanner.nextDouble();
	        
	        String sql = "INSERT INTO bills (customer_id, due_amount, payable_amount) VALUES (?, ?, ?)";
	        try (PreparedStatement pstmt = Conn.connection.prepareStatement(sql)) {
	            pstmt.setInt(1, consumerId);
	            pstmt.setDouble(2, dueAmount);
	            pstmt.setDouble(3, payableAmount);
	            pstmt.executeUpdate();
	            System.out.println("Bill added successfully.");
	        }
    	} catch (SQLException e) {
        System.out.println("Error adding bill: " + e.getMessage());
    	}
        
    }

    static void updateBill() {
        System.out.println("Enter Consumer ID:");
        int consumerId = scanner.nextInt();
        System.out.println("Enter New Due Amount:");
        double newDueAmount = scanner.nextDouble();
        
        String sql = "UPDATE bills SET due_amount = ? WHERE customer_id = ?";
        try (PreparedStatement pstmt = Conn.connection.prepareStatement(sql)) {
            pstmt.setDouble(1, newDueAmount);
            pstmt.setInt(2, consumerId);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Due amount updated successfully.");
            } 
            
            else {
                System.out.println("Customer not found.");
            }
        } catch (SQLException e) {
        System.out.println("Error updating due amount: " + e.getMessage());
        }

    }

    static void deleteBill() {
    	try {
    		System.out.println("Enter Consumer ID:");
            int customerId = scanner.nextInt();
            
            String sql = "DELETE FROM bills WHERE customer_id = ?";
            try (PreparedStatement pstmt = Conn.connection.prepareStatement(sql)) {
                pstmt.setInt(1, customerId);
                int rowsDeleted = pstmt.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Bill deleted successfully.");
                } else {
                    System.out.println("Bill not found.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error deleting bill: " + e.getMessage());
        }     
    }
}
