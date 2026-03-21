package ru.laba5.cli;

import Validation.InputReader;
import ru.laba5.domain.MeasurementParam;
import ru.laba5.domain.Run;
import ru.laba5.domain.RunResult;
import ru.laba5.service.CollectionManager;

public class ResultAddCommand implements Command {
    private final CollectionManager manager;
    private final InputReader reader;
    private final String currentUser;

    public ResultAddCommand(CollectionManager manager, InputReader reader, String currentUser) {
        this.manager = manager;
        this.reader = reader;
        this.currentUser = currentUser;
    }

    @Override
    public void execute() {
        long runId;
        while (true) {
            runId = reader.readLong("run_id: ");
            Run run = manager.findRunById(runId);
            if (run != null) break;
            System.out.println("Запуск #" + runId + " не найден");
        }

        MeasurementParam param;
        while (true) {
            String paramStr = reader.readNonEmpty("Параметр (PH, CONDUCTIVITY, NITRATE): ");
            try {
                param = MeasurementParam.valueOf(paramStr.toUpperCase());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: только PH, CONDUCTIVITY, NITRATE");
            }
        }

        double value = reader.readDouble("Значение: ");
        String unit = reader.readNonEmpty("Единицы: ");
        String comment = reader.readString("Комментарий: ");

        long resultId = manager.getNextResultId();
        RunResult result = new RunResult(resultId, runId, param, value, unit, comment, currentUser);
        manager.addResult(result);
        System.out.println("OK result_id=" + resultId);
    }
}
