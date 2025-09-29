package com.example.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Database class with intentional SQL injection vulnerabilities
 * to demonstrate CodeQL detection capabilities.
 */
public class UserDatabase {
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";
    
    /**
     * VULNERABLE: SQL Injection vulnerability - user input directly concatenated
     * This should trigger a high/critical CodeQL alert
     */
    public boolean authenticateUser(String username, String password) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stmt = conn.createStatement();
            
            // VULNERABILITY: Direct string concatenation leads to SQL injection
            String query = "SELECT * FROM users WHERE username = '" + username + 
                          "' AND password = '" + password + "'";
            
            System.out.println("Executing query: " + query);
            ResultSet rs = stmt.executeQuery(query);
            
            boolean authenticated = rs.next();
            
            rs.close();
            stmt.close();
            conn.close();
            
            return authenticated;
            
        } catch (Exception e) {
            System.err.println("Database error: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * VULNERABLE: Another SQL injection point
     */
    public void updateUserProfile(String userId, String email, String fullName) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stmt = conn.createStatement();
            
            // VULNERABILITY: String concatenation in UPDATE statement
            String updateQuery = "UPDATE users SET email = '" + email + 
                               "', full_name = '" + fullName + 
                               "' WHERE user_id = " + userId;
            
            stmt.executeUpdate(updateQuery);
            
            stmt.close();
            conn.close();
            
        } catch (Exception e) {
            System.err.println("Update failed: " + e.getMessage());
        }
    }
    
    /**
     * VULNERABLE: Dynamic query construction - another SQL injection pattern
     */
    public void deleteUser(String userIdParam) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement stmt = conn.createStatement();
            
            // VULNERABILITY: Direct concatenation in DELETE statement
            String sql = "DELETE FROM users WHERE id = " + userIdParam;
            stmt.executeUpdate(sql);
            
            stmt.close();
            conn.close();
            
        } catch (Exception e) {
            System.err.println("Delete failed: " + e.getMessage());
        }
    }
}