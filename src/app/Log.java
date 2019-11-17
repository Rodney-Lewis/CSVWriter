package app;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;

public class Log {

    private final String FILE_EXTENSION = ".csv";
    private final int MAX_LOG_FILE_COUNT = 5;
    private final int MIN_REQUIRED_FREE_SPACE = 10240000; //10MB in bytes
    protected final int MAX_LOG_SIZE = 512000; //500KB in bytes
    
    protected File log;
    protected Boolean logSizeThresholdExceeded = false;
    protected Boolean requiredFreeSpaceThresholdExceeded = false;

    protected Log(String fileName) {
        createLogFile(fileName);
    }

    private void createLogFile(String fileName) {
        if(requiredDiskSpaceCheck(fileName)) {
            for(int i = 0; i < MAX_LOG_FILE_COUNT; i++) {
                log = new File(fileName + "." + i + FILE_EXTENSION);
                if(!log.exists()) {
                    try {
                        log.createNewFile();
                        break;
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            requiredDiskSpaceExceeded();
        }
    }

    protected String getPresentTimeInDateFormat() {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        return cal.getTime().toString();
    }

    protected Boolean requiredDiskSpaceCheck(String fileName) {
        log = new File("."); 
        if(log.getFreeSpace() < MIN_REQUIRED_FREE_SPACE)
            return false;
        else
            return true;
    }

    protected void logSizeThresholdExceeded() {
        if(!logSizeThresholdExceeded)
            System.out.print(getPresentTimeInDateFormat() + " " + log.getName() + " size exceeded " + MAX_LOG_SIZE + " bytes. No longer writing to file. \n\r");
        logSizeThresholdExceeded = true;
    }

    private void requiredDiskSpaceExceeded() {
        if(!requiredFreeSpaceThresholdExceeded)
            System.out.print(getPresentTimeInDateFormat() + " Not enough space available on disk. Logging requires " + MIN_REQUIRED_FREE_SPACE + " bytes or more available, skipping writing " + log.getName() + " to disk. \n\r");
        requiredFreeSpaceThresholdExceeded = true;
    }
}