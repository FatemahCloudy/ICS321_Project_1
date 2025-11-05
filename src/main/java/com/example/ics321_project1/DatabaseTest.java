package com.example.ics321_project1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseTest {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/horse_race?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password = "EngSwe&987";

        System.out.println("🔍 Testing database connection...");

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Connected successfully!");
            } else {
                System.out.println("⚠️ Connection is null or closed!");
            }
        } catch (SQLException e) {
            System.out.println("❌ Connection failed!");
            e.printStackTrace();
        }
    }
}