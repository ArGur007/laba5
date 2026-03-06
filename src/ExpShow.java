import java.util.Scanner;

public class ExpShow implements Command {
    private CollectionManager manager;
    private Scanner scanner;

    public ExpShow(CollectionManager manager, Scanner scanner) {
        this.manager = manager;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        System.out.println("> show");

        // Запрашиваем ID
        System.out.print("Введите ID эксперимента: ");
        String input = scanner.nextLine().trim();

        try {
            // Преобразуем строку в Long
            Long id = Long.parseLong(input);

            // Ищем эксперимент
            Experiment exp = manager.findById(id);

            if (exp == null) {
                System.out.println("Эксперимент с ID " + id + " не найден");
                return;
            }

            // Выводим информацию
            System.out.println("Experiment #" + exp.getId());
            System.out.println("name: " + exp.getName());
            System.out.println("description: " + exp.getDescribtion());
            System.out.println("owner: " + exp.getOwnerUsername());
            System.out.println("created: " + exp.getCreatedAt());
            System.out.println("runs: 3");  // это заглушка, пока нет runs

        } catch (NumberFormatException e) {
            System.out.println("Ошибка: ID должен быть числом");
        }
    }
}