package logger;

import java.io.IOException;

public class EventLog extends Log {

    public enum Severity {
        INFO, WARN, ERROR, SEVERE
    }

    public EventLog(String fileName) {
        super(fileName);
        initialTimestampWrite();
    }
    
    /*
     * Writes a event entry to the event log with error status and message.
     * 
     * @param error code
     * 
     * @param String message that describes the event
     */
    public void writeToEventLog(Severity error, String msg) {
        if (diskSpaceCheck()) {
            if (fileSizeCheck()) {
                try {
                    this.logWriter.append(String.format("%s, %s, %s\n", getPresentTimeInDateFormat(), error, msg));
                    this.logWriter.flush();
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