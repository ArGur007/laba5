public class RunResult {
    private final long id;
    private final String parameter;
    private final double value;
    private final String unit;
    private final String comment;

    public RunResult(long id, String parameter, double value, String unit, String comment) {
        this.id = id;
        this.parameter = parameter;
        this.value = value;
        this.unit = unit;
        this.comment = comment;
    }

    public long getId() { return id; }
    public String getParameter() { return parameter; }
    public double getValue() { return value; }
    public String getUnit() { return unit; }
    public String getComment() { return comment; }
}
