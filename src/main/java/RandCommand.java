import java.io.IOException;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


class RandCommand extends AbstractCommand {

    private static final Logger log = LoggerFactory.getLogger(DefinitionBot.class);

    private final String COMMAND = "/rand";
    private final String HELP = "/rand {word}";
    private final String NOT_FOUND = "0 definitions found";
    private final UrbanDictionaryAPI udAPI;

    RandCommand(UrbanDictionaryAPI udAPI) {
        super.COMMAND = this.COMMAND;
        this.udAPI = udAPI;
    }

    @Override
    public String execute (String input) {
        if (input.length() == COMMAND.length()) {
            return HELP;
        } else {
            input = input.substring(COMMAND.length() + 1);
            UrbanDictionaryAPI.Definition[] definitions = null;
            try {
                definitions = udAPI.call(input);
            } catch (IOException e) {
                log.error(e + " occurred in API call");
            }
            if ((definitions == null) || (definitions.length == 0)) {
                return NOT_FOUND;
            } else {
                StringBuilder sendMessage = new StringBuilder();
                Random random = new Random();
                int index = random.nextInt(definitions.length);
                sendMessage.append(definitions[index].word).append("\n")
                        .append("From: ").append(definitions[index].author).append("\n")
                        .append(definitions[index].body);
                return sendMessage.toString();
            }
        }
    }

}
