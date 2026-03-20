package ru.laba5.cli;

import ru.laba5.domain.Experiment;
import ru.laba5.service.CollectionManager;

public class ExpUpdateCommand implements Command {
    private final CollectionManager manager;
    private final InputReader reader;
    private final String currentUser;

    public ExpUpdateCommand(CollectionManager manager, InputReader reader, String currentUser) {
        this.manager = manager;
        this.reader = reader;
        this.currentUser = currentUser;
    }

    @Override
    public void execute() {
        long id = reader.readLong("ID эксперимента: ");
        Experiment exp = manager.findExperimentById(id);
        if (exp == null) {
            System.out.println("Эксперимент #" + id + " не найден");
            return;
        }

        String newName = reader.readString("Новое name [" + exp.getName() + "]: ");
        String newDesc = reader.readString("Новое desc [" + exp.getDescription() + "]: ");

        Experiment updated = exp;
        if (!newName.isEmpty()) {
            updated = updated.updateName(newName);
        }
        if (!newDesc.isEmpty()) {
            updated = updated.updateDescription(newDesc);
        }

        if (updated != exp) {
            manager.updateExperiment(updated);
            System.out.println("OK");
        } else {
            System.out.println("Ничего не изменено");
        }
    }
}
