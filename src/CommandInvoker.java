import java.util.HashMap;
import java.util.Map;


public class CommandInvoker {
    // Хранилище команд: имя -> объект команды
    private final Map<String, Command> commands = new HashMap<>();
    public void register(String commandName, Command command) {
        // Сохраняем имя в нижнем регистре для удобства поиска
        commands.put(commandName.toLowerCase(), command);
    }
    public void execute(String input) {
        if (input == null || input.trim().isEmpty()) {
            return;
        }
        String commandName = input.toLowerCase().trim();

        Command cmd = commands.get(commandName);

        if (cmd != null) {
            cmd.execute();
        } else {
            System.out.println("Ошибка: Команда '" + commandName + "' не найдена. Введите 'help' для справки.");
        }
    }
}