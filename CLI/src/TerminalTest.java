import org.junit.jupiter.api.*;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class TerminalTest {

    private Terminal terminal;
    private String testDir;

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
    /*public void testCdValidDirectory() {
        terminal.cd("src"); // Replace with an actual directory path in your project
        assertTrue(terminal.getCurrentDirectory().endsWith("src"));
    }

    @Test
    public void testCdInvalidDirectory() {
        cli.cd("nonexistentDir");
        assertTrue(outContent.toString().trim().contains("Error: Invalid directory"));
    }
*/
    @Test
    public void testLs() throws IOException {
        terminal.touch("file1.txt");
        terminal.touch("file2.txt");
        // Capture output and verify it contains expected files
        // You may need to implement a way to capture stdout or refactor ls to return a
        // List<String>
    }

   /*@Test
    public void testCat() throws IOException {
        String fileName = "testFile.txt";
        terminal.touch(fileName);
        // Write content to the file
        terminal.WriteFile(testDir + "\\", fileName, false);

        // Now test reading the content
        terminal.cat(fileName);
        // Verify output (you may need to implement a way to capture stdout)

    }*/
   @Test
    public void testCat() throws IOException {
       // Redirect System.out to capture output
       String fileName = "testFile.txt";
       File testFile = new File(testDir, fileName);
       String testContent = "Hello, world!";

       // Write content to the file
       try (FileWriter writer = new FileWriter(testFile)) {
           writer.write(testContent);
       }
       // Redirect System.out to capture output
       ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
       System.setOut(new PrintStream(outputStream));

       terminal.cat(fileName);

       // Verify that the output matches the file content
       assertEquals(testContent + System.lineSeparator(), outputStream.toString());
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
        // Test for clear function (this may require a different approach as it clears
        // the console)
        // You might want to check if the method executes without exceptions
        assertDoesNotThrow(() -> terminal.clear(), "Clear should execute without exceptions.");
    }

    @Test
    public void testHelp() {
        // Test for help function (you may want to check if it prints the expected help
        // message)
        assertDoesNotThrow(() -> terminal.help(), "Help should execute without exceptions.");
    }
}
