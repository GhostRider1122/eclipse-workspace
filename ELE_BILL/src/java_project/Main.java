package java_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
	private static final String URL = "jdbc:sqlite:E:\\Projects\\Electricity Bill Payment\\JAVA\\EBill.db"; // Path to your SQLite database
    private static Connection connection = null;
	
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
    	connect();
    	//Creates the customer table
        createCustomerTable();
        
        //Creates the bills tables
        createBillTable();
        
        //Creates the complaints table
        createComplaintTable();
    	
        System.out.println("Connected to the database successfully!");
        System.out.println("----------------------------------------------------------------------------");
    	
        boolean exit = false;
        while (!exit) {
            System.out.println("Choose an option:");
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
	                manageCustomers();
	                break;
	            case 2:
	                manageBills();
	                break;
	            case 3:
	                manageComplaints();
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
	                disconnect();
	                System.out.println("Exiting the console .....");
	                break;
	            default:
	                System.out.println("Invalid choice. Please try again.");
        
            }
        }
    }

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
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
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
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
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
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
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
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
        	
//        	if(!rs.next()) {
//        		System.out.println("No customer found!!");
//        		return;
//        	}
        	
            System.out.println("Customer Details:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("customer_id") +
                        ", Name: " + rs.getString("customer_name") +
                        ", Email: " + rs.getString("email") +
                        ", Mobile: " + rs.getString("mobile_number"));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving customers: " + e.getMessage());
        }
    }

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
	        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
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
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
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
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
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
        scanner.nextLine(); // Clear the newline        System.out.println("Enter Complaint ID:");
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
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
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
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
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

    // Search and Display Methods
    static void searchCustomerById() {
    	try {
    		System.out.println("Enter Consumer ID to search:");
            int consumerId = scanner.nextInt();
            
            String sql = "SELECT * FROM customers WHERE customer_id = ?";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setInt(1, consumerId);
                ResultSet rs = pstmt.executeQuery();
                
                boolean found = false;
                System.out.println("Customer Details:");
                while (rs.next()) {
                	found = true;
                    System.out.println("ID: " + rs.getInt("customer_id") +
                            ", Name: " + rs.getString("title") + " "+ rs.getString("customer_name") +
                            ", Email: " + rs.getString("email") +
                            ", Mobile: " + rs.getString("mobile_number"));
                }
                
                if(!found) System.out.println("No customer found matching the given id");
            }
    	} catch (SQLException e) {
            System.out.println("Error deleting Complaint: " + e.getMessage());
        }
        
        
    }

    static void searchUnpaidBillsByConsumerId() {
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
            
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
                pstmt.setInt(1, consumerId);
                ResultSet rs = pstmt.executeQuery();
                
                boolean found = false;
                System.out.println("Details of customers with unpaid bills:");
                while (rs.next()) {
                	found = true;
                    System.out.println("ID: " + rs.getInt("customer_id") +
                            ", Name: " +  rs.getString("customer_name") +
                            ", Due Amount: " + rs.getDouble("due_amount") +
                            ", Payable Amount: " + rs.getDouble("payable_amount"));
                }
                
                if(!found) System.out.println("No unpaid bills found matching the given id");
            }
    	}  catch (SQLException e) {
            System.out.println("Error getting customer details: " + e.getMessage());
        }
        
    }

    static void viewComplaintsByConsumerId() {
    	try {
	        System.out.println("Enter Consumer ID to view complaints:");
	        int consumerId = scanner.nextInt();
	        
	        String sql = "SELECT * FROM complaints WHERE consumerId = ?";
	        
	        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
	            pstmt.setInt(1, consumerId);
	            ResultSet rs = pstmt.executeQuery();
	            
	            System.out.println("Details of complaints for the given customer:");
	            while (rs.next()) {
	                System.out.println("ID: " + rs.getInt("consumerId") +
	                        ", Name: " +  rs.getString("customerName") +
	                        ", Complaint Type: " + rs.getString("complaintType") +
	                        ", Category: " + rs.getString("category") + 
	                 		", Mobile Number: " + rs.getInt("mobile_number") +
	                 		", Problem Description: " + rs.getString("mobile_number"));
	                
	            }
	        }
	        
    	} catch (SQLException e) {
            System.out.println("Error getting complaint details: " + e.getMessage());
    	}

    }
    
    
    
 // Connect to SQLite Database
    private static void connect() throws SQLException, ClassNotFoundException {
    	Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection(URL);
        
        // Enable foreign key support
        Statement stmt = connection.createStatement();
        stmt.execute("PRAGMA foreign_keys = ON;");
        
    }

    // Disconnect from SQLite Database
    private static void disconnect() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
    
    public static void createCustomerTable() throws SQLException {
    	String query="""
    			create table if not exists customers (
    			  customer_id BIGINT(13) PRIMARY KEY,
    			  bill_number INTEGER(5) NOT NULL,
    			  title VARCHAR(5) NOT NULL,
				  customer_name VARCHAR(50) NOT NULL,
				  email VARCHAR(100) NOT NULL UNIQUE,
				  mobile_number BIGINT(10) NOT NULL,
				  user_id VARCHAR(20) CHECK (LENGTH(user_id) BETWEEN 5 AND 20),
				  password VARCHAR(30) NOT NULL
    			);
    			""";
    	PreparedStatement ps=connection.prepareStatement(query);
    	ps.executeUpdate();
    }
    
    public static void createBillTable() throws SQLException {
    	String query="""
    			create table if not exists bills (
    			  customer_id BIGINT(13) PRIMARY KEY,
    			  due_amount DECIMAL NOT NULL,
    			  payable_amount DECIMAL NOT NULL,
    			  FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE
    			);
    			""";
    	PreparedStatement ps=connection.prepareStatement(query);
    	ps.executeUpdate();
    }
    
    public static void createComplaintTable() throws SQLException {
    	String query="""
    			create table if not exists complaints (
    			  complaintId INTEGER PRIMARY KEY, 
			      complaintType TEXT NOT NULL,
			      category TEXT NOT NULL,    
			      landMark TEXT,                              
			      customerName TEXT NOT NULL,                  
			      problem TEXT NOT NULL,                       
			      consumerId INTEGER NOT NULL, 
			      address TEXT NOT NULL,                       
			      mobileNumber TEXT NOT NULL,                  
			      CHECK(LENGTH(mobileNumber) = 10 AND mobileNumber GLOB '[0-9]*'),
			      FOREIGN KEY (consumerId) REFERENCES customers(customer_id) ON DELETE CASCADE
    			);
    			""";
    	PreparedStatement ps=connection.prepareStatement(query);
    	ps.executeUpdate();
    }
    
}
