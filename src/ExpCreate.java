import java.util.Scanner;

public class ExpCreate implements Command {
    private CollectionManager manager;
    private Scanner scanner;
    private String ownerUsername;

    public ExpCreate(CollectionManager manager, Scanner scanner, String ownerUsername){
        this.manager = manager;
        this.scanner = scanner;
        this.ownerUsername = ownerUsername;
    }

    @Override
    public void execute() {
        System.out.println("--- Добавление нового эксперимента ---");

        while (true) {
            try {
                // Всегда запрашиваем и название, И описание вместе
                System.out.print("Название: ");
                String nameInput = scanner.nextLine().trim();

                System.out.print("Описание (можно пусто): ");
                String descInput = scanner.nextLine();

                // Создаем эксперимент - здесь сработают все проверки в сеттерах!
                Experiment experiment = new Experiment(null, nameInput, descInput, ownerUsername);

                // Если дошли сюда - ошибок нет, добавляем
                manager.create(experiment);
                break; // успешно создали - выходим из цикла

            } catch (IllegalArgumentException e) {
                // Ловим исключения из сеттеров (пустое имя, слишком длинное имя или описание)
                System.out.println("Ошибка: " + e.getMessage());
                System.out.println("Повторите ввод заново.");
                // Цикл продолжается - снова запросим и название, и описание
            }
        }

    }

}
