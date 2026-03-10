import java.util.Scanner;

public class RunCreate implements Command {
    private CollectionManager manager;
    private Scanner scanner;

    public RunCreate(CollectionManager manager, Scanner scanner) {
        this.manager = manager;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        try {
            System.out.print("Введите ID эксперимента, для которого создается запуск: ");
            Long expId = Long.parseLong(scanner.nextLine().trim());
            Experiment exp = manager.findById(expId);

            if (exp == null) {
                System.out.println("Ошибка: Эксперимент не найден.");
                return;
            }

            System.out.print("Введите название запуска (Run name): ");
            String runName = scanner.nextLine().trim();

            System.out.print("Введите имя оператора (Operator): ");
            String operator = scanner.nextLine().trim();

            Run newRun = new Run(exp.getNextRunId(), runName, operator);

            System.out.println("\n--- Добавление результатов (введите 'done' для завершения) ---");
            while (true) {
                System.out.print("Параметр (напр. NITRATE) или 'done': ");
                String param = scanner.nextLine().trim();
                if (param.equalsIgnoreCase("done")) break;

                System.out.print("Значение (число): ");
                double val = Double.parseDouble(scanner.nextLine().trim());

                System.out.print("Единицы измерения (Unit): ");
                String unit = scanner.nextLine().trim();
                if (unit.isEmpty()) {
                    System.out.println("Ошибка: Единицы измерения не могут быть пустыми.");
                    continue;
                }

                System.out.print("Комментарий (Comment): ");
                String comment = scanner.nextLine().trim();

                newRun.addResult(param, val, unit, comment);
                System.out.println("Результат добавлен.");
            }

            exp.addRun(newRun);
            System.out.println("\nOK! Запуск успешно добавлен к эксперименту #" + expId);
            System.out.println("Присвоен run_id=" + newRun.getId());

        } catch (NumberFormatException e) {
            System.out.println("Ошибка: Ожидалось числовое значение.");
        } catch (Exception e) {
            System.out.println("Произошла ошибка: " + e.getMessage());
        }
    }
}