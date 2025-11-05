package com.example.ics321_project1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection connection;

    // Initialize connection once
    public static void initialize() throws SQLException {
        if (connection == null || connection.isClosed()) {
            String url = "jdbc:mysql://localhost/horse_race";
            String user = "root";
            String password = "EngSwe&987";
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Database connected successfully.");
        }
    }

    // Get existing connection
    public static Connection getConnection() {
        return connection;
    }

    // Close connection on exit
    public static void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("🔒 Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
