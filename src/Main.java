import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CollectionManager manager = new CollectionManager();
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Система управления химическими экспериментами ===");
        System.out.print("Введите имя пользователя (Enter для SYSTEM): ");
        String owner = scanner.nextLine().trim();
        if (owner.isEmpty()) {
            owner = "SYSTEM";
        }

        // 1. Создаем invoker
        CommandInvoker invoker = new CommandInvoker();

        // 2. Регистрируем базовые команды
        invoker.register("help", new Help());
        invoker.register("create", new ExpCreate(manager, scanner, owner));
        invoker.register("show", new ExpShow(manager, scanner));
        invoker.register("update", new ExpUpdate(manager, scanner, owner));
        invoker.register("run", new RunCreate(manager, scanner));
        System.out.println("Добро пожаловать, " + owner + "!");

        // 3. Бесконечный цикл
        while (true) {
            System.out.println("\n(Введите 'help' для списка команд или 'exit' для выхода)");
            System.out.print("> ");

            String input = scanner.nextLine().trim();

            // Выход из программы
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Завершение работы... До свидания!");
                break;
            }

            // Обработка команды list (с флагом --mine)
            if (input.startsWith("list")) {
                boolean showOnlyMine = input.contains("--mine");
                ExpList listCommand = new ExpList(manager, owner, showOnlyMine);
                listCommand.execute();
            }
            // Обработка всех остальных зарегистрированных команд
            else if (!input.isEmpty()) {
                invoker.execute(input);
            }
        }

        scanner.close();
    }
}


