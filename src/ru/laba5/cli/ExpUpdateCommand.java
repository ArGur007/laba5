package ru.laba5.cli;

import ru.laba5.domain.Experiment;
import ru.laba5.service.CollectionManager;

import java.util.Scanner;

public class ExpUpdateCommand implements Command {
    private final CollectionManager manager;
    private final Scanner scanner;
    private final String currentUser;

    public ExpUpdateCommand(CollectionManager manager, Scanner scanner, String currentUser) {
        this.manager = manager;
        this.scanner = scanner;
        this.currentUser = currentUser;
    }

    @Override
    public void execute() {
        System.out.print("Введите ID эксперимента для обновления: ");
        String idInput = scanner.nextLine().trim();
        long id;
        try {
            id = Long.parseLong(idInput);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: ID должен быть числом");
            return;
        }

        Experiment exp = manager.findExperimentById(id);
        if (exp == null) {
            System.out.println("Эксперимент с ID " + id + " не найден");
            return;
        }

        System.out.println("Обновление эксперимента #" + id);
        System.out.println("(Оставьте поле пустым и нажмите Enter, чтобы оставить текущее значение)");

        boolean updated = false;

        // Обновление названия
        System.out.println("Текущее название: " + exp.getName());
        System.out.print("Новое название: ");
        String newName = scanner.nextLine().trim();
        if (!newName.isEmpty()) {
            try {
                exp.setName(newName);
                updated = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }

        // Обновление описания
        String currentDesc = exp.getDescription();
        System.out.println("Текущее описание: " + (currentDesc.isEmpty() ? "(пусто)" : currentDesc));
        System.out.print("Новое описание: ");
        String newDesc = scanner.nextLine(); // не trim, чтобы можно было пустую строку
        if (!newDesc.isEmpty()) {
            try {
                exp.setDescription(newDesc);
                updated = true;
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }


        if (updated) {
            exp.setUpdatedAt(); // метод уже есть в Experiment
            System.out.println("OK");
        } else {
            System.out.println("Ничего не обновлено");
        }
    }
}
