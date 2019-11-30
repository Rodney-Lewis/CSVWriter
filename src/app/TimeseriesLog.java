package app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TimeseriesLog extends Log {

    public TimeseriesLog(String fileName) {
        super(fileName);
        initialTimestampWrite();
    }

    private void initialTimestampWrite() {
        try {
            this.logWriter.append(String.format("%s, %s, %s\n", "#####", "START " + getPresentTimeInDateFormat(), "#####"));
            this.logWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Writes a list of metrics to respective timeseries log.
     * 
     * @param List of metrics to be written
     */
    public void writeMetricsToTimeseriesLog(List<Double> metrics) {

        if (fileSizeCheck() && diskSpaceCheck()) {
            try {
                logWriter.append(String.format("%s,%s\n", getPresentTimeInDateFormat(),
                        doubleListToTimeseriesLogString(metrics)));
                logWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            logFileSizeExceeded();
            initializeNewFile();
            initialTimestampWrite();
        }
    }

    /*
     * Writes a list of metrics to respective timeseries log.
     * 
     * @param Array of metrics to be written
     */
    public void writeMetricsToTimeseriesLog(Double[] metrics) {

        if (fileSizeCheck() && diskSpaceCheck()) {
            try {
                logWriter.append(String.format("%s,%s\n", getPresentTimeInDateFormat(),
                        doubleArryToTimeseriesLogString(metrics)));
                logWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            logFileSizeExceeded();
            initializeNewFile();
            initialTimestampWrite();
        }
    }

    /*
     * Writes a single metric to respective timeseries log.
     * 
     * @param Single double metric to be written
     */
    public void writeMetricToTimeseriesLog(Double metric) {

        if (fileSizeCheck() && diskSpaceCheck()) {
            try {
                logWriter.append(String.format("%s,%s\n", getPresentTimeInDateFormat(), metric));
                logWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            logFileSizeExceeded();
            initializeNewFile();
            initialTimestampWrite();
        }
    }

    /*
     * Converts array or list of doubles to a list of strings. Converts the list of
     * strings to a single string delimted by commas.
     * 
     * @param List or array of doubles
     * 
     * @return Comma delimted string of list or array values
     */
    private String doubleListToTimeseriesLogString(List<Double> metrics) {
        List<String> metricsStringList = new ArrayList<String>();
        for (Double var : metrics) {
            metricsStringList.add(String.valueOf(var));
        }
        return String.join(",", metricsStringList);
    }

    private String doubleArryToTimeseriesLogString(Double[] metrics) {
        List<String> metricsStringList = new ArrayList<String>();
        for (Double var : metrics) {
            metricsStringList.add(String.valueOf(var));
        }
        return String.join(",", metricsStringList);
    }
}
