package ru.laba5.domain;

import java.time.Instant;

public final class RunResult {
    private final long id;
    private final long runId;
    private final MeasurementParam param;
    private final double value;
    private final String unit;
    private final String comment;
    private final String ownerUsername;
    private final Instant createdAt;

    public static final int MAX_UNIT_LENGTH = 16;
    public static final int MAX_COMMENT_LENGTH = 128;

    public RunResult(long id, long runId, MeasurementParam param, double value,
                     String unit, String comment, String ownerUsername) {

        validateId(id);
        validateRunId(runId);
        validateParam(param);
        validateValue(value);
        validateUnit(unit);
        validateComment(comment);
        validateOwner(ownerUsername);

        this.id = id;
        this.runId = runId;
        this.param = param;
        this.value = value;
        this.unit = unit != null ? unit.trim() : "unit";
        this.comment = comment != null ? comment.trim() : "";
        this.ownerUsername = ownerUsername != null ? ownerUsername.trim() : "SYSTEM";
        this.createdAt = Instant.now();
    }

    public long getId() { return id; }
    public long getRunId() { return runId; }
    public MeasurementParam getParam() { return param; }
    public double getValue() { return value; }
    public String getUnit() { return unit; }
    public String getComment() { return comment; }
    public String getOwnerUsername() { return ownerUsername; }
    public Instant getCreatedAt() { return createdAt; }


    private static void validateId(long id) {
        if (id <= 0) throw new IllegalArgumentException("ID > 0");
    }

    private static void validateRunId(long runId) {
        if (runId <= 0) throw new IllegalArgumentException("runId > 0");
    }

    private static void validateParam(MeasurementParam param) {
        if (param == null) throw new IllegalArgumentException("param обязателен");
    }

    private static void validateValue(double value) {
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            throw new IllegalArgumentException("value конечное число");
        }
    }

    private static void validateUnit(String unit) {
        if (unit != null && unit.trim().length() > MAX_UNIT_LENGTH) {
            throw new IllegalArgumentException("unit ≤ " + MAX_UNIT_LENGTH);
        }
    }

    private static void validateComment(String comment) {
        if (comment != null && comment.length() > MAX_COMMENT_LENGTH) {
            throw new IllegalArgumentException("comment ≤ " + MAX_COMMENT_LENGTH);
        }
    }

    private static void validateOwner(String owner) {
        if (owner != null && owner.trim().length() > 64) {
            throw new IllegalArgumentException("owner ≤ 64");
        }
    }

    @Override
    public String toString() {
        return String.format("RunResult %d runId %d param %s value %.2f %s %s",
                id, runId, param, value, unit, comment);
    }
}
