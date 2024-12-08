package com.Ebill.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    // SQLite database file
    private static final String DATABASE_URL = "jdbc:sqlite:E:\\Projects\\Electricity Bill Payment\\JAVA\\Ebill_jsp.db";

    // Method to get a connection to the SQLite database
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try {
        	Class.forName("org.sqlite.JDBC");
            // Establish the connection
            connection = DriverManager.getConnection(DATABASE_URL);
            
            Statement stmt = connection.createStatement();
            stmt.execute("PRAGMA foreign_keys = ON;");
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
            throw e;
        }
        return connection;
    }
}