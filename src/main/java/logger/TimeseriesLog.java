package logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import series.Timeseries;

public class TimeseriesLog extends Log {

    Boolean headersInitialized = false;

    public TimeseriesLog(String fileName) {
        super(fileName);
        initialTimestampWrite();
    }

    public void writeTimeseriesMetricsToTimeseriesLog(List<Timeseries> metrics) {
        if (diskSpaceCheck()) {
            if (fileSizeCheck()) {
                try {
                    if (headersInitialized == false) {
                        logWriter.append(String.format("%s,%s\n", getPresentTimeInDateFormat(),
                                timeseriesListToTimeseriesLogStringHeaders(metrics)));
                    }
                    logWriter.append(String.format("%s,%s\n", getPresentTimeInDateFormat(),
                            timeseriesListToTimeseriesLogString(metrics)));
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

    public void writeListMetricsToTimeseriesLog(List<Double> metrics) {
        if (diskSpaceCheck()) {
            if (fileSizeCheck()) {
                try {
                    logWriter.append(String.format("%s,%s\n", getPresentTimeInDateFormat(),
                            doubleListToTimeseriesLogString(metrics)));
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

    public void writeArrayMetricsToTimeseriesLog(Double[] metrics) {
        if (diskSpaceCheck()) {
            if (fileSizeCheck()) {
                try {
                    logWriter.append(String.format("%s,%s\n", getPresentTimeInDateFormat(),
                            doubleArrayToTimeseriesLogString(metrics)));
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

    public void writeMetricToTimeseriesLog(Double metric) {
        if (diskSpaceCheck()) {
            if (fileSizeCheck()) {
                try {
                    logWriter.append(String.format("%s,%s\n", getPresentTimeInDateFormat(), metric));
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

    private String doubleListToTimeseriesLogString(List<Double> metrics) {
        List<String> metricsStringList = new ArrayList<String>();
        for (Double var : metrics) {
            metricsStringList.add(String.valueOf(var));
        }
        return String.join(",", metricsStringList);
    }

    private String timeseriesListToTimeseriesLogString(List<Timeseries> metrics) {
        List<String> metricsStringList = new ArrayList<String>();
        for (Timeseries var : metrics) {
            metricsStringList.add(String.valueOf(var.getValue()));
        }
        return String.join(",", metricsStringList);
    }

    private String timeseriesListToTimeseriesLogStringHeaders(List<Timeseries> metrics) {
        List<String> metricsStringList = new ArrayList<String>();
        for (Timeseries var : metrics) {
            metricsStringList.add(String.valueOf(var.getName()));
        }
        return String.join(",", metricsStringList);
    }

    private String doubleArrayToTimeseriesLogString(Double[] metrics) {
        List<String> metricsStringList = new ArrayList<String>();
        for (Double var : metrics) {
            metricsStringList.add(String.valueOf(var));
        }
        return String.join(",", metricsStringList);
    }
}
