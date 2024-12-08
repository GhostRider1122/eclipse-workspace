package ebill_project;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
    	Conn.connect();
    	//Creates the customer table
        Tables.createCustomerTable();
        
        //Creates the bills tables
        Tables.createBillTable();
        
        //Creates the complaints table
        Tables.createComplaintTable();
    	
        System.out.println("Connected to the database successfully!");
        System.out.println("----------------------------------------------------------------------------");
    	
        boolean exit = false;
        while (!exit) {
            System.out.println("\nChoose an option:");
            System.out.println("1- Manage Customers");
            System.out.println("2- Manage Bills");
            System.out.println("3- Manage Complaints");
            System.out.println("4- Search Consumer Details by ID");
            System.out.println("5- Search Unpaid Bills by Consumer ID");
            System.out.println("6- View Complaints by Consumer ID");
            System.out.println("7- Exit");

            int choice = scanner.nextInt();
            switch (choice) {
	            case 1:
	                Customers.manageCustomers();
	                break;
	            case 2:
	                Bills.manageBills();
	                break;
	            case 3:
	                Complaints.manageComplaints();
	                break;
	            case 4:
	                searchCustomerById();
	                break;
	            case 5:
	                searchUnpaidBillsByConsumerId();
	                break;
	            case 6:
	                viewComplaintsByConsumerId();
	                break;
	            case 7:
	                exit = true;
	                Conn.disconnect();
	                System.out.println("Exiting the console .....");
	                break;
	            default:
	                System.out.println("Invalid choice. Please try again.");
        
            }
        }
    }

    
    // Search and Display Methods
    private static void searchCustomerById() {
    	try {
    		System.out.println("Enter Consumer ID to search:");
            int consumerId = scanner.nextInt();
            
            String sql = "SELECT * FROM customers WHERE customer_id = ?";
            try (PreparedStatement pstmt = Conn.connection.prepareStatement(sql)) {
                pstmt.setInt(1, consumerId);
                ResultSet rs = pstmt.executeQuery();
                
                boolean found = false;
                System.out.println("\nCustomer Details:");
                System.out.println("\nConsumer ID  | Customer Name        | Mobile Number   | Email");
                System.out.println("---------------------------------------------------------------------");
                while (rs.next()) {
                	found = true;
                	System.out.printf("%-12d | %-20s | %-15s | %-25s\n", rs.getInt("customer_id"), rs.getString("title") + " "+ rs.getString("customer_name"), rs.getString("mobile_number"), rs.getString("email"));
                
                }
                
                if(!found) System.out.println("No customer found matching the given id");
            }
    	} catch (SQLException e) {
            System.out.println("Error deleting Complaint: " + e.getMessage());
        }
        
        
    }

    private static void searchUnpaidBillsByConsumerId() {
    	try {
    		System.out.println("Enter Consumer ID to search unpaid bills:");
            int consumerId = scanner.nextInt();
            
            String sql = """
            			SELECT 
            		 		c.customer_id, 
						    c.customer_name, 
						    b.due_amount, 
						    b.payable_amount
						FROM 
						    customers c
						INNER JOIN 
						    bills b 
						ON 
						    c.customer_id = b.customer_id
						WHERE 
						    c.customer_id = ? AND b.due_amount > 0;
            		""";
            
            try (PreparedStatement pstmt = Conn.connection.prepareStatement(sql)) {
                pstmt.setInt(1, consumerId);
                ResultSet rs = pstmt.executeQuery();
                
                boolean found = false;
                System.out.println("\nDetails of customers with unpaid bills:");
                System.out.println("\nConsumer ID  | Customer Name        | Due Amount   | Payable Amount");
                System.out.println("---------------------------------------------------------------------");
                while (rs.next()) {
                	found = true;
                	System.out.printf("%-12d | %-20s | %-12.2f | %-14.2f\n", rs.getInt("customer_id"), rs.getString("customer_name"), rs.getDouble("due_amount"), rs.getDouble("payable_amount"));
                   
                }
                
                if(!found) System.out.println("No unpaid bills found matching the given id");
            }
    	}  catch (SQLException e) {
            System.out.println("Error getting customer details: " + e.getMessage());
        }
        
    }

    private static void viewComplaintsByConsumerId() {
    	try {
	        System.out.println("Enter Consumer ID to view complaints:");
	        int consumerId = scanner.nextInt();
	        
	        String sql = "SELECT * FROM complaints WHERE consumerId = ?";
	        
	        try (PreparedStatement pstmt = Conn.connection.prepareStatement(sql)) {
	            pstmt.setInt(1, consumerId);
	            ResultSet rs = pstmt.executeQuery();
	            boolean found = false;
	            
	            System.out.println("Details of complaints for the given customer:");
	            System.out.println("\nConsumer ID  | Customer Name        | Complaint Type           | Category          | Mobile Number | Problem Description");
	            System.out.println("----------------------------------------------------------------------------------------------------------------------------");
	            while (rs.next()) {
	            	found = true;
	            	System.out.printf("%-12d | %-20s | %-24s | %-17s | %-13d | %-18s\n", rs.getInt("consumerId"), rs.getString("customerName"), rs.getString("complaintType"), rs.getString("category"), rs.getInt("mobileNumber"), rs.getString("problem"));
	                    
	            }
	            
	            if(!found) System.out.println("No complaints found matching the given id");
	        }
	        
    	} catch (SQLException e) {
            System.out.println("Error getting complaint details: " + e.getMessage());
    	}

    }
    
}
