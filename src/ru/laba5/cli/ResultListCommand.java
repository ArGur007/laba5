package ru.laba5.cli;

import ru.laba5.domain.Run;
import ru.laba5.domain.RunResult;
import ru.laba5.service.CollectionManager;
import java.util.List;
import java.util.Scanner;

public class ResultListCommand implements Command {
    private final CollectionManager manager;
    private final Scanner scanner;

    public ResultListCommand(CollectionManager manager, Scanner scanner) {
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

        // ✅ ПРОВЕРЯЕМ существование run
        Run run = manager.findRunById(runId);
        if (run == null) {
            System.out.println("Запуск #" + runId + " не найден");
            return;
        }

        // ✅ ✅ ✅ ИСПРАВЛЕНО: используем manager.getResultsByRun()! ✅ ✅ ✅
        List<RunResult> results = manager.getResultsByRun(runId);

        if (results.isEmpty()) {
            System.out.println("Результатов нет");
            return;
        }

        System.out.printf("%-5s | %-12s | %-10s | %-6s | %s\n",
                "ID", "Параметр", "Значение", "Unit", "Комментарий");
        System.out.println("-".repeat(60));
        for (RunResult res : results) {
            System.out.printf("%-5d | %-12s | %-10.2f | %-6s | %s\n",
                    res.getId(),
                    res.getParam().name(),  // ✅ .name() для enum!
                    res.getValue(),
                    res.getUnit(),
                    res.getComment());
        }
    }
}
