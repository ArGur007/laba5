import java.util.Scanner;

public class ExpUpdate implements Command {
    private CollectionManager manager;
    private Scanner scanner;

    public ExpUpdate(CollectionManager manager, Scanner scanner) {
        this.manager = manager;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        // Получаем всю строку команды от пользователя
        String input = scanner.nextLine().trim();

        // Разбираем команду
        parseAndExecute(input);
    }

    private void parseAndExecute(String input) {
        // Разбиваем по пробелам: ["exp_update", "2", "name=\"Nitrate removal test (v2)\""]
        String[] parts = input.split(" ");

        // Проверяем минимальное количество частей
        if (parts.length < 3) {
            System.out.println("Ошибка: Неверный формат. Используйте: exp_update <id> field=value ...");
            return;
        }

        // Проверяем, что это действительно команда exp_update
        if (!parts[0].equals("exp_update")) {
            System.out.println("Ошибка: Неверная команда");
            return;
        }

        try {
            // Получаем ID эксперимента
            Long id = Long.parseLong(parts[1]);

            // Ищем эксперимент
            Experiment exp = manager.findById(id);
            if (exp == null) {
                System.out.println("Ошибка: Эксперимент с ID " + id + " не найден");
                return;
            }

            // Обрабатываем все пары field=value
            boolean updated = false;
            for (int i = 2; i < parts.length; i++) {
                if (updateField(exp, parts[i])) {
                    updated = true;
                }
            }

            // Если хотя бы одно поле обновилось
            if (updated) {
                System.out.println("OK");
            } else {
                System.out.println("Ничего не обновлено");
            }

        } catch (NumberFormatException e) {
            System.out.println("Ошибка: ID должен быть числом");
        }
    }

    private boolean updateField(Experiment exp, String fieldValue) {
        // Разделяем на поле и значение
        String[] keyValue = fieldValue.split("=", 2);

        if (keyValue.length != 2) {
            System.out.println("Ошибка: Неверный формат поля. Используйте field=value");
            return false;
        }

        String field = keyValue[0].toLowerCase();
        String value = keyValue[1];

        // Убираем кавычки, если есть
        if (value.startsWith("\"") && value.endsWith("\"")) {
            value = value.substring(1, value.length() - 1);
        }

        try {
            switch (field) {
                case "name":
                    exp.setName(value);  // Здесь сработает ВСЯ валидация из setName()
                    System.out.println("Поле name обновлено");
                    return true;

                case "description":
                    exp.setDescribtion(value);  // Здесь сработает ВСЯ валидация из setDecribtion()
                    System.out.println("Поле description обновлено");
                    return true;

                default:
                    System.out.println("Ошибка: Неизвестное поле '" + field + "'. Доступные поля: name, description");
                    return false;
            }
        } catch (IllegalArgumentException e) {
            // Ловим исключения из сеттеров и показываем пользователю
            System.out.println("Ошибка в поле " + field + ": " + e.getMessage());
            return false;
        }
    }
}