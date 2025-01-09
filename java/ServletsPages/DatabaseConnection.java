//package ServletsPages;
//
//import java.sql.*;
//
//public class DatabaseConnection {
//
//    private static Connection conn = null;
//
//    public static Connection getConnection() throws SQLException, ClassNotFoundException {
//        if (conn == null) {
//            // Database credentials
//            String jdbcURL = "jdbc:mysql://localhost:3306/todoliast";
//            String dbUser = "root";
//            String dbPassword = ""; 
//
//            // Load the MySQL JDBC driver
//            Class.forName("com.mysql.cj.jdbc.Driver");
//
//            // Establish a connection to the database
//            conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
//        }
//        return conn;
//    }
//
//    public static void closeConnection() throws SQLException {
//        if (conn != null) {
//            conn.close();
//        }
//    }
//}


package ServletsPages;

import java.sql.*;

//public class DatabaseConnection {
//
//    private static Connection conn = null;
//
//    public static Connection getConnection() throws SQLException, ClassNotFoundException {
//        if (conn == null) {
//            // Database credentials
//            String jdbcURL = "jdbc:mysql://localhost:3306/todoliast";
//            String dbUser = "root";
//            String dbPassword = ""; 
//
//            // Load the MySQL JDBC driver
//            Class.forName("com.mysql.cj.jdbc.Driver");
//
//            // Establish a connection to the database
//            conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
//            
//            // Debug: Print connection info
//            System.out.println("Connected to database: " + conn.getMetaData().getURL());
//            System.out.println("Database user: " + conn.getMetaData().getUserName());
//        }
//        return conn;
//    }
//
//    public static void closeConnection() throws SQLException {
//        if (conn != null) {
//            conn.close();
//            System.out.println("Database connection closed.");
//        }
//    }
//}


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/todoliast";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        // Load MySQL JDBC Driver
        Class.forName("com.mysql.cj.jdbc.Driver");
        // Return a new connection every time
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}

