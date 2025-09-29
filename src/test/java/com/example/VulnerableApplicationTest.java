package com.example;

import com.example.app.VulnerableApplication;
import com.example.database.UserDatabase;
import com.example.security.CryptoUtils;
import com.example.web.FileController;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Basic tests to verify the application compiles and runs
 */
public class VulnerableApplicationTest {
    
    @Test
    public void testUserDatabaseCreation() {
        UserDatabase db = new UserDatabase();
        assertNotNull("UserDatabase should be created", db);
    }
    
    @Test
    public void testCryptoUtilsCreation() {
        CryptoUtils crypto = new CryptoUtils();
        assertNotNull("CryptoUtils should be created", crypto);
        
        String token = crypto.generateToken();
        assertNotNull("Token should be generated", token);
        assertTrue("Token should have length", token.length() > 0);
    }
    
    @Test
    public void testFileControllerCreation() {
        FileController controller = new FileController();
        assertNotNull("FileController should be created", controller);
    }
    
    @Test
    public void testHashPasswordReturnsValue() {
        CryptoUtils crypto = new CryptoUtils();
        String hash = crypto.hashPassword("testpassword");
        assertNotNull("Hash should not be null", hash);
        assertTrue("Hash should have content", hash.length() > 0);
    }
}