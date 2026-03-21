package ru.laba5.cli;

import Validation.InputReader;
import ru.laba5.domain.Run;
import ru.laba5.service.CollectionManager;

public class RunCreateCommand implements Command {
    private final CollectionManager manager;
    private final InputReader reader;
    private final String currentUser;

    public RunCreateCommand(CollectionManager manager, InputReader reader, String currentUser) {
        this.manager = manager;
        this.reader = reader;
        this.currentUser = currentUser;
    }

    @Override
    public void execute() {
        long expId;
        while (true) {
            expId = reader.readLong("experiment_id: ");
            if (manager.findExperimentById(expId) != null) break;
            System.out.println("Эксперимент #" + expId + " не найден");
        }

        String name = reader.readNonEmpty("Run name: ");
        String operator = reader.readString("Operator [" + currentUser + "]: ");
        if (operator.isEmpty()) operator = currentUser;

        long runId = manager.getNextRunId();
        Run run = new Run(runId, expId, name, operator);
        manager.addRun(run);
        System.out.println("OK run_id=" + runId);
    }
}
