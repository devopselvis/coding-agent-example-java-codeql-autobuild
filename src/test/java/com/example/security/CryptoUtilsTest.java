package com.example.security;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

/**
 * Unit tests for CryptoUtils class
 */
public class CryptoUtilsTest {
    
    private CryptoUtils cryptoUtils;
    
    @Before
    public void setUp() {
        cryptoUtils = new CryptoUtils();
    }
    
    @Test
    public void testGenerateTokenNotNull() {
        String token = cryptoUtils.generateToken();
        assertNotNull("Token should not be null", token);
    }
    
    @Test
    public void testGenerateTokenHasCorrectLength() {
        String token = cryptoUtils.generateToken();
        assertEquals("Token should have 32 characters", 32, token.length());
    }
    
    @Test
    public void testGenerateTokenContainsOnlyValidCharacters() {
        String token = cryptoUtils.generateToken();
        assertTrue("Token should contain only alphanumeric characters", 
                   token.matches("[a-z0-9]+"));
    }
    
    @Test
    public void testGenerateTokenGeneratesDifferentTokens() {
        String token1 = cryptoUtils.generateToken();
        String token2 = cryptoUtils.generateToken();
        assertNotEquals("Two consecutive tokens should be different", token1, token2);
    }
    
    @Test
    public void testGenerateSessionIdNotNull() {
        String sessionId = cryptoUtils.generateSessionId();
        assertNotNull("Session ID should not be null", sessionId);
    }
    
    @Test
    public void testGenerateSessionIdNotEmpty() {
        String sessionId = cryptoUtils.generateSessionId();
        assertTrue("Session ID should not be empty", sessionId.length() > 0);
    }
    
    @Test
    public void testGenerateSessionIdIsNumeric() {
        String sessionId = cryptoUtils.generateSessionId();
        assertTrue("Session ID should be numeric", sessionId.matches("\\d+"));
    }
    
    @Test
    public void testGenerateSessionIdGeneratesDifferentIds() {
        String sessionId1 = cryptoUtils.generateSessionId();
        String sessionId2 = cryptoUtils.generateSessionId();
        assertNotEquals("Two consecutive session IDs should be different", 
                        sessionId1, sessionId2);
    }
    
    @Test
    public void testHashPasswordNotNull() {
        String hash = cryptoUtils.hashPassword("password123");
        assertNotNull("Hash should not be null", hash);
    }
    
    @Test
    public void testHashPasswordNotEmpty() {
        String hash = cryptoUtils.hashPassword("password123");
        assertTrue("Hash should not be empty", hash.length() > 0);
    }
    
    @Test
    public void testHashPasswordConsistency() {
        String password = "testPassword";
        String hash1 = cryptoUtils.hashPassword(password);
        String hash2 = cryptoUtils.hashPassword(password);
        assertEquals("Same password should produce same hash", hash1, hash2);
    }
    
    @Test
    public void testHashPasswordDifferentForDifferentPasswords() {
        String hash1 = cryptoUtils.hashPassword("password1");
        String hash2 = cryptoUtils.hashPassword("password2");
        assertNotEquals("Different passwords should produce different hashes", 
                        hash1, hash2);
    }
    
    @Test
    public void testHashPasswordEmptyString() {
        String hash = cryptoUtils.hashPassword("");
        assertNotNull("Hash of empty string should not be null", hash);
        assertTrue("Hash of empty string should not be empty", hash.length() > 0);
    }
    
    @Test
    public void testHashPasswordSpecialCharacters() {
        String hash = cryptoUtils.hashPassword("p@ssw0rd!#$");
        assertNotNull("Hash with special characters should not be null", hash);
        assertTrue("Hash with special characters should not be empty", hash.length() > 0);
    }
    
    @Test
    public void testGetEncryptionKeyNotNull() {
        String key = cryptoUtils.getEncryptionKey();
        assertNotNull("Encryption key should not be null", key);
    }
    
    @Test
    public void testGetEncryptionKeyNotEmpty() {
        String key = cryptoUtils.getEncryptionKey();
        assertTrue("Encryption key should not be empty", key.length() > 0);
    }
    
    @Test
    public void testGetEncryptionKeyConsistent() {
        String key1 = cryptoUtils.getEncryptionKey();
        String key2 = cryptoUtils.getEncryptionKey();
        assertEquals("Encryption key should be consistent", key1, key2);
    }
}
