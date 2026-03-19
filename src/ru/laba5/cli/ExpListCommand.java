package ru.laba5.cli;

import ru.laba5.domain.Experiment;
import ru.laba5.service.CollectionManager;

import java.util.List;

public class ExpListCommand implements Command {
    private final CollectionManager manager;
    private final String currentOwner;
    private final boolean showOnlyMine;

    public ExpListCommand(CollectionManager manager, String currentOwner, boolean showOnlyMine) {
        this.manager = manager;
        this.currentOwner = currentOwner;
        this.showOnlyMine = showOnlyMine;
    }

    @Override
    public void execute() {
        List<Experiment> list;
        if (showOnlyMine) {
            list = manager.getExperimentsByOwner(currentOwner);
            System.out.println("\n--- Ваши эксперименты (оператор: " + currentOwner + ") ---");
        } else {
            list = manager.getAllExperiments();
            System.out.println("\n--- Список всех экспериментов ---");
        }

        if (list.isEmpty()) {
            System.out.println("Список пуст.");
            return;
        }

        System.out.printf("%-5s | %-30s | %-15s | %-10s\n", "ID", "Название", "Создан", "Владелец");
        System.out.println("-".repeat(70));
        for (Experiment e : list) {
            System.out.printf("%-5d | %-30s | %-15s | %-10s\n",
                    e.getId(),
                    e.getName(),
                    e.getCreatedAt().toString().substring(0, 10),
                    e.getOwnerUsername());
        }
    }
}
