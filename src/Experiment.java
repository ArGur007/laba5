import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public final class Experiment {
    private Long id;
    private String name;
    private String describtion;
    private String ownerUsername;
    private Instant createdAt;
    private Instant updatedAt;
    private List<Run> runs = new ArrayList<>(); // Список всех запусков этого эксперимента
    private long nextRunId = 1; // Внутренний счетчик ID для запусков

    private static final int MAX_NAME_LENGTH = 128;
    private static final int MAX_DESCRIBTION_LENGTH = 512;

    public Experiment(Long id, String name, String describtion, String ownerUsername){
        this.id = id;
        setName(name);
        setDescribtion(describtion);
        setOwnerUsername(ownerUsername);
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public void addRun(Run run) {
        this.runs.add(run);
        setUpdatedAt();
    }

    public List<Run> getRuns() {
        return new ArrayList<>(runs); // Возвращаем копию для безопасности (инкапсуляция)
    }

    public long getNextRunId() {
        return nextRunId++;
    }
    public Long getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getDescribtion(){
        return describtion;
    }
    public String getOwnerUsername() {
        return ownerUsername;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Название пусто. Введите название");
        }
        if(name.length() > MAX_NAME_LENGTH){
            throw new IllegalArgumentException("Название не должно превышать " + MAX_NAME_LENGTH + " символов.");
        }
        this.name = name.trim();
        setUpdatedAt();
    }

    public void setDescribtion(String describtion) {
        if (describtion == null || describtion.trim().isEmpty()){
            this.describtion = "";
        } else if (describtion.length() > MAX_DESCRIBTION_LENGTH){
            throw new IllegalArgumentException("Слишком много символов, макс. 512");
        } else {
            this.describtion = describtion.trim();
        }
        setUpdatedAt();
    }

    public void setOwnerUsername(String ownerUsername) {
        if (ownerUsername == null || ownerUsername.trim().isEmpty()){
            this.ownerUsername = "SYSTEM";
        } else {
            this.ownerUsername = ownerUsername.trim();
        }
        setUpdatedAt();
    }

    public void setUpdatedAt(){
        this.updatedAt = Instant.now();
    }

    @Override
    public String toString() {
        return "Эксперимент #" + id + " { " +
                "Название: '" + name + "', " +
                "Владелец: '" + ownerUsername + "', " +
                "Запусков: " + runs.size() +
                " }";
    }
}