package ru.laba5.cli;

import ru.laba5.domain.Run;
import ru.laba5.service.CollectionManager;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class RunListCommand implements Command {
    private final CollectionManager manager;
    private final Scanner scanner;

    public RunListCommand(CollectionManager manager, Scanner scanner) {
        this.manager = manager;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Введите experiment_id: ");
        String expIdInput = scanner.nextLine().trim();
        long expId;
        try {
            expId = Long.parseLong(expIdInput);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: experiment_id должен быть числом");
            return;
        }

        List<Run> runs = manager.getRunsByExperiment(expId);
        if (runs.isEmpty()) {
            System.out.println("Запусков для данного эксперимента нет");
            return;
        }

        // Обработка флага --last N (пока не реализовано, можно добавить позже)
        System.out.println("\nID  Run name            Operator   Time");
        System.out.println("-".repeat(45));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (Run r : runs) {
            System.out.printf("%-3d %-19s %-10s %s\n",
                    r.getId(),
                    r.getName(),
                    r.getOperatorName(),
                    r.getCreatedAt().toString().replace("T", " ").substring(0, 16));
        }
    }
}
