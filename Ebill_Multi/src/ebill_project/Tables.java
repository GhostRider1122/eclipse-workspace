package ebill_project;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Tables {
	
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
    	PreparedStatement ps=Conn.connection.prepareStatement(query);
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
    	PreparedStatement ps=Conn.connection.prepareStatement(query);
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
    	PreparedStatement ps=Conn.connection.prepareStatement(query);
    	ps.executeUpdate();
    }
}
