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
        System.out.println("--- Создание нового эксперимента ---");

        // Цикл будет крутиться до тех пор, пока данные не пройдут валидацию
        while (true) {
            try {
                System.out.print("Название: ");
                String name = scanner.nextLine().trim();

                System.out.print("Описание: ");
                String description = scanner.nextLine().trim();
                Experiment newExp = new Experiment(
                        manager.getNextId(),
                        name,
                        description,
                        ownerUsername
                );

                manager.create(newExp);
                break;

            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка валидации: " + e.getMessage());
                System.out.println("Пожалуйста, попробуйте ввести данные еще раз.\n");
            } catch (Exception e) {
                System.out.println("Критическая ошибка: " + e.getMessage());
                break;
            }
        }
    }
}