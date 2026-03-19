package ru.laba5.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public final class Experiment {
    private final Long id;
    private String name;
    private String description;
    private String ownerUsername;
    private final Instant createdAt;
    private Instant updatedAt;
    private final List<Run> runs = new ArrayList<>();

    private static final int MAX_NAME_LENGTH = 128;
    private static final int MAX_DESCRIPTION_LENGTH = 512;

    public Experiment(Long id, String name, String description, String ownerUsername) {
        this.id = id;
        setName(name);
        setDescription(description);
        setOwnerUsername(ownerUsername);
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public void addRun(Run run) {
        this.runs.add(run);
        setUpdatedAt();
    }

    public void removeRun(long runId) {
        runs.removeIf(r -> r.getId() == runId);
        setUpdatedAt();
    }

    public List<Run> getRuns() {
        return new ArrayList<>(runs);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
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
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название не может быть пустым");
        }
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("Название не должно превышать " + MAX_NAME_LENGTH + " символов");
        }
        this.name = name.trim();
        setUpdatedAt();
    }

    public void setDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            this.description = "";
        } else if (description.length() > MAX_DESCRIPTION_LENGTH) {
            throw new IllegalArgumentException("Описание не должно превышать " + MAX_DESCRIPTION_LENGTH + " символов");
        } else {
            this.description = description.trim();
        }
        setUpdatedAt();
    }

    public void setOwnerUsername(String ownerUsername) {
        if (ownerUsername == null || ownerUsername.trim().isEmpty()) {
            this.ownerUsername = "SYSTEM";
        } else {
            this.ownerUsername = ownerUsername.trim();
        }
        setUpdatedAt();
    }

    public void setUpdatedAt() {
        this.updatedAt = Instant.now();
    }

    @Override
    public String toString() {
        return "Experiment #" + id + " { name='" + name + "', owner='" + ownerUsername + "', runs=" + runs.size() + " }";
    }
}
