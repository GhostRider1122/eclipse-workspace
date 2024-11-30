package ebill_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conn {
	
	private static final String URL = "jdbc:sqlite:E:\\Projects\\Electricity Bill Payment\\JAVA\\ebill_multi.db"; // Path to your SQLite database
	static Connection connection = null;
	
	// Connect to SQLite Database
    static void connect() throws SQLException, ClassNotFoundException {
    	Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection(URL);
        
        // Enable foreign key support
        Statement stmt = connection.createStatement();
        stmt.execute("PRAGMA foreign_keys = ON;");
        
    }

    // Disconnect from SQLite Database
    static void disconnect() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

}
