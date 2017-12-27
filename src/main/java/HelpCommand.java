import com.petersamokhin.bots.sdk.clients.Group;
import com.petersamokhin.bots.sdk.objects.Message;


public class HelpCommand extends AbstractCommand {
    private final String COMMAND = "/help";
    private final String HELP = "\"/def {word}\" Shows all definitions\n\"/rand {word}\" Shows one random definition\n\"/help\" ..?";

    HelpCommand() {
        super.COMMAND = this.COMMAND;
    }

    @Override
    void start(Group group) {
        group.onCommand(COMMAND, message -> {
            new Message()
                    .from(group)
                    .to(message.authorId())
                    .text(HELP)
                    .send();
        });
    }
}