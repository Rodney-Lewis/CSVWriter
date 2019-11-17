package app;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;

public class Log {

    private final String FILE_EXTENSION = ".csv";
    private final int MAX_LOG_FILE_COUNT = 10;
    private final int MIN_REQUIRED_FREE_SPACE = 10240000; //10MB in bytes
    protected final int MAX_LOG_SIZE = 512000; //500KB in bytes
    
    protected File log;
    protected Boolean logSizeThresholdExceeded = false;
    protected Boolean requiredFreeSpaceThresholdExceeded = false;

    protected Log(String fileName) {
        createLogFile(fileName);
    }

    /*
        Attempt to create a event or timeseries log within constraints.
        Requires 10MB of free disk space or more. Log files can not be larger then 500KB.
        @param name of the log generated
    */
    private void createLogFile(String fileName) {
        if(requiredDiskSpaceCheck()) {
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

    /*
        Gets the current time and date of the present to write as a timestamp.
        @return returns the current date and time as a string
    */
    protected String getPresentTimeInDateFormat() {
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        return cal.getTime().toString();
    }

    /*
        @return true - If there is more then 10MB available
        @return false - If there is less then 10MB available
    */  
    protected Boolean requiredDiskSpaceCheck() {
        log = new File("."); 
        if(log.getFreeSpace() < MIN_REQUIRED_FREE_SPACE)
            return false;
        else
            return true;
    }

    /*
        Console logging messages if constraits are violated.
    */
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