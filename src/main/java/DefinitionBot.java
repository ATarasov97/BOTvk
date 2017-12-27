import java.util.ArrayList;

import com.petersamokhin.bots.sdk.clients.Group;
import com.petersamokhin.bots.sdk.objects.Message;


public class DefinitionBot {

    private final String WRONG_MESSAGE = "Try /help";
    private final Group group;
    private ArrayList<AbstractCommand> commands = new ArrayList<>();

    DefinitionBot(Integer groupId, String accessToken) {
        group = new Group(groupId, accessToken);
    }

    public void start() {
        commands.add(new DefCommand());
        commands.add(new RandCommand());
        commands.add(new HelpCommand());
        for (AbstractCommand command : commands) {
            command.start(group);
        }
        startWrongInputCallback();
    }

    private void startWrongInputCallback() {
        group.onMessage(message -> {
            String messageText = message.getText();
            boolean isWrong = true;
            for (AbstractCommand command : commands) {
                if (messageText.equals(command.getCommand())) {
                    isWrong = false;
                    break;
                }
            }
            if (isWrong) {
                new Message()
                        .from(group)
                        .to(message.authorId())
                        .text(WRONG_MESSAGE)
                        .send();
            }
        });
    }

}
