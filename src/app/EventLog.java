package app;

import java.io.IOException;

public class EventLog extends Log {

    public enum Severity {
        INFO, WARN, ERROR, SEVERE
    }

    private static EventLog instance = null;

    /*
     * Singleton so that the EventLog is GLOBAL.
     */
    public static EventLog getInstance() {
        if (instance == null) {
            instance = new EventLog("Event");
        }
        return instance;
    }

    private EventLog(String fileName) {
        super(fileName);
        initialTimestampWrite();
    }

    private void initialTimestampWrite() {
        try {
            this.logWriter.append(String.format("%s, %s, %s\n", "########", "START " + getPresentTimeInDateFormat(), "########"));
            this.logWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Writes a event entry to the event log with error status and message.
     * 
     * @param error code
     * 
     * @param String message that describes the event
     */
    public void writeToEventLog(Severity error, String msg) {

        if (fileSizeCheck() && diskSpaceCheck()) {
            try {
                this.logWriter.append(String.format("%s, %s, %s\n", getPresentTimeInDateFormat(), error, msg));
                this.logWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            logFileSizeExceeded();
            initializeNewFile();
            initialTimestampWrite();
        }
    }
}