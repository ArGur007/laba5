package ru.laba5.cli;

import ru.laba5.domain.Experiment;

import java.util.Scanner;

public class ExpShowCommand implements Command {
    private final CollectionManager manager;
    private final Scanner scanner;

    public ExpShowCommand(CollectionManager manager, Scanner scanner) {
        this.manager = manager;
        this.scanner = scanner;
    }

    @Override
    public void execute(List<String> args) {
        System.out.print("Введите ID эксперимента: ");
        String input = scanner.nextLine().trim();
        try {
            long id = Long.parseLong(input);
            Experiment exp = manager.findExperimentById(id);
            if (exp == null) {
                System.out.println("Эксперимент с ID " + id + " не найден");
                return;
            }
            System.out.println("Experiment #" + exp.getId());
            System.out.println("name: " + exp.getName());
            System.out.println("description: " + exp.getDescription());
            System.out.println("owner: " + exp.getOwnerUsername());
            System.out.println("created: " + exp.getCreatedAt());
            System.out.println("runs: " + exp.getRuns().size());
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: ID должен быть числом");
        }
    }
}
