package ru.laba5.cli;

import ru.laba5.domain.Experiment;
import ru.laba5.domain.Run;
import ru.laba5.service.CollectionManager;

import java.util.Scanner;

public class RunCreateCommand implements Command {
    private final CollectionManager manager;
    private final Scanner scanner;

    public RunCreateCommand(CollectionManager manager, Scanner scanner) {
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

        Experiment exp = manager.findExperimentById(expId);
        if (exp == null) {
            System.out.println("Ошибка: эксперимент не найден");
            return;
        }

        System.out.print("Run name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Operator: ");
        String operator = scanner.nextLine().trim();

        try {
            long runId = manager.getNextRunId();
            Run run = new Run(runId, expId, name, operator);
            manager.addRun(run);
            System.out.println("OK run_id=" + runId);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка валидации: " + e.getMessage());
        }
    }
}
