package ru.laba5.service;

import ru.laba5.domain.*;
import java.util.*;
import java.util.stream.Collectors;

public class CollectionManager {
    private final Map<Long, Experiment> experiments;
    private final Map<Long, Run> runs;
    private final Map<Long, RunResult> results;
    private final Map<Long, Experiment> mutableExperiments;
    private final Map<Long, Run> mutableRuns;
    private final Map<Long, RunResult> mutableResults;

    private final IdGenerator experimentIds = new IdGenerator();
    private final IdGenerator runIds = new IdGenerator();
    private final IdGenerator resultIds = new IdGenerator();

    public CollectionManager() {
        Map<Long, Experiment> expMutable = new HashMap<>();
        Map<Long, Run> runMutable = new HashMap<>();
        Map<Long, RunResult> resMutable = new HashMap<>();

        this.experiments = Collections.unmodifiableMap(expMutable);
        this.runs = Collections.unmodifiableMap(runMutable);
        this.results = Collections.unmodifiableMap(resMutable);
        this.mutableExperiments = expMutable;
        this.mutableRuns = runMutable;
        this.mutableResults = resMutable;
    }

    // ✅ EXPERIMENT методы
    public long getNextExperimentId() { return experimentIds.nextId(); }

    public void addExperiment(Experiment exp) {
        if (mutableExperiments.containsKey(exp.getId())) {
            throw new IllegalArgumentException("Experiment ID " + exp.getId() + " уже существует");
        }
        mutableExperiments.put(exp.getId(), exp);
    }

    public Experiment findExperimentById(long id) {
        return experiments.get(id);
    }

    public List<Experiment> getAllExperiments() {
        return new ArrayList<>(experiments.values());
    }

    public List<Experiment> getExperimentsByOwner(String owner) {
        return experiments.values().stream()
                .filter(e -> e.getOwnerUsername().equals(owner))
                .collect(Collectors.toList());
    }

    public void updateExperiment(Experiment updatedExp) {
        if (!mutableExperiments.containsKey(updatedExp.getId())) {
            throw new IllegalArgumentException("Experiment #" + updatedExp.getId() + " не найден");
        }
        mutableExperiments.put(updatedExp.getId(), updatedExp);
    }

    public long getNextRunId() { return runIds.nextId(); }

    public void addRun(Run run) {
        if (!mutableExperiments.containsKey(run.getExperimentId())) {
            throw new IllegalArgumentException("Experiment #" + run.getExperimentId() + " не существует");
        }
        mutableRuns.put(run.getId(), run);
    }

    public Run findRunById(long id) {
        return runs.get(id);
    }

    public List<Run> getRunsByExperiment(long experimentId) {
        return runs.values().stream()
                .filter(r -> r.getExperimentId() == experimentId)
                .collect(Collectors.toList());
    }

    public void updateRun(Run updatedRun) {
        if (!mutableRuns.containsKey(updatedRun.getId())) {
            throw new IllegalArgumentException("Run #" + updatedRun.getId() + " не найден");
        }
        mutableRuns.put(updatedRun.getId(), updatedRun);
    }

    public long getNextResultId() { return resultIds.nextId(); }

    public void addResult(RunResult result) {
        if (!mutableRuns.containsKey(result.getRunId())) {
            throw new IllegalArgumentException("Run #" + result.getRunId() + " не существует");
        }
        mutableResults.put(result.getId(), result);
    }

    public RunResult findResultById(long id) {
        return results.get(id);
    }

    public List<RunResult> getResultsByRun(long runId) {
        return results.values().stream()
                .filter(r -> r.getRunId() == runId)
                .collect(Collectors.toList());
    }

    public List<RunResult> getResultsByExperiment(long experimentId) {
        return getRunsByExperiment(experimentId).stream()
                .flatMap(run -> getResultsByRun(run.getId()).stream())
                .collect(Collectors.toList());
    }

    public Map<MeasurementParam, Summary> getExperimentSummary(long experimentId) {
        List<RunResult> expResults = getResultsByExperiment(experimentId);

        return expResults.stream()
                .collect(Collectors.groupingBy(
                        RunResult::getParam,
                        Collectors.collectingAndThen(
                                Collectors.mapping(RunResult::getValue, Collectors.toList()),
                                list -> {
                                    DoubleSummaryStatistics stats = list.stream()
                                            .mapToDouble(Double::doubleValue).summaryStatistics();
                                    return new Summary((long)stats.getCount(), stats.getMin(),
                                            stats.getMax(), stats.getAverage());
                                }
                        )
                ));
    }

    public static class Summary {
        public final long count;
        public final double min, max, avg;

        public Summary(long count, double min, double max, double avg) {
            this.count = count; this.min = min; this.max = max; this.avg = avg;
        }

        public long getCount() { return count; }
        public double getMin() { return min; }
        public double getMax() { return max; }
        public double getAvg() { return avg; }

        @Override
        public String toString() {
            return String.format("count=%d min=%.2f max=%.2f avg=%.2f", count, min, max, avg);
        }
    }
}
