package ebill_project;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Complaints {
	
	static Scanner scanner = new Scanner(System.in);
	
	// Complaints management Methods
    static void manageComplaints() {
    	boolean exit = false;
        while (!exit) {
	        System.out.println("1- Register Complaint");
	        System.out.println("2- Delete Complaint");
	        System.out.println("3- Return to main menu");
	        int choice = scanner.nextInt();
	
	        switch (choice) {
	            case 1 -> registerComplaint();
	            case 2 -> deleteComplaint();
	            case 3 -> exit=true;
	            default -> System.out.println("Invalid choice.");
	        }
        }
    }

    static void registerComplaint() {
        scanner.nextLine(); // Clear the newline
        System.out.println("Enter Complaint ID:");
        int complaintid = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter Complaint Type:");
        String complaintType = scanner.nextLine();
        System.out.println("Enter Category:");
        String category = scanner.nextLine();
        System.out.println("Enter Landmark:");
        String landmark = scanner.nextLine();
        System.out.println("Enter Customer Name:");
        String customerName = scanner.nextLine();
        System.out.println("Enter Problem:");
        String problem = scanner.nextLine();
        System.out.println("Enter Consumer ID:");
        int consumerId = scanner.nextInt();
        scanner.nextLine(); // Clear the newline
        System.out.println("Enter Address:");
        String address = scanner.nextLine();
        System.out.println("Enter Mobile Number:");
        String mobileNumber = scanner.nextLine();

        String sql = "INSERT INTO complaints (complaintId, complaintType, category, landMark, customerName, problem, consumerId, address, mobileNumber) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = Conn.connection.prepareStatement(sql)) {
            pstmt.setInt(1, complaintid);
            pstmt.setString(2, complaintType);
            pstmt.setString(3, category);
            pstmt.setString(4, landmark);
            pstmt.setString(5, customerName);
            pstmt.setString(6, problem);
            pstmt.setInt(7, consumerId);
            pstmt.setString(8, address);
            pstmt.setString(9, mobileNumber);
            pstmt.executeUpdate();
            System.out.println("Complaint added successfully.");
        } catch (SQLException e) {
        System.out.println("Error adding complaint: " + e.getMessage());
        }
    }

    static void deleteComplaint() {
    	try {
    		System.out.println("Enter Consumer ID:");
            int consumerId = scanner.nextInt();
            
            String sql = "DELETE FROM complaints WHERE consumerId = ?";
            try (PreparedStatement pstmt = Conn.connection.prepareStatement(sql)) {
                pstmt.setInt(1, consumerId);
                int rowsDeleted = pstmt.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Complaint deleted successfully.");
                } else {
                    System.out.println("Complaint not found.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error deleting Complaint: " + e.getMessage());
        }
          
    }
}
