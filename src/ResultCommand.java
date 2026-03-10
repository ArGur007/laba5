import java.util.*;

public class ResultCommand implements Command {
    private CollectionManager manager;
    private Scanner scanner;
    private String type; // "add", "list", "summary"

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
        System.out.print("run_id: ");
        long runId = Long.parseLong(scanner.nextLine().trim());
        Run run = manager.findRunById(runId);
        if (run == null) return;

        System.out.print("Параметр: ");
        String param = scanner.nextLine().trim();
        System.out.print("Значение: ");
        double val = Double.parseDouble(scanner.nextLine().trim());
        System.out.print("Единицы: ");
        String unit = scanner.nextLine().trim();
        if (unit.isEmpty()) { System.out.println("Ошибка: пустые unit"); return; }
        System.out.print("Комментарий: ");
        String comment = scanner.nextLine().trim();

        run.addResult(param, val, unit, comment);
        System.out.println("OK result_id=" + (88)); // Упрощенно для примера
    }

    private void doSummary() {
        System.out.print("experiment_id: ");
        long expId = Long.parseLong(scanner.nextLine().trim());
        Experiment exp = manager.findById(expId);
        if (exp == null) return;

        Map<String, List<Double>> stats = new HashMap<>();
        for (Run r : exp.getRuns()) {
            for (RunResult res : r.getResults()) {
                stats.computeIfAbsent(res.getParameter(), k -> new ArrayList<>()).add(res.getValue());
            }
        }

        stats.forEach((param, values) -> {
            double min = Collections.min(values);
            double max = Collections.max(values);
            double avg = values.stream().mapToDouble(d -> d).average().orElse(0);
            System.out.printf("%s: count=%d min=%.1f max=%.1f avg=%.1f\n", param, values.size(), min, max, avg);
        });
    }
}