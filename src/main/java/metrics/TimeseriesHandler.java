package metrics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.stream.Collectors;

import logger.Log;

public class TimeseriesHandler extends Log {
    Boolean headersInitialized = false;

    private List<Metric> metrics = new ArrayList<Metric>();

    public TimeseriesHandler(String fileName) {
        super(fileName);
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

    public void writeMetricsToTimeseriesLog(List<Metric> metrics) {
        if (diskSpaceCheck()) {
            if (fileSizeCheck()) {
                try {
                    if (headersInitialized == false) {
                        logWriter.append(String.format("%s,%s\n", getPresentTimeInDateFormat(), getMetricNames()));
                    }
                    logWriter.append(String.format("%s,%s\n", getPresentTimeInDateFormat(), getMetricData()));
                    logWriter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                initializeNewFile();
                initialTimestampWrite();
            }
        }
    }

}
