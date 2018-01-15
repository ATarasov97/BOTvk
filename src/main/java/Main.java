
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Properties props = new Properties();
        log.info("Reading application properties...");
        try {
            props.load(Main.class.getResourceAsStream("/application.properties"));
        } catch (IOException e) {
            log.error("Failed to read application properties. Terminating application...");
            System.exit(1);
        }
        DefinitionBot bot = new DefinitionBot(Integer.parseInt(props.getProperty("group_id")),
                props.getProperty("access_token"));
        bot.start();
    }

}