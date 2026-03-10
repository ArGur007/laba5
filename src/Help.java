public class Help implements Command {
    @Override
    public void execute() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("   ДОСТУПНЫЕ КОМАНДЫ СИСТЕМЫ LIMS");
        System.out.println("=".repeat(40));

        System.out.println("\n[ Базовые операции ]");
        System.out.println(" help          - Показать это справочное меню");
        System.out.println(" create        - Создать новый эксперимент");
        System.out.println(" list [--mine] - Список всех экспериментов");
        System.out.println(" show          - Детальный просмотр эксперимента по ID");
        System.out.println(" update        - Редактировать данные эксперимента");
        System.out.println(" exit          - Завершить работу программы");

        System.out.println("\n[ Управление запусками (Runs) ]");
        System.out.println(" run_add       - Добавить серию измерений (запуск)");
        System.out.println(" run_list      - Показать список запусков эксперимента");
        System.out.println(" run_show      - Информация о конкретном запуске и его результатах");

        System.out.println("\n[ Результаты и Аналитика ]");
        System.out.println(" res_add       - Добавить результат (pH, NITRATE и др.) в запуск");
        System.out.println(" exp_summary   - Сводная статистика (min/max/avg) по эксперименту");

        System.out.println("=".repeat(40));
    }
}