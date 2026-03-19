package ru.laba5;

import ru.laba5.cli.CommandRegistry;
import ru.laba5.cli.HelpCommand;
import ru.laba5.service.CollectionManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CollectionManager manager = new CollectionManager();
        Scanner scanner = new Scanner(System.in);

        System.out.println("=".repeat(50));
        System.out.println("  ИНФОХИМИЯ: СИСТЕМА УПРАВЛЕНИЯ ЭКСПЕРИМЕНТАМИ");
        System.out.println("=".repeat(50));

        System.out.print("Введите имя оператора (Enter для SYSTEM): ");
        String owner = scanner.nextLine().trim();
        if (owner.isEmpty()) owner = "SYSTEM";

        CommandRegistry registry = new CommandRegistry(manager, scanner, owner);

        // Показываем приветствие и справку
        System.out.println("\nЗдравствуйте, " + owner + "!");
        new HelpCommand().execute();

        while (true) {
            System.out.print("\n> ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Завершение работы.");
                break;
            }
            registry.execute(input);
        }
        scanner.close();
    }
}
