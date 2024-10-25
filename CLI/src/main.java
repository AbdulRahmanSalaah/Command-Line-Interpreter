import java.io.IOException;
import java.util.Scanner;

public class main {

    // Create a new Terminal object
    static Terminal T = new Terminal();

    // check_redirect function to check if the user wants to redirect the output to
    // a file
    static void check_redirect(String arg, String dir, String fileName) {
        // if the user wants to overwrite the file
        if (arg.equals(">")) {
            T.WriteFile(dir, fileName, false);
        }
        // if the user wants to append to the file
        else if (arg.equals(">>")) {
            T.WriteFile(dir, fileName, true);
        }
    }

    public static void main(String[] args) throws IOException {
        try (Scanner input = new Scanner(System.in)) {

            do {
                String directory = System.getProperty("user.dir") + "\\";

                System.out.print(directory);
                System.out.print(":-$ ");
                String operation = input.nextLine();
                System.out.println();

                // Split on the pipe symbol to handle each command in sequence
                String[] commands = operation.split("\\|");

                // Loop through each command separated by "|"
                for (String command : commands) {
                    String[] allCommands = command.trim().split("\\s+");
                    Parser parse = new Parser(allCommands);

                    String cmd = parse.getCmd();

                    if (cmd.equals("cd")) {
                        directory = T.cd(parse.getArguments()[1], directory);
                    } else if (cmd.equals("ls")) {
                        T.ls();
                    } else if (cmd.equals("cat")) {
                        if (parse.getArguments()[1].equals(">") || parse.getArguments()[1].equals(">>")) {
                            check_redirect(parse.getArguments()[1], directory, parse.getArguments()[2]);
                        } else {
                            T.cat(parse.getArguments()[1]);
                        }
                    } else if (cmd.equals("rm")) {
                        if (parse.getFirstArguments().equals("")) {
                            System.out.println("One argument needed");
                        } else {
                            T.rm(parse.getFirstArguments());
                        }
                    } else if (cmd.equals("mv")) {
                        if (parse.getSecondArguments().equals("")) {
                            System.out.println("Two arguments needed");
                        } else {
                            T.mv(parse.getFirstArguments(), parse.getSecondArguments());
                        }
                    } else if (cmd.equals("help")) {
                        if (parse.getFirstArguments().equals("")) {
                            T.help();
                        } else {
                            System.out.println("No arguments needed");
                        }
                    } else if (cmd.equals("rmdir")) {
                        if (parse.getSecondArguments().equals("")) {
                            T.rmdir(parse.getFirstArguments());
                        } else {
                            System.out.println("One argument needed");
                        }
                    } else if (cmd.equals("mkdir")) {
                        if (parse.getSecondArguments().equals("")) {
                            T.mkdir(parse.getFirstArguments());
                        } else {
                            System.out.println("One argument needed");
                        }
                    } else if (cmd.equals("touch")) {
                        if (parse.getSecondArguments().equals("")) {
                            T.touch(parse.getFirstArguments());
                        } else {
                            System.out.println("One argument needed");
                        }
                    } else if (cmd.equals("clear")) {
                        if (parse.getFirstArguments().equals("")) {
                            T.clear();
                        } else {
                            System.out.println("No arguments needed");
                        }
                    } else if (cmd.equals("exit")) {
                        if (parse.getFirstArguments().equals("")) {
                            System.exit(0);
                        } else {
                            System.out.println("No arguments needed");
                        }
                    } else {
                        System.out.println("Command not found");
                    }
                }

            } while (true);
        }
    }
}