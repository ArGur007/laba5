package ru.laba5.cli;

import ru.laba5.domain.MeasurementParam;
import ru.laba5.domain.Run;
import ru.laba5.domain.RunResult;
import ru.laba5.service.CollectionManager;

import java.util.Scanner;

public class ResultAddCommand implements Command {
    private final CollectionManager manager;
    private final Scanner scanner;

    public ResultAddCommand(CollectionManager manager, Scanner scanner) {
        this.manager = manager;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.print("Введите run_id: ");
        String runIdInput = scanner.nextLine().trim();
        long runId;
        try {
            runId = Long.parseLong(runIdInput);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: run_id должен быть числом");
            return;
        }

        Run run = manager.findRunById(runId);
        if (run == null) {
            System.out.println("Ошибка: запуск не найден");
            return;
        }

        while (true) {
            try {
                System.out.print("Параметр (PH, CONDUCTIVITY, NITRATE): ");
                String paramStr = scanner.nextLine().trim().toUpperCase();
                MeasurementParam param = MeasurementParam.fromString(paramStr);
                if (param == null) {
                    System.out.println("Ошибка: неизвестный параметр. Допустимые: PH, CONDUCTIVITY, NITRATE");
                    continue;
                }

                System.out.print("Значение: ");
                double value = Double.parseDouble(scanner.nextLine().trim());

                System.out.print("Единицы: ");
                String unit = scanner.nextLine().trim();

                System.out.print("Комментарий: ");
                String comment = scanner.nextLine().trim();

                long resultId = manager.getNextResultId();
                RunResult result = new RunResult(resultId, runId, param, value, unit, comment);
                manager.addResult(result);
                System.out.println("OK result_id=" + resultId);
                break;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: значение должно быть числом");
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка валидации: " + e.getMessage());
            }
        }
    }
}
