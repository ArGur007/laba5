import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CollectionManager {
    private final Map<Long, Experiment> experiments = new HashMap<>();
    private long nextExpId = 1;

    // Генерация ID для новых экспериментов
    public long getNextId() {
        return nextExpId++;
    }

    // Принимает готовый объект (исправлено под твой запрос)
    public void create(Experiment exp) {
        experiments.put(exp.getId(), exp);
        System.out.println("OK. Experiment_id=" + exp.getId());
    }

    public Experiment findById(Long id) {
        return experiments.get(id);
    }

    public List<Experiment> getAll() {
        return new ArrayList<>(experiments.values());
    }

    public List<Experiment> getByOwner(String owner) {
        return experiments.values().stream()
                .filter(e -> e.getOwnerUsername().equalsIgnoreCase(owner))
                .collect(Collectors.toList());
    }

    public Run findRunById(long runId) {
        for (Experiment exp : experiments.values()) {
            for (Run run : exp.getRuns()) {
                if (run.getId() == runId) return run;
            }
        }
        return null;
    }
}