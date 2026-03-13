import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Инициализация инфраструктуры
        CollectionManager manager = new CollectionManager();
        Scanner scanner = new Scanner(System.in);
        CommandInvoker invoker = new CommandInvoker();

        System.out.println("=".repeat(50));
        System.out.println("  ИНФОХИМИЯ: СИСТЕМА УПРАВЛЕНИЯ ЭКСПЕРИМЕНТАМИ");
        System.out.println("=".repeat(50));

        System.out.print("Введите имя оператора (Enter для SYSTEM): ");
        String owner = scanner.nextLine().trim();
        if (owner.isEmpty()) owner = "SYSTEM";

        // --- РЕГИСТРАЦИЯ КОМАНД ---

        // Помощь (сохраняем в переменную для автозапуска)
        Help helpCommand = new Help();
        invoker.register("help", helpCommand);

        // Эксперименты (Команды 1-4)
        invoker.register("create", new ExpCreate(manager, scanner, owner));
        invoker.register("show", new ExpShow(manager, scanner));
        invoker.register("update", new ExpUpdate(manager, scanner, owner));

        // Запуски (Команды 5-7)
        invoker.register("run_add", new RunCreate(manager, scanner));
        invoker.register("run_list", new RunCommand(manager, scanner, "list"));
        invoker.register("run_show", new RunCommand(manager, scanner, "show"));

        // Результаты и Аналитика (Команды 8-10)
        invoker.register("res_add", new ResultCommand(manager, scanner, "add"));
        invoker.register("exp_summary", new ResultCommand(manager, scanner, "summary"));

        System.out.println("\nЗдравствуйте, " + owner + "!");
        helpCommand.execute();

        while (true) {
            System.out.print("\nВведите команду > ");
            String input = scanner.nextLine().trim();

            // Обработка выхода
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Завершение работы. Все данные сохранены в памяти.");
                break;
            }

            // Специальная обработка для команды list (так как она имеет флаг)
            if (input.startsWith("list")) {
                boolean showOnlyMine = input.contains("--mine");
                // Создаем команду и передаем флаг, вызывая метод getByOwner внутри
                new ExpList(manager, owner, showOnlyMine).execute();
            }
            // Обработка всех остальных зарегистрированных команд
            else if (!input.isEmpty()) {
                invoker.execute(input);
            }
        }

        scanner.close();
    }
}