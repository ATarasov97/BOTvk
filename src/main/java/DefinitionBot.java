import java.util.ArrayList;

import com.petersamokhin.bots.sdk.clients.Group;
import com.petersamokhin.bots.sdk.objects.Message;


public class DefinitionBot {

    private final Group group;
    private UrbanDictionaryAPI udAPI = new UrbanDictionaryAPI();
    private ArrayList<AbstractCommand> commands = new ArrayList<>();

    DefinitionBot(Integer groupId, String accessToken) {
        group = new Group(groupId, accessToken);
    }

    public void start() {
        commands.add(new DefCommand(udAPI));
        commands.add(new RandCommand(udAPI));
        commands.add(new HelpCommand());
        group.onMessage(message -> {
            String sendText = null;
            String input = message.getText();
            for (AbstractCommand command : commands) {
                if (command.matches(input)) {
                    sendText = command.execute(input);
                    break;
                }
            }
            new Message()
                    .from(group)
                    .to(message.authorId())
                    .text(sendText)
                    .send();
        });
    }
}
