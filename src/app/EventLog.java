package app;

import java.io.FileWriter;
import java.io.IOException;

public class EventLog extends Log {

    public enum Severity {
        INFO,
        WARN,
        ERROR,
        SEVERE
    }

    private FileWriter eventLogWriter;
    private static EventLog instance = null;

    /*
        Singleton so that the EventLog is GLOBAL.
    */
    public static EventLog getInstance() {
        if (instance == null) {
            instance = new EventLog("Event");
        }
        return instance;
    }

    private EventLog(String fileName) {
        super(fileName);
        buildLogWriter();
    }

    /*
        initalizes FileWriter with log information.
    */
    private void buildLogWriter() {
        if(!requiredFreeSpaceThresholdExceeded) {
            try {
                this.eventLogWriter = new FileWriter(log);
                this.eventLogWriter.write(String.format("%s, %s, %s\n", "TIME", "SEVERITY", "MESSAGE"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
        Writes a event entry to the event log with error status and message.
        @param error code
        @param String message that describes the event
    */
    public void writeToEventLog(Severity error, String msg){
        if(log.length() < MAX_LOG_SIZE) {
            try {
                this.eventLogWriter.write(String.format("%s, %s, %s\n", getPresentTimeInDateFormat(), error, msg));
                this.eventLogWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            logSizeThresholdExceeded();
        }   
    }
}