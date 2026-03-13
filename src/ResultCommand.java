import java.util.*;

public class ResultCommand implements Command {
    private CollectionManager manager;
    private Scanner scanner;
    private String type; // "add", "summary"

    public ResultCommand(CollectionManager manager, Scanner scanner, String type) {
        this.manager = manager;
        this.scanner = scanner;
        this.type = type;
    }

    @Override
    public void execute() {
        try {
            if (type.equals("add")) doAdd();
            else if (type.equals("summary")) doSummary();
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void doAdd() {
        System.out.println("--- Добавление результата измерения ---");
        System.out.print("run_id: ");
        long runId = Long.parseLong(scanner.nextLine().trim());
        Run run = manager.findRunById(runId);
        if (run == null) {
            System.out.println("Ошибка: запуск не найден.");
            return;
        }

        String param, unit, comment;
        double val = 0;

        while (true) {
            try {
                System.out.print("Параметр: ");
                param = scanner.nextLine().trim();
                if (param.isEmpty()) throw new IllegalArgumentException("Название параметра не может быть пустым");

                System.out.print("Значение: ");
                val = Double.parseDouble(scanner.nextLine().trim());

                System.out.print("Единицы: ");
                unit = scanner.nextLine().trim();
                if (unit.isEmpty()) throw new IllegalArgumentException("Единицы измерения обязательны");

                System.out.print("Комментарий: ");
                comment = scanner.nextLine().trim();

                long newId = run.addResult(param, val, unit, comment);
                System.out.println("OK result_id=" + newId);
                break;

            } catch (NumberFormatException e) {
                System.out.println("Ошибка: Значение должно быть числом (например, 7.5)");
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка валидации: " + e.getMessage());
            }
            System.out.println("Попробуйте ввести данные заново для этого запуска.\n");
        }
    }

    private void doSummary() {
        System.out.print("experiment_id: ");
        long expId = Long.parseLong(scanner.nextLine().trim());
        Experiment exp = manager.findById(expId);
        if (exp == null) {
            System.out.println("Ошибка: эксперимент не найден.");
            return;
        }

        Map<String, List<Double>> stats = new HashMap<>();
        for (Run r : exp.getRuns()) {
            for (RunResult res : r.getResults()) {
                stats.computeIfAbsent(res.getParameter(), k -> new ArrayList<>()).add(res.getValue());
            }
        }

        if (stats.isEmpty()) {
            System.out.println("Нет данных для анализа в этом эксперименте.");
            return;
        }

        System.out.println("\n--- Сводная статистика по эксперименту #" + expId + " ---");
        stats.forEach((param, values) -> {
            double min = Collections.min(values);
            double max = Collections.max(values);
            double avg = values.stream().mapToDouble(d -> d).average().orElse(0);
            System.out.printf("%s: count=%d min=%.1f max=%.1f avg=%.1f\n",
                    param, values.size(), min, max, avg);
        });
    }
}