import java.util.HashMap;
import java.util.Map;

// Invoker класс
public class CommandInvoker {
    private Map<String, Command> commands = new HashMap<>();

    public void register(String commandName, Command command) {
        commands.put(commandName, command);
    }

    public void execute(String commandName) {
        Command cmd = commands.get(commandName);
        if (cmd != null) {
            cmd.execute();
        } else {
            System.out.println("Неизвестная команда: " + commandName);
        }
    }
}