import java.util.Scanner;

public class ExpCreate implements Command {
    private CollectionManager manager;
    private Scanner scanner;
    private String ownerUsername;

    public ExpCreate(CollectionManager manager, Scanner scanner, String ownerUsername) {
        this.manager = manager;
        this.scanner = scanner;
        this.ownerUsername = ownerUsername;
    }

    @Override
    public void execute() {
        try {
            System.out.println("--- Создание нового эксперимента ---");
            System.out.print("Название: ");
            String name = scanner.nextLine().trim();

            System.out.print("Описание: ");
            String description = scanner.nextLine().trim();

            // Создаем объект. Конструктор Experiment сам проверит длину строк.
            Experiment newExp = new Experiment(
                    manager.getNextId(),
                    name,
                    description,
                    ownerUsername
            );

            // Передаем созданный объект в менеджер
            manager.create(newExp);

        } catch (IllegalArgumentException e) {
            // Сюда попадут ошибки, если название слишком длинное или пустое
            System.out.println("Ошибка валидации: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ошибка при создании: " + e.getMessage());
        }
    }
}