import java.io.UnsupportedEncodingException;
import java.util.Random;

import com.petersamokhin.bots.sdk.clients.Group;
import com.petersamokhin.bots.sdk.objects.Message;

class RandCommand extends AbstractCommand{
    private final String COMMAND = "/rand";
    private final String HELP = "/rand {word}";
    private final String NOT_FOUND = "0 definitions found";

    RandCommand() {
        super.COMMAND = this.COMMAND;
    }

    @Override
    void start(Group group) {
        group.onCommand(COMMAND, message -> {
            String input = message.getText();
            if (input.length() == COMMAND.length()) {
                sendHelpMessage(group, message);
            } else {
                input = input.substring(COMMAND.length() + 1);
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
                        sendNotFoundMessage(group, message);
                    }
                    StringBuilder sendMessage = new StringBuilder();
                    Random random = new Random();
                    int index = random.nextInt(definitions.length);
                    System.out.println(index);
                    sendMessage.append(definitions[index].word).append("\n")
                            .append("From: ").append(definitions[index].author).append("\n")
                            .append(definitions[index].body);
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

    private void sendNotFoundMessage(Group group, Message message) {
        new Message()
                .from(group)
                .to(message.authorId())
                .text(NOT_FOUND)
                .send();
    }
}
