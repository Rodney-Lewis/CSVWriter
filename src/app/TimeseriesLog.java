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

    private void buildLogWriter() {
        try {
            this.timeseriesLogWriter = new FileWriter(log);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
