import java.util.ArrayList;
import java.util.List;

public class Run {
    private final Long id;
    private final List<RunResult> results = new ArrayList<>();
    private String status = "COMPLETED";

    public Run(Long id) {
        this.id = id;
    }

    public void addResult(String param, double val) {
        results.add(new RunResult(param, val));
    }

    public Long getId() { return id; }
    public List<RunResult> getResults() { return results; }

    @Override
    public String toString() {
        return "Запуск #" + id + " | Результатов: " + results.size();
    }
}