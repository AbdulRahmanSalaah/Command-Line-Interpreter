import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Terminal {

    // this is the scanner object that will be used to read the user input from the
    // terminal
    Scanner input = new Scanner(System.in);

    // this is the destination directory that the user will be working in by default
    public static String destination = "//D";

    // command cd to change the directory that the user is in to the directory that
    // the user wants to go to
    public String cd(String location, String currentLocation) {

// File object pointing to the target directory
File newDirectory = new File(currentLocation + "\\" + location);

if (newDirectory.exists() && newDirectory.isDirectory()) {
    // Change the current directory to the new directory
    currentLocation = newDirectory.getAbsolutePath();
    System.setProperty("user.dir", currentLocation); // Update the working directory
} else {
    System.out.println("Directory not found.");
}

return currentLocation;
    }

    // command rm to remove file from the directory
    public void rm(String fileName) {

        File file = new File(fileName);

        if (file.delete()) {
            System.out.println("File deleted successfully");
        } else {
            System.out.println("Failed to delete the file");
        }

    }

    // command mv to move file from source to destination
    public void mv(String source, String destn) throws IOException {
        // Construct source and destination file paths based on the current working directory
        String currentPath = System.getProperty("user.dir");
        File sourceFile = new File(currentPath + "\\" + source);
        File destinationFile = new File(currentPath + "\\" + destn);
    
        // Check if the source file exists
        if (sourceFile.exists()) {
            // Attempt to rename (move) the file
            if (sourceFile.renameTo(destinationFile)) {
                System.out.println("File moved successfully");
            } else {
                System.out.println("Failed to move the file");
            }
        } else {
            System.out.println("Source file does not exist.");
        }
    }
    

    // command rmdir to remove a empty directory from the directory
    public void rmdir(String fileName) {

        // this is the current path that the user is in
        String currentPath = System.getProperty("user.dir");

        // this is the file path that the user wants to remove
        File filePath = new File(currentPath + "\\" + fileName);

        // this is used to check if the directory that the user wants to remove is exist
        if (filePath.exists()) {
            filePath.delete();
        } else {
            System.out.println("directory is not exist");
        }
    }

    // command ls to list all the files in the directory
    public void ls() {
        File directory = new File(System.getProperty("user.dir"));
        String files[] = directory.list();
        for (String file : files) {
            System.out.println(file + "\n");
        }
    }

    // command mkdir to create a new directory in the current directory
    public void mkdir(String fileName) throws IOException {
        // Create the directory
        Path path = Paths.get(System.getProperty("user.dir") + "\\" + fileName);

        try {
            // Use Files.createDirectories to create the directory
            Files.createDirectories(path);
            System.out.println("Directory created successfully.");
        } catch (IOException e) {
            // If there's an exception, print an error message
            System.out.println("Failed to create directory: " + e.getMessage());
            throw e; // Optionally re-throw the exception if needed
        }

    }

    // command touch to create a new file in the current directory
    public void touch(String fileName) throws IOException {
        // Create the file
        Path path = Paths.get(System.getProperty("user.dir") + "\\" + fileName);

        try {
            // Use Files.createFile to create the file
            Files.createFile(path);
            System.out.println("File created successfully.");
        } catch (IOException e) {
            // If there's an exception, print an error message
            System.out.println("Failed to create file: " + e.getMessage());
            throw e; // Optionally re-throw the exception if needed
        }

    }

    // command cat to display the content of the file
    public void cat(String fileName) throws FileNotFoundException {
        // Determine the file we going to use
        String Current_path = System.getProperty("user.dir");
        File file_path = new File(Current_path + "\\" + fileName);
        if (file_path.exists()) {
            Scanner scan_file = new Scanner(file_path);
            while (scan_file.hasNext()) {
                System.out.println(scan_file.nextLine());
            }
        } else {
            System.out.println("file doesn't exist");
        }

    }

    public void clear() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void help() {
        System.out.println("================================");
        System.out.println("cd" + "   " + "(1)"
                + " - Change the current directory, It requires either the full path or the name of the directory");
        System.out.println("ls" + "   " + "(0)" + " - shows all files in the current directory");
        System.out.println("cat" + "   " + "(1)" + " - shows all context of a file");
        System.out.println("mkdir" + "   " + "(1)" + " - Created a directory");
        System.out.println("rmdir" + "   " + "(1)" + " - Remove a directory if it empty");
        System.out.println("touch" + "   " + "(1)" + " - Created a file");
        System.out.println("mv" + "   " + "(3)" + " - cut a file into the directory mentioned");
        System.out.println("rm" + "   " + "(1)" + " - Remove a file");
        System.out.println("help" + "   " + "(0)" + " - Print commands, their arguments and a brief description");
        System.out.println("clear" + "   " + "(0)" + " - clear the terminal page");
        System.out.println("================================");
    }

    void WriteFile(String location, String fileName, boolean append) {

        // this is the scanner object that will be used to read the user input from the
        // terminal
        FileWriter fout;
        try {

            // this is used to check if the user wants to write to the file or append to it
            fout = new FileWriter(location + fileName , append);

            // this is the input that the user will write to the file
            String in;

            // this is used to write the input to the file until the user stops
            in = input.nextLine();

            fout.write(in + "\n");
            fout.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
