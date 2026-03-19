package ru.laba5.cli;

import ru.laba5.domain.Experiment;
import ru.laba5.service.CollectionManager;

import java.util.Scanner;

public class ExpCreateCommand implements Command {
    private final CollectionManager manager;
    private final Scanner scanner;
    private final String ownerUsername;

    public ExpCreateCommand(CollectionManager manager, Scanner scanner, String ownerUsername) {
        this.manager = manager;
        this.scanner = scanner;
        this.ownerUsername = ownerUsername;
    }

    @Override
    public void execute() {
        System.out.println("--- Создание нового эксперимента ---");
        while (true) {
            try {
                System.out.print("Название: ");
                String name = scanner.nextLine().trim();

                System.out.print("Описание: ");
                String description = scanner.nextLine().trim();

                long id = manager.getNextExperimentId();
                Experiment newExp = new Experiment(id, name, description, ownerUsername);
                manager.addExperiment(newExp);
                System.out.println("OK experiment_id=" + id);
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка валидации: " + e.getMessage());
                System.out.println("Пожалуйста, попробуйте снова.\n");
            }
        }
    }
}
