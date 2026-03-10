import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Run {
    private final long id;
    private final String name;
    private final String operator;
    private final LocalDateTime time;
    private final List<RunResult> results = new ArrayList<>();

    private static long nextResId = 88;

    public Run(long id, String name, String operator) {
        this.id = id;
        this.name = name;
        this.operator = operator;
        this.time = LocalDateTime.now(); // Время фиксируется в момент создания
    }

    public void addResult(String param, double val, String unit, String comment) {
        RunResult newResult = new RunResult(nextResId++, param, val, unit, comment);
        results.add(newResult);
    }

    // Геттеры
    public long getId() { return id; }
    public String getName() { return name; }
    public String getOperator() { return operator; }
    public List<RunResult> getResults() { return results; }

    public String getTimeFormatted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return time.format(formatter);
    }

    @Override
    public String toString() {
        return String.format("ID: %-3d | Name: %-15s | Op: %-10s | Time: %s",
                id, name, operator, getTimeFormatted());
    }
}