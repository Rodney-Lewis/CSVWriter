package events;

import java.io.IOException;

import logger.CSVWriter;

public class EventsHandler extends CSVWriter {

    public enum Severity {
        TRACE, DEBUG, INFO, WARN, ERROR, FATAL, OFF
    }

    private Severity level;

    public EventsHandler(String fileName, Severity level) {
        super(fileName);
        this.level = level;
    }

    private boolean compareLevels(Severity comparedLevel) {
        return level.ordinal() >= comparedLevel.ordinal();
    }

    /*
     * Writes a event entry to the event log with error status and message.
     * 
     * @param error code
     * 
     * @param String message that describes the event
     */
    public void writeToEventLog(Severity level, String msg) {
        if (diskSpaceCheck()) {
            if (fileSizeCheck()) {
                try {
                    if (compareLevels(level)) {
                        csvWriter.append(String.format("%s, %s, %s\n", getPresentTimeInDateFormat(), level, msg));
                        csvWriter.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                newFileWithEpochTimestamp();
            }
        }
    }
}