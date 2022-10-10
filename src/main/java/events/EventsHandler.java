package events;

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

    public EventsHandler(String fileName, int maxLogFileCount, long minimumRequiredFreeSpace,
    long maxDirectorySize, long maxLogSize, Severity level) {
        super(fileName, maxLogFileCount, minimumRequiredFreeSpace, maxDirectorySize, maxLogSize);
        this.level = level;
    }

    private boolean compareLevels(Severity comparedLevel) {
        return level.ordinal() >= comparedLevel.ordinal();
    }

    /*
     * Writes a event entry to the event log with error status and message.
     * 
     * @param Severity level
     * 
     * @param Message that describes the event
     */
    public void writeToEventLog(Severity level, String message) {
        if (headersInitialized == false) {
            writeHeadersToLog(String.format("%s,%s,%s\n", "Timestamp", "Level", "Message"));
        }
        if (compareLevels(level)) {
            writeToLog(String.format("%s,%s,%s\n", String.valueOf(((System.currentTimeMillis() / 1000L) - getInitTimeSeconds())),level, message));
        }
    }
}
