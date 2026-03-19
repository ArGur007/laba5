package ru.laba5.domain;


import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public final class Run {
    private final Long id;
    private final long experimentId;
    private String name;
    private String operatorName;
    private final Instant createdAt;
    private final List<RunResult> results = new ArrayList<>();

    private static final int MAX_NAME_LENGTH = 128;
    private static final int MAX_OPERATOR_LENGTH = 64;

    public Run(Long id, long experimentId, String name, String operatorName) {
        this.id = id;
        this.experimentId = experimentId;
        setName(name);
        setOperatorName(operatorName);
        this.createdAt = Instant.now();
    }

    public void addResult(RunResult result) {
        results.add(result);
    }

    public void removeResult(long resultId) {
        results.removeIf(r -> r.getId() == resultId);
    }

    public List<RunResult> getResults() {
        return new ArrayList<>(results);
    }

    public Long getId() {
        return id;
    }

    public long getExperimentId() {
        return experimentId;
    }

    public String getName() {
        return name;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название запуска не может быть пустым");
        }
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("Название запуска не должно превышать " + MAX_NAME_LENGTH + " символов");
        }
        this.name = name.trim();
    }

    public void setOperatorName(String operatorName) {
        if (operatorName == null || operatorName.trim().isEmpty()) {
            throw new IllegalArgumentException("Имя оператора не может быть пустым");
        }
        if (operatorName.length() > MAX_OPERATOR_LENGTH) {
            throw new IllegalArgumentException("Имя оператора не должно превышать " + MAX_OPERATOR_LENGTH + " символов");
        }
        this.operatorName = operatorName.trim();
    }

    @Override
    public String toString() {
        return String.format("Run #%d [%s] operator=%s", id, name, operatorName);
    }
}
