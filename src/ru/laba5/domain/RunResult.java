package ru.laba5.domain;

import java.time.Instant;

public final class RunResult {
    private final Long id;
    private final long runId;
    private MeasurementParam param;
    private double value;
    private String unit;
    private String comment;
    private final Instant createdAt;

    private static final int MAX_UNIT_LENGTH = 16;
    private static final int MAX_COMMENT_LENGTH = 128;

    public RunResult(Long id, long runId, MeasurementParam param, double value, String unit, String comment) {
        this.id = id;
        this.runId = runId;
        setParam(param);
        setValue(value);
        setUnit(unit);
        setComment(comment);
        this.createdAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public long getRunId() {
        return runId;
    }

    public MeasurementParam getParam() {
        return param;
    }

    public double getValue() {
        return value;
    }

    public String getUnit() {
        return unit;
    }

    public String getComment() {
        return comment;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setParam(MeasurementParam param) {
        if (param == null) {
            throw new IllegalArgumentException("Параметр не может быть null");
        }
        this.param = param;
    }

    public void setValue(double value) {
        // можно добавить проверку на положительность, если нужно
        this.value = value;
    }

    public void setUnit(String unit) {
        if (unit == null || unit.trim().isEmpty()) {
            throw new IllegalArgumentException("Единицы измерения не могут быть пустыми");
        }
        if (unit.length() > MAX_UNIT_LENGTH) {
            throw new IllegalArgumentException("Единицы измерения не должны превышать " + MAX_UNIT_LENGTH + " символов");
        }
        this.unit = unit.trim();
    }

    public void setComment(String comment) {
        if (comment == null) {
            this.comment = "";
        } else if (comment.length() > MAX_COMMENT_LENGTH) {
            throw new IllegalArgumentException("Комментарий не должен превышать " + MAX_COMMENT_LENGTH + " символов");
        } else {
            this.comment = comment.trim();
        }
    }
}
