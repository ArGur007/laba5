package ru.laba5;

import ru.laba5.cli.CommandRegistry;
import ru.laba5.cli.HelpCommand;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CollectionManager manager = new CollectionManager();
        Scanner scanner = new Scanner(System.in);


        System.out.println("=".repeat(60));
        System.out.println("  ИНФОХИМИЯ: СИСТЕМА УПРАВЛЕНИЯ ЭКСПЕРИМЕНТАМИ");
        System.out.println("=".repeat(60));

        System.out.print("Введите имя оператора (Enter для SYSTEM): ");
        String owner = scanner.nextLine().trim();
        if (owner.isEmpty()) {
            owner = "SYSTEM";
        }

        CommandRegistry registry = new CommandRegistry(manager, scanner, owner);

        System.out.println("\nЗдравствуйте, " + owner + "!");
        new HelpCommand().execute(new ArrayList<>());

        while (true) {
            System.out.print("\n> ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Завершение работы. Все данные сохранены в памяти.");
                break;
            }

            registry.execute(input);
        }

        scanner.close();
    }
}