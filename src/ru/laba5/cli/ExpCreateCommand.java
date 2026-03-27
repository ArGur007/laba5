package ru.laba5.cli;

import Validation.InputReader;
import ru.laba5.domain.Experiment;

import java.util.List;

public class ExpCreateCommand implements Command {
    private final CollectionManager manager;
    private final InputReader reader;
    private final String currentUser;

    public ExpCreateCommand(CollectionManager manager, InputReader reader, String currentUser) {
        this.manager = manager;
        this.reader = reader;
        this.currentUser = currentUser;
    }

    @Override
    public void execute(List<String> args) {
        String name = reader.readNonEmpty("Название: ");
        String description = reader.readString("Описание: ");

        long id = manager.getNextExperimentId();
        Experiment exp = new Experiment(id, name, description, currentUser);
        manager.addExperiment(exp);
        System.out.println("OK exp_id=" + id);
    }
}
