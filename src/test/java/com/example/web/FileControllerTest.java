package com.example.web;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;

/**
 * Unit tests for FileController class
 */
public class FileControllerTest {
    
    private FileController fileController;
    private static final String TEST_DIR = "/tmp/uploads/";
    private static final String TEST_FILE = "testfile.txt";
    private static final String TEST_CONTENT = "This is test content";
    
    @Before
    public void setUp() {
        fileController = new FileController();
        // Create test directory
        new File(TEST_DIR).mkdirs();
    }
    
    @After
    public void tearDown() {
        // Clean up test files
        File testFile = new File(TEST_DIR + TEST_FILE);
        if (testFile.exists()) {
            testFile.delete();
        }
    }
    
    @Test
    public void testFileControllerCreation() {
        assertNotNull("FileController should be created", fileController);
    }
    
    @Test
    public void testReadFileNonExistent() {
        String content = fileController.readFile("nonexistent.txt");
        assertNull("Reading non-existent file should return null", content);
    }
    
    @Test
    public void testWriteFileSuccess() throws IOException {
        boolean result = fileController.writeFile(TEST_FILE, TEST_CONTENT);
        assertTrue("File write should succeed", result);
        
        File file = new File(TEST_DIR + TEST_FILE);
        assertTrue("File should exist after write", file.exists());
    }
    
    @Test
    public void testWriteAndReadFile() throws IOException {
        // Write a file
        boolean writeResult = fileController.writeFile(TEST_FILE, TEST_CONTENT);
        assertTrue("File write should succeed", writeResult);
        
        // Read the file back
        String content = fileController.readFile(TEST_FILE);
        assertNotNull("Read content should not be null", content);
        assertTrue("Content should contain test data", content.contains(TEST_CONTENT));
    }
    
    @Test
    public void testWriteFileEmptyContent() {
        boolean result = fileController.writeFile(TEST_FILE, "");
        assertTrue("Writing empty content should succeed", result);
    }
    
    @Test
    public void testWriteFileWithSpecialCharacters() {
        String specialContent = "Special chars: !@#$%^&*()";
        boolean result = fileController.writeFile(TEST_FILE, specialContent);
        assertTrue("Writing special characters should succeed", result);
        
        String content = fileController.readFile(TEST_FILE);
        assertNotNull("Content should not be null", content);
        assertTrue("Content should contain special characters", 
                   content.contains(specialContent));
    }
    
    @Test
    public void testReadFileEmptyFilename() {
        String content = fileController.readFile("");
        assertNull("Reading empty filename should return null", content);
    }
    
    @Test
    public void testExecuteCommandReturnsResult() {
        String result = fileController.executeCommand("echo test");
        assertNotNull("Command execution should return result", result);
    }
    
    @Test
    public void testExecuteCommandWithEmptyCommand() {
        String result = fileController.executeCommand("");
        // May return empty or error, but should not crash
        assertNotNull("Command execution should return something", result);
    }
    
    @Test
    public void testExecuteSystemCommandReturnsResult() {
        String result = fileController.executeSystemCommand("echo test");
        assertNotNull("System command execution should return result", result);
    }
    
    @Test
    public void testExecuteSystemCommandWithEmptyCommand() {
        String result = fileController.executeSystemCommand("");
        assertNotNull("System command execution should return something", result);
    }
    
    @Test
    public void testWriteMultipleFiles() {
        boolean result1 = fileController.writeFile("file1.txt", "Content 1");
        boolean result2 = fileController.writeFile("file2.txt", "Content 2");
        
        assertTrue("First file write should succeed", result1);
        assertTrue("Second file write should succeed", result2);
        
        // Clean up
        new File(TEST_DIR + "file1.txt").delete();
        new File(TEST_DIR + "file2.txt").delete();
    }
    
    @Test
    public void testWriteFileMultilineContent() {
        String multilineContent = "Line 1\nLine 2\nLine 3";
        boolean result = fileController.writeFile(TEST_FILE, multilineContent);
        assertTrue("Writing multiline content should succeed", result);
        
        String content = fileController.readFile(TEST_FILE);
        assertNotNull("Content should not be null", content);
        assertTrue("Content should contain multiline data", content.contains("Line 1"));
    }
}
