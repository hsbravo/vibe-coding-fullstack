package com.example.vibeapp.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DbCleanup {
    public static void main(String[] args) {
        String url = "jdbc:h2:file:./data/testdb";
        String user = "sa";
        String pass = "";
        try (Connection conn = DriverManager.getConnection(url, user, pass);
             Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS POSTS");
            System.out.println("Dropped table POSTS if existed.");
        } catch (Exception e) {
            System.err.println("Failed to drop POSTS: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
