package ru.laba5.domain;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

public final class Run {
    private final long id;
    private final long experimentId;
    private final String name;
    private final String operatorName;
    private final Instant createdAt;

    public static final int MAX_NAME_LENGTH = 128;
    public static final int MAX_OPERATOR_LENGTH = 64;

    public Run(long id, long experimentId, String name, String operatorName) {
        validateId(id);
        validateExperimentId(experimentId);
        validateName(name);
        validateOperatorName(operatorName);

        this.id = id;
        this.experimentId = experimentId;
        this.name = name.trim();
        this.operatorName = operatorName != null ? operatorName.trim() : "SYSTEM";
        this.createdAt = Instant.now();
    }

    public Run updateName(String newName) {
        validateName(newName);
        return new Run(id, experimentId, newName, operatorName);
    }

    public List<RunResult> getResults() {
        return Collections.emptyList();
    }

    public long getId() { return id; }
    public long getExperimentId() { return experimentId; }
    public String getName() { return name; }
    public String getOperatorName() { return operatorName; }
    public Instant getCreatedAt() { return createdAt; }
    
    private static void validateId(long id) {
        if (id <= 0) throw new IllegalArgumentException("ID > 0");
    }

    private static void validateExperimentId(long expId) {
        if (expId <= 0) throw new IllegalArgumentException("experimentId > 0");
    }

    private static void validateName(String name) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("name не пустой");
        if (name.trim().length() > MAX_NAME_LENGTH)
            throw new IllegalArgumentException("name ≤ " + MAX_NAME_LENGTH);
    }

    private static void validateOperatorName(String operatorName) {
        if (operatorName != null && operatorName.trim().length() > MAX_OPERATOR_LENGTH)
            throw new IllegalArgumentException("operator ≤ " + MAX_OPERATOR_LENGTH);
    }

    @Override
    public String toString() {
        return String.format("Run %d experimentid %d name %s operator %s",
                id, experimentId, name, operatorName);
    }
}
