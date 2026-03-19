package ru.laba5.service;

import ru.laba5.domain.*;

        import java.util.*;
        import java.util.stream.Collectors;

public class CollectionManager {
    private final Map<Long, Experiment> experiments = new HashMap<>();
    private final Map<Long, Run> runs = new HashMap<>();
    private final Map<Long, RunResult> results = new HashMap<>();

    private long nextExperimentId = 1;
    private long nextRunId = 1;
    private long nextResultId = 1;

    // Experiment methods
    public long getNextExperimentId() {
        return nextExperimentId++;
    }

    public void addExperiment(Experiment exp) {
        experiments.put(exp.getId(), exp);
    }

    public Experiment findExperimentById(long id) {
        return experiments.get(id);
    }

    public List<Experiment> getAllExperiments() {
        return new ArrayList<>(experiments.values());
    }

    public List<Experiment> getExperimentsByOwner(String owner) {
        return experiments.values().stream()
                .filter(e -> e.getOwnerUsername().equalsIgnoreCase(owner))
                .collect(Collectors.toList());
    }

    public void updateExperiment(long id, String newName, String newDescription, String newOwner) {
        Experiment exp = findExperimentById(id);
        if (exp == null) throw new IllegalArgumentException("Experiment not found");
        if (newName != null) exp.setName(newName);
        if (newDescription != null) exp.setDescription(newDescription);
        if (newOwner != null) exp.setOwnerUsername(newOwner);
    }

    public void removeExperiment(long id) {
        Experiment exp = findExperimentById(id);
        if (exp == null) throw new IllegalArgumentException("Experiment not found");
        if (!exp.getRuns().isEmpty()) {
            throw new IllegalStateException("Cannot delete experiment with existing runs");
        }
        experiments.remove(id);
    }

    // Run methods
    public long getNextRunId() {
        return nextRunId++;
    }

    public void addRun(Run run) {
        if (!experiments.containsKey(run.getExperimentId())) {
            throw new IllegalArgumentException("Experiment with id " + run.getExperimentId() + " does not exist");
        }
        runs.put(run.getId(), run);
        experiments.get(run.getExperimentId()).addRun(run);
    }

    public Run findRunById(long id) {
        return runs.get(id);
    }

    public List<Run> getRunsByExperiment(long experimentId) {
        return runs.values().stream()
                .filter(r -> r.getExperimentId() == experimentId)
                .collect(Collectors.toList());
    }

    public void updateRun(long id, String newName, String newOperator) {
        Run run = findRunById(id);
        if (run == null) throw new IllegalArgumentException("Run not found");
        if (newName != null) run.setName(newName);
        if (newOperator != null) run.setOperatorName(newOperator);
    }

    public void removeRun(long id) {
        Run run = findRunById(id);
        if (run == null) throw new IllegalArgumentException("Run not found");
        if (!run.getResults().isEmpty()) {
            throw new IllegalStateException("Cannot delete run with existing results");
        }
        runs.remove(id);
        Experiment exp = experiments.get(run.getExperimentId());
        if (exp != null) {
            exp.removeRun(id);
        }
    }

    // Result methods
    public long getNextResultId() {
        return nextResultId++;
    }

    public void addResult(RunResult result) {
        if (!runs.containsKey(result.getRunId())) {
            throw new IllegalArgumentException("Run with id " + result.getRunId() + " does not exist");
        }
        results.put(result.getId(), result);
        runs.get(result.getRunId()).addResult(result);
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
        return runs.values().stream()
                .filter(r -> r.getExperimentId() == experimentId)
                .flatMap(r -> r.getResults().stream())
                .collect(Collectors.toList());
    }

    public void updateResult(long id, Double newValue, String newUnit, String newComment) {
        RunResult res = findResultById(id);
        if (res == null) throw new IllegalArgumentException("Result not found");
        if (newValue != null) res.setValue(newValue);
        if (newUnit != null) res.setUnit(newUnit);
        if (newComment != null) res.setComment(newComment);
    }

    public void removeResult(long id) {
        RunResult res = findResultById(id);
        if (res == null) throw new IllegalArgumentException("Result not found");
        results.remove(id);
        Run run = runs.get(res.getRunId());
        if (run != null) {
            run.removeResult(id);
        }
    }

    // Summary for experiment
    public Map<MeasurementParam, Summary> getExperimentSummary(long experimentId) {
        List<RunResult> experimentResults = getResultsByExperiment(experimentId);
        Map<MeasurementParam, List<Double>> valuesByParam = experimentResults.stream()
                .collect(Collectors.groupingBy(
                        RunResult::getParam,
                        Collectors.mapping(RunResult::getValue, Collectors.toList())
                ));

        Map<MeasurementParam, Summary> summary = new HashMap<>();
        for (Map.Entry<MeasurementParam, List<Double>> entry : valuesByParam.entrySet()) {
            List<Double> vals = entry.getValue();
            DoubleSummaryStatistics stats = vals.stream().mapToDouble(Double::doubleValue).summaryStatistics();
            summary.put(entry.getKey(), new Summary(
                    stats.getCount(),
                    stats.getMin(),
                    stats.getMax(),
                    stats.getAverage()
            ));
        }
        return summary;
    }

    public static class Summary {
        private final long count;
        private final double min;
        private final double max;
        private final double avg;

        public Summary(long count, double min, double max, double avg) {
            this.count = count;
            this.min = min;
            this.max = max;
            this.avg = avg;
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
