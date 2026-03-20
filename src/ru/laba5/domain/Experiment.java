package ru.laba5.domain;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

public final class Experiment {
    private final long id;                    // primitive long
    private final String name;
    private final String description;
    private final String ownerUsername;
    private final Instant createdAt;
    private final Instant updatedAt;

    public static final int MAX_NAME_LENGTH = 128;
    public static final int MAX_DESCRIPTION_LENGTH = 512;
    public static final int MAX_OWNER_LENGTH = 64;

    public Experiment(long id, String name, String description, String ownerUsername) {
        validateId(id);
        validateName(name);
        validateDescription(description);
        validateOwnerUsername(ownerUsername);

        this.id = id;
        this.name = name.trim();
        this.description = description != null ? description.trim() : "";
        this.ownerUsername = ownerUsername != null ? ownerUsername.trim() : "SYSTEM";
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }

    public Experiment updateName(String newName) {
        validateName(newName);
        return new Experiment(id, newName.trim(), description, ownerUsername);
    }

    public Experiment updateDescription(String newDescription) {
        validateDescription(newDescription);
        return new Experiment(id, name, newDescription != null ? newDescription.trim() : "", ownerUsername);
    }

    public List<Run> getRuns() {
        return Collections.emptyList();
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getOwnerUsername() { return ownerUsername; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    private static void validateId(long id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID должен быть положительным: " + id);
        }
    }

    private static void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название не может быть пустым");
        }
        if (name.trim().length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("Название <= " + MAX_NAME_LENGTH + " символов");
        }
    }

    private static void validateDescription(String description) {
        if (description != null && description.length() > MAX_DESCRIPTION_LENGTH) {
            throw new IllegalArgumentException("Описание <= " + MAX_DESCRIPTION_LENGTH + " символов");
        }
    }

    private static void validateOwnerUsername(String ownerUsername) {
        if (ownerUsername != null && ownerUsername.trim().length() > MAX_OWNER_LENGTH) {
            throw new IllegalArgumentException("Владелец <= " + MAX_OWNER_LENGTH + " символов");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Experiment that = (Experiment) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }

    @Override
    public String toString() {
        return String.format("Experiment %d name %s owner %s", id, name, ownerUsername);
    }
}
