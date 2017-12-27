import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.petersamokhin.bots.sdk.clients.Group;
import com.petersamokhin.bots.sdk.objects.Message;

public class DefCommand extends AbstractCommand {

    private static final Logger log = LoggerFactory.getLogger(DefinitionBot.class);

    private final String COMMAND = "/def";
    private final String HELP = "/def {word}";

    DefCommand() {
        super.COMMAND = this.COMMAND;
    }

    @Override
    void start(Group group) {
        group.onCommand(COMMAND, message -> {
            String input = message.getText().substring(COMMAND.length() + 1);
            if (input.length() == 0) {
                sendHelpMessage(group, message);
            } else {
                UrbanDictionaryAPI udAPI = null;
                try {
                    udAPI = new UrbanDictionaryAPI(input);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (udAPI == null) {
                    sendHelpMessage(group, message);
                } else {
                    UrbanDictionaryAPI.Definition[] definitions = udAPI.getDefinitions();
                    if (definitions.length == 0) {
                        sendHelpMessage(group, message);
                    }
                    StringBuilder sendMessage = new StringBuilder();
                    for (UrbanDictionaryAPI.Definition definition : definitions) {
                        sendMessage.append(definition.word).append("\n")
                                .append("From: ").append(definition.author).append("\n")
                                .append(definition.body).append("\n\n\n");
                    }
                    new Message()
                            .from(group)
                            .to(message.authorId())
                            .text(sendMessage)
                            .send();
                }
            }
        });
    }

    private void sendHelpMessage(Group group, Message message) {
        new Message()
                .from(group)
                .to(message.authorId())
                .text(HELP)
                .send();
    }

}
