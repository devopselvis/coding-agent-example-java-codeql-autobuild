package com.example.app;

import com.example.database.UserDatabase;
import com.example.security.CryptoUtils;
import com.example.web.FileController;
import com.example.ldap.LdapAuth;

/**
 * Main application class demonstrating various Java vulnerabilities
 * that should be detected by CodeQL scanning.
 */
public class VulnerableApplication {
    
    public static void main(String[] args) {
        System.out.println("Starting Vulnerable Application...");
        
        // Demonstrate various vulnerable components
        UserDatabase userDb = new UserDatabase();
        CryptoUtils crypto = new CryptoUtils();
        FileController fileController = new FileController();
        LdapAuth ldapAuth = new LdapAuth();
        
        // Example usage that would trigger vulnerabilities
        String userInput = args.length > 0 ? args[0] : "admin";
        String password = args.length > 1 ? args[1] : "password123";
        
        // SQL Injection vulnerability
        userDb.authenticateUser(userInput, password);
        userDb.deleteUser(userInput);
        
        // Weak cryptography
        String token = crypto.generateToken();
        System.out.println("Generated token: " + token);
        
        // Path traversal vulnerability
        String filename = args.length > 2 ? args[2] : "../../etc/passwd";
        fileController.readFile(filename);
        
        // Command injection
        String command = args.length > 3 ? args[3] : "ls -la";
        fileController.executeCommand(command);
        fileController.executeSystemCommand(command);
        
        // LDAP injection
        ldapAuth.authenticateUser(userInput, password);
        ldapAuth.getUserInfo(userInput);
        
        System.out.println("Application completed.");
    }
}