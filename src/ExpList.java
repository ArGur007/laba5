import java.util.List;

public class ExpList implements Command{
    private CollectionManager manager;
    private String currentUser;
    private boolean showOnlyMine;

    public ExpList(CollectionManager manager, String currentUser, boolean showOnlyMine){
        this.manager = manager;
        this.currentUser = currentUser;
        this.showOnlyMine = showOnlyMine;
    }

    @Override
    public void execute() {
        List<Experiment> experiments = manager.getAll();

        System.out.println("ID Name");

        // Выводим каждый эксперимент
        for (Experiment exp : experiments) {
            // Проверяем флаг --mine
            if (showOnlyMine) {
                // Только эксперименты текущего пользователя
                if (currentUser.equals(exp.getOwnerUsername())) {
                    System.out.println(exp.getId() + " " + exp.getName());
                }
            } else {
                // Все эксперименты
                System.out.println(exp.getId() + " " + exp.getName());
            }
        }

        // Если список пуст, можно вывести сообщение
        if (experiments.isEmpty()) {
            System.out.println("(нет экспериментов)");
        }

    }
}
