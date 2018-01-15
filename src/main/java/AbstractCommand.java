
abstract class AbstractCommand {
    String COMMAND = null;

    abstract String execute(String input);

    public String getCommand() {
        return COMMAND;
    }

    public boolean matches(String args) {
        return args.startsWith(getCommand());
    }

}
