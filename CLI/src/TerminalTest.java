import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
public class TerminalTest {
    static Terminal T = new Terminal();
    private Terminal terminal;
    private String testDir;
    //private Path testDir;
    @BeforeEach
    public void setUp() {
        terminal = new Terminal();
        testDir = System.getProperty("user.dir") + "\\testDir";
        new File(testDir).mkdir(); // Create a test directory
        System.setProperty("user.dir", testDir); // Set current directory to testDir
    }

    @AfterEach
    public void tearDown() {
        File dir = new File(testDir);
        for (File file : dir.listFiles()) {
            file.delete(); // Clean up files
        }
        dir.delete(); // Clean up directory
    }

    @Test
    public void testMkdir() throws IOException {
        String dirName = "newDir";
        terminal.mkdir(dirName);
        assertTrue(new File(testDir, dirName).exists(), "Directory should be created.");
    }

    @Test
    public void testTouch() throws IOException {
        String fileName = "newFile.txt";
        terminal.touch(fileName);
        assertTrue(new File(testDir, fileName).exists(), "File should be created.");
    }

    @Test
    public void testRm() throws IOException {
        String fileName = "fileToRemove.txt";
        terminal.touch(fileName);
        terminal.rm(fileName);
        assertFalse(new File(testDir, fileName).exists(), "File should be removed.");
    }

    @Test
    public void testCd() {
        String newDir = "subDir";
        new File(testDir, newDir).mkdir();
        String currentLocation = System.getProperty("user.dir");
        currentLocation = terminal.cd(newDir, currentLocation);
        assertEquals(new File(testDir, newDir).getAbsolutePath(), currentLocation, "Should change directory.");
    }


    @Test
    void testLs() throws IOException {
        terminal.mkdir("dir1");
        terminal.mkdir("dir2");
        terminal.touch("file1.txt");
        terminal.touch("file2.txt");
        String[] arguments = new String[0];
        List<String> files = terminal.ls(arguments);
        assertEquals(4, files.size(), "There should be 4 items in the directory");

    }


    @Test
    void testCat() throws IOException {
        String fileName = "testFile.txt";
        File testFile = new File(testDir, fileName);
        String testContent = "Hello, world!";
        try (FileWriter writer = new FileWriter(testFile)) {
            writer.write(testContent);
        }
        List<String> outputLines = terminal.cat("testFile.txt");
        List<String> expectedLines = Arrays.asList(testContent);
        assertEquals(expectedLines, outputLines, "Content should match the file content");
    }


    @Test
    public void testMv() throws IOException {
        String sourceFile = "sourceFile.txt";
        String destFile = "destinationFile.txt";
        terminal.touch(sourceFile);
        terminal.mv(sourceFile, destFile);
        assertFalse(new File(testDir, sourceFile).exists(), "Source file should be moved.");
        assertTrue(new File(testDir, destFile).exists(), "Destination file should exist.");
    }

    @Test
    public void testRmdir() {
        String dirName = "emptyDir";
        new File(testDir, dirName).mkdir();
        terminal.rmdir(dirName);
        assertFalse(new File(testDir, dirName).exists(), "Directory should be removed.");
    }

    @Test
    public void testClear() {

        assertDoesNotThrow(() -> terminal.clear(), "Clear should execute without exceptions.");
    }

    @Test
    public void testHelp() {

        assertDoesNotThrow(() -> terminal.help(), "Help should execute without exceptions.");
    }



}
