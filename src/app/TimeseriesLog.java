package app;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TimeseriesLog extends Log {

    private FileWriter timeseriesLogWriter;

    public TimeseriesLog(String fileName) {
        super(fileName);
        buildLogWriter();
    }

    /*
        initalizes FileWriter with log information.
    */
    private void buildLogWriter() {
        try {
            this.timeseriesLogWriter = new FileWriter(log);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
        Writes a list of metrics to respective timeseries log.
        @param List of metrics to be written
    */
    public void writeMetricsToTimeseriesLog(List <Double> metrics) {
        if(log.length() < MAX_LOG_SIZE) {
            try {
                timeseriesLogWriter.write(String.format("%s,%s\n", getPresentTimeInDateFormat(), doubleListToTimeseriesLogString(metrics)));
                timeseriesLogWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            logSizeThresholdExceeded();
        }
    }

    /*
        Writes a list of metrics to respective timeseries log.
        @param Array of metrics to be written
    */
    public void writeMetricsToTimeseriesLog(Double[] metrics) {
        if(log.length() < MAX_LOG_SIZE) {
            try {
                timeseriesLogWriter.write(String.format("%s,%s\n", getPresentTimeInDateFormat(), doubleArryToTimeseriesLogString(metrics)));
                timeseriesLogWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            logSizeThresholdExceeded();
        }
    }

     /*
        Writes a single metric to respective timeseries log.
        @param Single double metric to be written
    */
    public void writeMetricToTimeseriesLog(Double metric) {
        if(log.length() < MAX_LOG_SIZE) {
            try {
                timeseriesLogWriter.write(String.format("%s,%s\n", getPresentTimeInDateFormat(), metric));
                timeseriesLogWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            logSizeThresholdExceeded();
        }   
    }

    /*
        Converts array or list of doubles to a list of strings.
        Converts the list of strings to a single string delimted by commas.
        @param List or array of doubles
        @return Comma delimted string of list or array values
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
