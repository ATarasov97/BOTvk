
public class HelpCommand extends AbstractCommand {
    private final String HELP = "\"/def {word}\" Shows all definitions\n\"/rand {word}\" Shows one random definition\n\"";

    @Override
    public String execute(String input) {
        return HELP;
    }

    @Override
    public boolean matches(String args) {
        return true;
    }
}