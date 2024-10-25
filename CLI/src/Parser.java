public class Parser {

    String[] args;
    String cmd;

    public Parser(String[] command) {
        cmd = command[0];
        args = command;
    }

    public String[] getArguments() {
        return args;
    }

    public String getFirstArguments() {
        if (args.length < 2) {
            return "";
        } else {
            return args[1];
        }
    }

    public String getSecondArguments() {
        if (args.length < 3) {
            return "";
        } else {
            return args[2];
        }
    }

    public String getCmd() {
        return cmd;

    }

}
