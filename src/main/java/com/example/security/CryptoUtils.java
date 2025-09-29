package com.example.security;

import java.util.Random;
import java.security.MessageDigest;

/**
 * Security utilities with intentional cryptographic vulnerabilities
 * to demonstrate CodeQL detection capabilities.
 */
public class CryptoUtils {
    
    // VULNERABLE: Using weak random number generator
    private static final Random random = new Random();
    
    /**
     * VULNERABLE: Uses weak random number generation for security tokens
     * This should trigger a CodeQL alert for insecure randomness
     */
    public String generateToken() {
        StringBuilder token = new StringBuilder();
        
        // VULNERABILITY: Using java.util.Random for security-sensitive operations
        for (int i = 0; i < 32; i++) {
            int randomChar = random.nextInt(36);
            if (randomChar < 10) {
                token.append((char) ('0' + randomChar));
            } else {
                token.append((char) ('a' + randomChar - 10));
            }
        }
        
        return token.toString();
    }
    
    /**
     * VULNERABLE: Uses weak random for session IDs
     */
    public String generateSessionId() {
        // VULNERABILITY: Predictable session ID generation
        long sessionId = System.currentTimeMillis() + random.nextInt(1000);
        return Long.toString(sessionId);
    }
    
    /**
     * VULNERABLE: Weak hash function usage
     */
    public String hashPassword(String password) {
        try {
            // VULNERABILITY: Using MD5 for password hashing (weak algorithm)
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(password.getBytes());
            
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            
            return hexString.toString();
            
        } catch (Exception e) {
            System.err.println("Hashing failed: " + e.getMessage());
            return password; // VULNERABILITY: Fallback to plaintext
        }
    }
    
    /**
     * VULNERABLE: Hard-coded encryption key
     */
    public String getEncryptionKey() {
        // VULNERABILITY: Hard-coded secret key
        return "MySecretKey123456789";
    }
}