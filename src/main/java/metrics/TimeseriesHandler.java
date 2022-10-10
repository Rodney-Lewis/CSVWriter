package metrics;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.stream.Collectors;
import com.google.common.base.Supplier;
import logger.CSVWriter;

public class TimeseriesHandler extends CSVWriter {
    private List<Metric> metrics = new ArrayList<Metric>();

    public TimeseriesHandler(String fileName) {
        super(fileName);
        LongSupplier longSupplier =
                () -> (System.currentTimeMillis() / 1000L) - getInitTimeSeconds();
        metrics.add(new LongMetric("Timestamp", longSupplier));
    }

    public TimeseriesHandler(String fileName, int maxLogFileCount, long minimumRequiredFreeSpace,
            long maxDirectorySize, long maxLogSize) {
        super(fileName, maxLogFileCount, minimumRequiredFreeSpace, maxDirectorySize, maxLogSize);
        LongSupplier longSupplier =
                () -> (System.currentTimeMillis() / 1000L) - getInitTimeSeconds();
        metrics.add(new LongMetric("Timestamp", longSupplier));
    }

    public void registerMetric(String name, BooleanSupplier booleanSupplier) {
        metrics.add(new BooleanMetric(name, booleanSupplier));
    }

    public void registerMetric(String name, LongSupplier longSupplier) {
        metrics.add(new LongMetric(name, longSupplier));
    }

    public void registerMetric(String name, IntSupplier intSupplier) {
        metrics.add(new IntMetric(name, intSupplier));
    }

    public void registerMetric(String name, DoubleSupplier doubleSupplier) {
        metrics.add(new DoubleMetric(name, doubleSupplier));
    }

    public void registerMetric(String name, Supplier<String> stringSupplier) {
        metrics.add(new StringMetric(name, stringSupplier));
    }

    public List<String> getMetricNames() {
        return metrics.stream().map(Metric::getName).collect(Collectors.toList());
    }

    public List<String> getMetricData() {
        return metrics.stream().map(Metric::getDataAsString).collect(Collectors.toList());
    }

    public String convertToCsv(List<String> values) {
        return String.join(",", values);
    }

    public void writeMetricsToTimeseriesLog() {
        if (headersInitialized == false) {
            writeHeadersToLog(String.format("%s\n", convertToCsv(getMetricNames())));
        }
        writeToLog(String.format("%s\n", convertToCsv(getMetricData())));
    }

}
