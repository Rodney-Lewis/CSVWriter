package metrics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.stream.Collectors;

import logger.CSVWriter;

public class TimeseriesHandler extends CSVWriter {
    private Boolean headersInitialized = false;
    private List<Metric> metrics = new ArrayList<Metric>();
    private Long initTime = System.currentTimeMillis();

    public TimeseriesHandler(String fileName) {
        super(fileName);
        LongSupplier longSupplier = () -> System.currentTimeMillis() - initTime;
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
        if (diskSpaceCheck()) {
            if (fileSizeCheck()) {
                try {
                    if (headersInitialized == false) {
                        csvWriter.append(String.format("%s\n", convertToCsv(getMetricNames())));
                        headersInitialized = true;
                    }
                    csvWriter.append(String.format("%s\n", convertToCsv(getMetricData())));
                    csvWriter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                newFileWithEpochTimestamp();
            }
        }
    }
}