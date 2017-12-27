import com.petersamokhin.bots.sdk.clients.Group;

abstract class AbstractCommand {
    String COMMAND = null;

    abstract void start(Group group);

    String getCommand() {
        return COMMAND;
    }
}
