package ru.laba5.cli;

import Validation.InputReader;
import ru.laba5.service.CollectionManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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
        String args = parts.length > 1 ? parts[1] : "";

        if (cmdName.equals("exp_list")) {
            boolean showOnlyMine = args.contains("--mine");
            new ExpListCommand(manager, currentUser, showOnlyMine).execute();
            return;
        }

        Command cmd = commands.get(cmdName);
        if (cmd != null) {
            try {
                cmd.execute();
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        } else {
            System.out.println("Неизвестная команда. Введите 'help' для справки.");
        }
    }
}
