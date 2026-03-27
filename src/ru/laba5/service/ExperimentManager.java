package ru.laba5.service;

import ru.laba5.domain.Experiment;
import java.util.*;
import java.util.stream.Collectors;

public class ExperimentManager {
    private final Map<Long, Experiment> experiments = new HashMap<>();
    private final IdGenerator idGenerator = new IdGenerator();

    public long getNextId() {
        return idGenerator.nextId();
    }

    public void add(Experiment exp) {
        if (experiments.containsKey(exp.getId())) {
            throw new IllegalArgumentException("Experiment ID " + exp.getId() + " already exists");
        }
        experiments.put(exp.getId(), exp);
    }

    public Experiment findById(long id) {
        return experiments.get(id);
    }

    public List<Experiment> getAll() {
        return new ArrayList<>(experiments.values());
    }

    public List<Experiment> getByOwner(String owner) {
        return experiments.values().stream()
                .filter(e -> e.getOwnerUsername().equals(owner))
                .collect(Collectors.toList());
    }

    public void update(Experiment exp) {
        if (!experiments.containsKey(exp.getId())) {
            throw new IllegalArgumentException("Experiment #" + exp.getId() + " not found");
        }
        experiments.put(exp.getId(), exp);
    }

    public void remove(long id) {
        experiments.remove(id);
    }

    public boolean exists(long id) {
        return experiments.containsKey(id);
    }
}