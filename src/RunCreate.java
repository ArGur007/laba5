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
        System.out.print("Введите ID эксперимента, для которого создается запуск: ");
        try {
            Long expId = Long.parseLong(scanner.nextLine().trim());
            Experiment exp = manager.findById(expId);

            if (exp == null) {
                System.out.println("Ошибка: Эксперимент не найден.");
                return;
            }

            // Создаем запуск с уникальным ID внутри этого эксперимента
            Run newRun = new Run(exp.getNextRunId());

            // Добавляем результаты (мини-цикл)
            System.out.println("Добавление результатов (введите 'done' для завершения)");
            while (true) {
                System.out.print("Параметр (или 'done'): ");
                String param = scanner.nextLine().trim();
                if (param.equalsIgnoreCase("done")) break;

                System.out.print("Значение (число): ");
                double val = Double.parseDouble(scanner.nextLine().trim());

                newRun.addResult(param, val);
            }

            exp.addRun(newRun);
            System.out.println("Запуск успешно добавлен к эксперименту #" + expId);

        } catch (NumberFormatException e) {
            System.out.println("Ошибка: вводите числа в ID и значениях.");
        }
    }
}
