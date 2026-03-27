package ru.laba5.cli;

import Validation.InputReader;

import java.util.*;

public class CommandRegistry {
    private final Map<String, Command> commands = new HashMap<>();
    private final CollectionManager manager;
    private final Scanner scanner;
    private final String currentUser;

    public CommandRegistry(CollectionManager manager, Scanner scanner, String currentUser) {
        this.manager = manager;
        this.scanner = scanner;
        this.currentUser = currentUser;
        registerCommands();
    }

    private void registerCommands() {
        InputReader reader = new InputReader(scanner);

        commands.put("help", new HelpCommand());
        commands.put("exp_create", new ExpCreateCommand(manager, reader, currentUser));
        commands.put("exp_list", new ExpListCommand(manager, currentUser));
        commands.put("exp_show", new ExpShowCommand(manager, scanner));
        commands.put("exp_update", new ExpUpdateCommand(manager, reader, currentUser));
        commands.put("run_add", new RunCreateCommand(manager, reader, currentUser));
        commands.put("run_list", new RunListCommand(manager, scanner));
        commands.put("run_show", new RunShowCommand(manager, scanner));
        commands.put("res_add", new ResultAddCommand(manager, reader, currentUser));
        commands.put("res_list", new ResultListCommand(manager, scanner));
        commands.put("exp_summary", new ExpSummaryCommand(manager, scanner));
    }

    public void execute(String input) {
        if (input == null || input.trim().isEmpty()) return;

        String[] parts = input.trim().split("\\s+", 2);
        String cmdName = parts[0].toLowerCase();

        // Парсим аргументы
        List<String> args = new ArrayList<>();
        if (parts.length > 1) {
            String argsStr = parts[1];
            // Разбиваем аргументы с учётом кавычек (если нужны будут)
            // Пока просто разбиваем по пробелам
            args = Arrays.asList(argsStr.split("\\s+"));
        }

        Command cmd = commands.get(cmdName);
        if (cmd != null) {
            try {
                cmd.execute(args);
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
                if (e.getCause() != null) {
                    System.out.println("Причина: " + e.getCause().getMessage());
                }
            }
        } else {
            System.out.println("Неизвестная команда: " + cmdName);
            System.out.println("Введите 'help' для списка доступных команд.");
        }
    }
}