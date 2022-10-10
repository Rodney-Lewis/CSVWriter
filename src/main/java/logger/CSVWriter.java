package logger;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class CSVWriter {
    private final String FILE_EXTENSION = ".csv";
    private final int MAX_LOG_FILE_COUNT = 20;
    private final int MIN_REQUIRED_FREE_SPACE = 1024 * 10000;
    private final int MAX_LOG_SIZE = 1024 * 100;
    private final String filePath;
    private final File rootDir;
    private final String fileName;
    private FilenameFilter filter;
    private File log;

    protected FileWriter csvWriter;
    private String os;

    protected CSVWriter(String fileName) {
        os = System.getProperty("os.name");
        if (os.toLowerCase().contains("windows")) {
            this.filePath = "C:\\Logs\\FRC\\";
            this.rootDir = new File("C:\\");
        } else {
            this.filePath = "/var/log/FRC/";
            this.rootDir = new File("/");
        }
        this.fileName = fileName;
        newFileWithEpochTimestamp();
    }

    protected void newFileWithEpochTimestamp() {
        File logDir = new File(filePath);
        filter = (dir, name) -> name.contains(fileName + FILE_EXTENSION);

        File[] files = logDir.listFiles(filter);

        if (files.length >= MAX_LOG_FILE_COUNT) {
            for (int i = 0; i < files.length - MAX_LOG_FILE_COUNT; i++) {
                files[i].delete();
            }
        }

        log = new File(filePath + System.currentTimeMillis() + "-" + fileName + FILE_EXTENSION);
        try {
            csvWriter = new FileWriter(log);
        } catch (IOException e) {
            e.printStackTrace();
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
        if (MIN_REQUIRED_FREE_SPACE <= rootDir.getFreeSpace())
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

    protected void initialTimestampWrite() {
        try {
            this.csvWriter.append(String.format("%s,%s\n", "LOGGING STARTED", getPresentTimeInDateFormat()));
            this.csvWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}