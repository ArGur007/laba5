package ru.laba5.cli;

import ru.laba5.domain.Run;
import ru.laba5.domain.RunResult;

import java.util.List;
import java.util.Scanner;

public class RunShowCommand implements Command {
    private final CollectionManager manager;
    private final Scanner scanner;

    public RunShowCommand(CollectionManager manager, Scanner scanner) {
        this.manager = manager;
        this.scanner = scanner;
    }

    @Override
    public void execute(List<String> args) {
        System.out.print("Введите run_id: ");
        String runIdInput = scanner.nextLine().trim();
        long runId;
        try {
            runId = Long.parseLong(runIdInput);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: run_id должен быть числом");
            return;
        }

        Run run = manager.findRunById(runId);
        if (run == null) {
            System.out.println("Запуск не найден");
            return;
        }

        System.out.println("\n" + "=".repeat(60));
        System.out.println("ДЕТАЛИ ЗАПУСКА #" + run.getId() + " [" + run.getName() + "]");
        System.out.println("Оператор: " + run.getOperatorName() + " | Время: " + run.getCreatedAt());
        System.out.println("-".repeat(60));

        List<RunResult> results = run.getResults();
        if (results.isEmpty()) {
            System.out.println("Результатов нет");
        } else {
            System.out.printf("%-5s | %-12s | %-10s | %-6s | %s\n",
                    "ID", "Параметр", "Значение", "Unit", "Комментарий");
            System.out.println("-".repeat(60));
            for (RunResult res : results) {
                System.out.printf("%-5d | %-12s | %-10.2f | %-6s | %s\n",
                        res.getId(),
                        res.getParam(),
                        res.getValue(),
                        res.getUnit(),
                        res.getComment());
            }
        }
        System.out.println("=".repeat(60));
    }
}
