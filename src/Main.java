import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;



// Главный класс
public class Main {
    public static void main(String[] args) {
        CollectionManager manager = new CollectionManager();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите имя пользователя (Enter для SYSTEM): ");
        String owner = scanner.nextLine().trim();
        if (owner.isEmpty()) {
            owner = "SYSTEM";
        }

        // Создаем invoker
        CommandInvoker invoker = new CommandInvoker();

        // Регистрируем команды
        invoker.register("create", new ExpCreate(manager, scanner, owner));

        // Можно добавить другие команды
        invoker.register("show", new ExpShow(manager, scanner));
        // invoker.register("exit", new ExitCommand());

        // Бесконечный цикл
        while (true) {
            System.out.println("\nДоступные команды: create, list, show, update");
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.equals("exit")) {
                System.out.println("Программа завершена");
                break;
            }
            if (input.startsWith("list")) {
                boolean showOnlyMine = input.contains("--mine");
                ExpList listCommand = new ExpList(manager, owner, showOnlyMine);
                listCommand.execute();
            }
            // Обработка остальных команд через Invoker
            else {
                invoker.execute(input);
            }
        }


        scanner.close();
    }
}

// добавть остальные команды
// добавить остальные коллекции
// залить на гит


