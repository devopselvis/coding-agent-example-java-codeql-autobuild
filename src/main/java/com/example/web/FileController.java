package com.example.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileWriter;

/**
 * File controller with intentional path traversal vulnerabilities
 * to demonstrate CodeQL detection capabilities.
 */
public class FileController {
    
    private static final String BASE_DIR = "/tmp/uploads/";
    
    /**
     * VULNERABLE: Path traversal vulnerability - no input validation
     * This should trigger a high/critical CodeQL alert
     */
    public String readFile(String filename) {
        try {
            // VULNERABILITY: Direct concatenation allows path traversal attacks
            File file = new File(BASE_DIR + filename);
            
            System.out.println("Reading file: " + file.getAbsolutePath());
            
            if (!file.exists()) {
                System.out.println("File does not exist: " + filename);
                return null;
            }
            
            StringBuilder content = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file)))) {
                
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
            }
            
            return content.toString();
            
        } catch (Exception e) {
            System.err.println("Error reading file: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * VULNERABLE: Another path traversal vulnerability in file writing
     */
    public boolean writeFile(String filename, String content) {
        try {
            // VULNERABILITY: No validation on filename parameter
            File file = new File(BASE_DIR + filename);
            
            // Create parent directories if they don't exist
            file.getParentFile().mkdirs();
            
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(content);
            }
            
            System.out.println("File written: " + file.getAbsolutePath());
            return true;
            
        } catch (Exception e) {
            System.err.println("Error writing file: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * VULNERABLE: Command injection vulnerability
     */
    public String executeCommand(String userCommand) {
        try {
            // VULNERABILITY: Direct execution of user input
            Process process = Runtime.getRuntime().exec("sh -c " + userCommand);
            
            StringBuilder output = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()))) {
                
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append("\n");
                }
            }
            
            return output.toString();
            
        } catch (Exception e) {
            System.err.println("Command execution failed: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * VULNERABLE: Another command injection pattern using ProcessBuilder
     */
    public String executeSystemCommand(String cmd) {
        try {
            // VULNERABILITY: ProcessBuilder with unsanitized input
            ProcessBuilder pb = new ProcessBuilder("/bin/sh", "-c", cmd);
            Process process = pb.start();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
            
            return result.toString();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}