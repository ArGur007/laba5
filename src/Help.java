public class Help implements Command{
    @Override
    public void execute(){
        System.out.println("\n Доступные команды: ");
        System.out.println(" help - справка");
        System.out.println(" exit - выход");
        System.out.println(" create - создать эксперимент");
        System.out.println(" list - список всех экспериментов");
        System.out.println(" show - показать экперимент по Id");
        System.out.println("update - обновить эксперимент");

    }
}
