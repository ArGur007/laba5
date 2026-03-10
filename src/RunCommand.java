import java.util.List;
import java.util.Scanner;
import java.util.Scanner;

public class RunCommand implements Command {
    private CollectionManager manager;
    private Scanner scanner;
    private String type; // "list" или "show"

    public RunCommand(CollectionManager manager, Scanner scanner, String type) {
        this.manager = manager;
        this.scanner = scanner;
        this.type = type;
    }

    @Override
    public void execute() {
        if (type.equals("list")) doList();
        else if (type.equals("show")) doShow();
    }

    private void doList() {
        System.out.print("Введите experiment_id: ");
        try {
            long expId = Long.parseLong(scanner.nextLine().trim());
            Experiment exp = manager.findById(expId);
            if (exp == null) return;

            System.out.println("\nID  Run Name            Operator   Time");
            System.out.println("-".repeat(45));
            for (Run r : exp.getRuns()) {
                System.out.printf("%-3d %-19s %-10s %s\n",
                        r.getId(), r.getName(), r.getOperator(), r.getTimeFormatted());
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private void doShow() {
        System.out.print("Введите run_id: ");
        try {
            long runId = Long.parseLong(scanner.nextLine().trim());
            Run run = manager.findRunById(runId);
            if (run == null) return;

            System.out.println("\n" + "=".repeat(60));
            System.out.println("ДЕТАЛИ ЗАПУСКА #" + run.getId() + " [" + run.getName() + "]");
            System.out.println("Оператор: " + run.getOperator() + " | Время: " + run.getTimeFormatted());
            System.out.println("-".repeat(60));

            System.out.printf("%-5s | %-12s | %-10s | %-6s | %s\n",
                    "ID", "Параметр", "Значение", "Unit", "Комментарий");
            System.out.println("-".repeat(60));

            for (RunResult res : run.getResults()) {
                System.out.printf("%-5d | %-12s | %-10.2f | %-6s | %s\n",
                        res.getId(),        // getId()
                        res.getParameter(), // getParameter()
                        res.getValue(),     // getValue()
                        res.getUnit(),      // getUnit()
                        res.getComment()    // getComment()
                );
            }
            System.out.println("=".repeat(60));
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}