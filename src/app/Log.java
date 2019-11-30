package app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

    private final String FILE_EXTENSION = ".csv";
    private final int MAX_LOG_FILE_COUNT = 20;
    private final int MIN_REQUIRED_FREE_SPACE = 10240000 * 2; // 20MB in bytes
    private final int MAX_LOG_SIZE = 512000; // 500KB in bytes

    private int fileNumber = 0;
    private String fileName = "";
    private final File dirLog = new File(".");
    private Boolean logFileLimitExceeded = false;
    private Boolean requiredFreeSpaceThresholdExceeded = false;
    private File log;

    protected Boolean existingLogUsed = false;
    protected FileWriter logWriter;

    protected Log(final String fileName) {
        this.fileName = fileName;
        initializeNewFile(fileName);
    }

    /*
     * Attempt to create a event or timeseries log within constraints. Requires 10MB
     * of free disk space or more. Log files can not be larger then 500KB.
     * 
     * @param name of the log generated
     */
    private void initializeNewFile(final String fileName) {
        if (diskSpaceCheck()) {
            for (int i = 0; i < MAX_LOG_FILE_COUNT; i++) {
                log = new File(fileName + "." + i + FILE_EXTENSION);
                if (log.exists() && diskSpaceCheck() && fileSizeCheck()) {
                    try {
                        fileNumber = i;
                        logWriter = new FileWriter(log, true);
                        existingLogUsed = true;
                    } catch (final IOException e) {
                        e.printStackTrace();
                    }
                    break;
                } else if (!log.exists()) {
                    try {
                        fileNumber = i;
                        log.createNewFile();
                        logWriter = new FileWriter(log);
                    } catch (final IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        } else {
            diskSpaceExceeded();
        }
    }

    protected void initializeNewFile() {
        try {
            logWriter.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }

        if (logFileLimitCheck()) {
            log = new File(this.fileName + "." + (fileNumber + 1) + FILE_EXTENSION);
            fileNumber++;
            try {
                log.createNewFile();
                logWriter = new FileWriter(log);
            } catch (final IOException e) {
                e.printStackTrace();
            }
        } else {
            logFileLimitExceeded();
        }
    }

    /*
     * Gets the current time and date of the present to write as a timestamp.
     * 
     * @return returns the current date and time as a string
     */
    protected String getPresentTimeInDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
    }

    /*
     * Log and filesystem size checks.
     */

    protected Boolean diskSpaceCheck() {
        if (MIN_REQUIRED_FREE_SPACE <= dirLog.getFreeSpace())
            return true;
        else
            return false;
    }

    protected Boolean fileSizeCheck() {
        if (log.length() < MAX_LOG_SIZE)
            return true;
        else
            return false;
    }

    private Boolean logFileLimitCheck() {
        if (fileNumber < MAX_LOG_FILE_COUNT)
            return true;
        else
            return false;
    }

    /*
     * Console logging messages if constraits are violated.
     */
    protected void logFileSizeExceeded() {
        System.out.print(getPresentTimeInDateFormat() + " " + log.getName() + " size exceeded " + MAX_LOG_SIZE
                + " bytes. No longer writing to file. \n\r");
    }

    private void logFileLimitExceeded() {
        if (!logFileLimitExceeded) {
            System.out.print(getPresentTimeInDateFormat() + " Log file count of " + MAX_LOG_FILE_COUNT
                    + " has been reached. \n\r");
            logFileLimitExceeded = true;
        }
    }

    private void diskSpaceExceeded() {
        if (!requiredFreeSpaceThresholdExceeded) {
            System.out.print(getPresentTimeInDateFormat() + " Not enough space available on disk. Logging requires "
                    + MIN_REQUIRED_FREE_SPACE + " bytes or more available. \n\r");
            requiredFreeSpaceThresholdExceeded = true;
        }
    }
}