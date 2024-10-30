import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class main {

    static Terminal T = new Terminal();

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
        Scanner input = new Scanner(System.in);

        do {
            String directory = System.getProperty("user.dir") + "\\";

            System.out.print(directory);
            System.out.print(":-$ ");
            String operation = input.nextLine();
            System.out.println();

            // Split on the pipe symbol to handle each command in sequence
            String command = operation.trim();
            String[] allCommands = command.split("\\s+");
            if (command.contains("|")) {
                String[] commands = command.split("\\|");
                T.pipe(commands);
                continue;
            }

            Parser parse = new Parser(allCommands);

            String cmd = parse.getCmd();
            String[] arguments = parse.getArguments();

            if (cmd.equals("pwd")) {
                T.pwd();
            } else if (cmd.equals("cd")) {
                directory = T.cd(parse.getArguments()[1], directory);
            } else if (cmd.equals("ls")) {

                List<String> files = T.ls(arguments);

                for (String file : files) {
                    System.out.println(file);
                }

            } else if (cmd.equals("cat")) {
                List<String> text;

                if (parse.getArguments()[1].equals(">") || parse.getArguments()[1].equals(">>")) {
                    check_redirect(parse.getArguments()[1], directory, parse.getArguments()[2]);
                } else {
                    text = T.cat(parse.getArguments()[1]);

                    for (String line : text) {
                        System.out.println(line);
                    }

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

        } while (true);
    }
}
