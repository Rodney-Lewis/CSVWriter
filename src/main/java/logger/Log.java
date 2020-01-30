package logger;

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

    private final String filePath;
    private final File rootDir;
    private final String fileName;
    private File log;

    protected FileWriter logWriter;
    String os;

    protected Log(String fileName) {
        os = System.getProperty("os.name");
        if (os.toLowerCase().contains("windows")) {
            this.filePath = "C:\\Logs\\FRC\\";
            this.rootDir = new File("C:\\");
        } else {
            this.filePath = "/var/log/FRC/";
            this.rootDir =  new File("/");
        }
        this.fileName = fileName;
        initializeNewFile();
    }

    protected void initializeNewFile() {
        int iterator = 0;
        Boolean fileSet = false;

        log = new File(filePath + fileName + FILE_EXTENSION);
        if (diskSpaceCheck()) {
            while ((MAX_LOG_FILE_COUNT > iterator) && !fileSet) {
                log = new File(filePath + fileName + "." + iterator + FILE_EXTENSION);
                if (log.exists() && (log.length() <= MAX_LOG_SIZE)) {
                    try {
                        logWriter = new FileWriter(log, true);
                        fileSet = true;
                    } catch (final IOException e) {
                        // Catch exceptions in a seperate log
                    }
                } else if (!log.exists()) {
                    try {
                        log.getParentFile().mkdirs();
                        log.createNewFile();
                        logWriter = new FileWriter(log);
                        fileSet = true;
                    } catch (final IOException e) {
                        // Catch exceptions in a seperate log
                    }
                }
                iterator++;
            }
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
            this.logWriter.append(String.format("%s,%s\n", "LOGGING STARTED", getPresentTimeInDateFormat()));
            this.logWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}