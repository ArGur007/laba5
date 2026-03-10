import java.util.List;

/**
 * Команда для вывода списка экспериментов.
 * Использует метод getByOwner для фильтрации, если указан флаг --mine.
 */
public class ExpList implements Command {
    private final CollectionManager manager;
    private final String currentOwner;
    private final boolean showOnlyMine;

    public ExpList(CollectionManager manager, String currentOwner, boolean showOnlyMine) {
        this.manager = manager;
        this.currentOwner = currentOwner;
        this.showOnlyMine = showOnlyMine;
    }

    @Override
    public void execute() {
        List<Experiment> list;

        if (showOnlyMine) {
            // ИСПОЛЬЗОВАНИЕ МЕТОДА ИЗ CollectionManager
            list = manager.getByOwner(currentOwner);
            System.out.println("\n--- Ваши эксперименты (Оператор: " + currentOwner + ") ---");
        } else {
            list = manager.getAll();
            System.out.println("\n--- Список всех экспериментов в системе ---");
        }

        if (list.isEmpty()) {
            System.out.println("Список пуст.");
            return;
        }

        // Вывод заголовка таблицы
        System.out.printf("%-5s | %-20s | %-15s | %-10s\n", "ID", "Название", "Создан", "Владелец");
        System.out.println("-".repeat(60));

        for (Experiment e : list) {
            System.out.printf("%-5d | %-20s | %-15s | %-10s\n",
                    e.getId(),
                    e.getName(),
                    e.getCreatedAt().toString().substring(0, 10), // Берем только дату ГГГГ-ММ-ДД
                    e.getOwnerUsername()
            );
        }
    }
}