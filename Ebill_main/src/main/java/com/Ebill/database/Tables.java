package com.Ebill.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Tables {
	static Connection connection = null;
	private static void createCustomerTable() throws SQLException, ClassNotFoundException {
		connection = DatabaseConnection.getConnection();
    	String query="""
    			create table if not exists customers (
    			  customer_id BIGINT(13) PRIMARY KEY,
    			  bill_number INTEGER(5) NOT NULL,
    			  title VARCHAR(5),
				  customer_name VARCHAR(50),
				  email VARCHAR(100) NOT NULL UNIQUE,
				  mobile_number BIGINT(10),
				  user_id VARCHAR(20),
				  password VARCHAR(30) NOT NULL
    			);
    			""";
    	PreparedStatement ps=connection.prepareStatement(query);
    	ps.executeUpdate();
    	connection.close();
    }
    
    private static void createBillTable() throws SQLException, ClassNotFoundException {
    	connection = DatabaseConnection.getConnection();
    	String query="""
    			create table if not exists bills (
    			  customer_id BIGINT(13) PRIMARY KEY,
    			  due_amount DECIMALL,
    			  payable_amount DECIMAL,
    			  FOREIGN KEY (customer_id) REFERENCES customers(customer_id) ON DELETE CASCADE
    			);
    			""";
    	PreparedStatement ps=connection.prepareStatement(query);
    	ps.executeUpdate();
    	connection.close();
    }
    
    private static void createComplaintTable() throws SQLException, ClassNotFoundException {
    	connection = DatabaseConnection.getConnection();
    	
    	String query="""
    			create table if not exists complaints (
    			  complaintId INTEGER PRIMARY KEY, 
			      complaintType TEXT,
			      category TEXT,    
			      landMark TEXT,                              
			      customerName TEXT,                  
			      problem TEXT,                       
			      consumerId BIGINT(13), 
			      address TEXT ,                       
			      mobileNumber TEXT,                  
			      CHECK(LENGTH(mobileNumber) = 10 AND mobileNumber GLOB '[0-9]*'),
			      status TEXT,
			      FOREIGN KEY (consumerId) REFERENCES customers(customer_id) ON DELETE CASCADE
    			);
    			""";
    	PreparedStatement ps=connection.prepareStatement(query);
    	ps.executeUpdate();
    	
    	connection.close();
    }
    
    private static void createAdminTable() throws SQLException, ClassNotFoundException {
    	connection = DatabaseConnection.getConnection();
    	
    	String query="""
    			create table if not exists admin (
    			  	admin_id VARCHAR(20) PRIMARY KEY,
				    admin_name VARCHAR(50) NOT NULL,
				    password VARCHAR(50) NOT NULL
    				);
    			""";
    	PreparedStatement ps=connection.prepareStatement(query);
    	ps.executeUpdate();
    	
    	connection.close();
    }
    
    public static void main(String[] args) {
			try {
				createCustomerTable();
				createBillTable();
				createComplaintTable();
				createAdminTable();
			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			
		
	}
}


