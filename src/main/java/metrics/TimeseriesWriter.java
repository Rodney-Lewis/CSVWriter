package metrics;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.stream.Collectors;
import com.google.common.base.Supplier;
import logger.LogWriter;

public class TimeseriesWriter extends LogWriter {
    private List<MetricDefinition> metricDefinitions;
    private LongSupplier timestampSupplier;

    public TimeseriesWriter(String fileName) {
        super(fileName);
        metricDefinitions = new ArrayList<MetricDefinition>();
        timestampSupplier = () -> (System.currentTimeMillis() / 1000L) - getInitTimeSeconds();
        metricDefinitions.add(new LongMetric("Timestamp", timestampSupplier));
    }

    public TimeseriesWriter(String fileName, int maxLogFileCount, long minimumRequiredFreeSpace,
            long maxDirectorySize, long maxLogSize) {
        super(fileName, maxLogFileCount, minimumRequiredFreeSpace, maxDirectorySize, maxLogSize);
        LongSupplier longSupplier =
                () -> (System.currentTimeMillis() / 1000L) - getInitTimeSeconds();
        metricDefinitions.add(new LongMetric("Timestamp", longSupplier));
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

    public List<String> getMetricData() {
        return metricDefinitions.stream().map(MetricDefinition::getDataAsString)
                .collect(Collectors.toList());
    }

    public void writeMetricsToTimeseriesLog() {
        if (headersInitialized == false) {
            writeHeadersToLog(String.format("%s\n", getMetricNames()));
        }
        writeToLog(String.format("%s\n", getMetricData()));
    }

}
