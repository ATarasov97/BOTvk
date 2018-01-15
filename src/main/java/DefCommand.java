import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DefCommand extends AbstractCommand {

    private static final Logger log = LoggerFactory.getLogger(DefinitionBot.class);

    private final String COMMAND = "/def";
    private final String HELP = "/def {word}";
    private final String NOT_FOUND = "0 definitions found";
    private final UrbanDictionaryAPI udAPI;


    public DefCommand(UrbanDictionaryAPI udAPI) {
        super.COMMAND = this.COMMAND;
        this.udAPI = udAPI;
    }

    @Override
    public String execute(String input) {
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
                for (UrbanDictionaryAPI.Definition definition : definitions) {
                    sendMessage.append(definition.word).append("\n")
                            .append("From: ").append(definition.author).append("\n")
                            .append(definition.body).append("\n\n\n");
                }
                return sendMessage.toString();
            }
        }
    }
}
