package metrics;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class TimeseriesHandler {
    private List<MetricDefinition> metricDefinitions;
    private final LongSupplier secondsFromStartSupplier;
    private final LongSupplier timestampEpoch;
    private final Long initTime;

    public TimeseriesHandler() {
        initTime = System.currentTimeMillis();

        secondsFromStartSupplier = () -> (System.currentTimeMillis() / 1000L) - initTime / 1000L;
        timestampEpoch = () -> System.currentTimeMillis();

        metricDefinitions = new ArrayList<MetricDefinition>();
        metricDefinitions.add(new LongMetric("Seconds from start", secondsFromStartSupplier));
        metricDefinitions.add(new LongMetric("Epoch Timestamp", timestampEpoch));
    }

    public void registerMetric(String name, BooleanSupplier booleanSupplier) {
        metricDefinitions.add(new BooleanMetric(name, booleanSupplier));
    }

    public void registerMetric(String name, LongSupplier longSupplier) {
        metricDefinitions.add(new LongMetric(name, longSupplier));
    }

    public void registerMetric(String name, IntSupplier intSupplier) {
        metricDefinitions.add(new IntMetric(name, intSupplier));
    }

    public void registerMetric(String name, DoubleSupplier doubleSupplier) {
        metricDefinitions.add(new DoubleMetric(name, doubleSupplier));
    }

    public void registerMetric(String name, Supplier<String> stringSupplier) {
        metricDefinitions.add(new StringMetric(name, stringSupplier));
    }

    public List<String> getMetricNames() {
        return metricDefinitions.stream().map(MetricDefinition::getName)
                .collect(Collectors.toList());
    }

    public String getMetricNamesCsv() {
        return metricDefinitions.stream().map(MetricDefinition::getName)
                .collect(Collectors.joining(","));
    }

    public List<String> getMetricData() {
        return metricDefinitions.stream().map(MetricDefinition::getDataAsString)
                .collect(Collectors.toList());
    }

}
