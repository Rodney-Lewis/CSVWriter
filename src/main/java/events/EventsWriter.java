package events;

import logger.LogWriter;

public class EventsWriter extends LogWriter {

    public enum Level {
        TRACE, DEBUG, INFO, WARN, ERROR, FATAL, OFF
    }

    private Level level;

    public EventsWriter(String fileName, Level level) {
        super(fileName);
        this.level = level;
    }

    public EventsWriter(String fileName, int maxLogFileCount, long minimumRequiredFreeSpace,
    long maxDirectorySize, long maxLogSize, Level level) {
        super(fileName, maxLogFileCount, minimumRequiredFreeSpace, maxDirectorySize, maxLogSize);
        this.level = level;
    }

    private boolean compareLevels(Level comparedLevel) {
        return level.ordinal() >= comparedLevel.ordinal();
    }

    /*
     * Writes a event entry to the event log with error status and message.
     * 
     * @param Severity level
     * 
     * @param Message that describes the event
     */
    public void writeToEventLog(Level level, String message) {
        if (headersInitialized == false) {
            writeHeadersToLog(String.format("%s,%s,%s\n", "Timestamp", "Level", "Message"));
        }
        if (compareLevels(level)) {
            writeToLog(String.format("%s,%s,%s\n", String.valueOf(((System.currentTimeMillis() / 1000L) - getInitTimeSeconds())),level, message));
        }
    }
}
