package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // Register the driver (MySQL 8+)
            conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/student_db?useSSL=false&allowPublicKeyRetrieval=true",
                "root",
                "abhi123"
            );
            System.out.println("Database Connected");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }
}
