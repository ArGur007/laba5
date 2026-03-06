import java.util.Scanner;

public class ExpUpdate implements Command {
    private CollectionManager manager;
    private Scanner scanner;
    private String currentUser;  // может понадобиться для проверки прав

    public ExpUpdate(CollectionManager manager, Scanner scanner, String currentUser) {
        this.manager = manager;
        this.scanner = scanner;
        this.currentUser = currentUser;
    }

    @Override
    public void execute() {
        System.out.println("> update");

        Long id = null;
        Experiment experiment = null;

        while (id == null) {
            System.out.print("Введите ID эксперимента для обновления: ");
            String idInput = scanner.nextLine().trim();

            try {
                id = Long.parseLong(idInput);
                experiment = manager.findById(id);

                if (experiment == null) {
                    System.out.println("Ошибка: Эксперимент с ID " + id + " не найден");
                    id = null;  // сбрасываем, чтобы запросить снова
                }
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: ID должен быть числом");
            }
        }

        System.out.println("Обновление эксперимента #" + id);
        System.out.println("(Оставьте поле пустым и нажмите Enter, чтобы оставить текущее значение)");

        boolean nameUpdated = updateName(experiment);

        boolean descUpdated = updateDescription(experiment);

        // Шаг 4: Проверка, были ли изменения
        if (nameUpdated || descUpdated) {
            experiment.setUpdatedAt();  // обновляем время изменения
            System.out.println("OK");
        } else {
            System.out.println("Ничего не обновлено");
        }
    }

    // Метод для обновления названия
    private boolean updateName(Experiment experiment) {
        String currentName = experiment.getName();
        System.out.println("Текущее название: " + currentName);

        while (true) {
            System.out.print("Новое название (Enter - оставить текущее): ");
            String input = scanner.nextLine().trim();

            // Если пользователь ничего не ввел - оставляем текущее
            if (input.isEmpty()) {
                return false;
            }

            try {
                experiment.setName(input);  // здесь сработает валидация
                System.out.println("Название обновлено");
                return true;
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage());
                System.out.println("Повторите ввод");
                // продолжаем цикл
            }
        }
    }

    // Метод для обновления описания
    private boolean updateDescription(Experiment experiment) {
        String currentDesc = experiment.getDescribtion();
        if (currentDesc == null || currentDesc.isEmpty()) {
            System.out.println("Текущее описание: (пусто)");
        } else {
            System.out.println("Текущее описание: " + currentDesc);
        }

        while (true) {
            System.out.print("Новое описание (Enter - оставить текущее): ");
            String input = scanner.nextLine();  // не делаем trim(), чтобы можно было ввести пустую строку

            // Если пользователь ничего не ввел - оставляем текущее
            if (input.isEmpty()) {
                return false;
            }

            try {
                experiment.setDescribtion(input);  // здесь сработает валидация
                System.out.println("Описание обновлено");
                return true;
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage());
                System.out.println("Повторите ввод");
                // продолжаем цикл
            }
        }
    }
}