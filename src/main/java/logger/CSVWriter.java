package logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public abstract class CSVWriter {
    private final String FILE_EXTENSION = ".csv";
    private final double STORAGE_PURGE_PERCENT = 0.975;
    private final int MAX_LOG_FILE_COUNT;
    private final long MIN_REQUIRED_FREE_SPACE;
    private final long MAX_DIRECTORY_SIZE;
    private final long MAX_LOG_SIZE;
    private final File LOG_DIRECTORY;
    private final File ROOT_DIRECTORY;
    private final String FILENAME;
    private final long INIT_TIME_SECONDS;
    private File log;
    private String os;
    private long lastFlush;

    protected boolean headersInitialized = false;
    protected FileWriter csvWriter;


    protected CSVWriter(String fileName) {
        this(fileName, 12, EMultiByte.MEGABYTE.bytes * 25, EMultiByte.MEGABYTE.bytes * 10,
                EMultiByte.KILOBYTE.bytes * 50);
    }

    protected CSVWriter(String fileName, int maxLogFileCount, long minimumRequiredFreeSpace,
            long maxDirectorySize, long maxLogSize) {
        os = System.getProperty("os.name");
        if (os.toLowerCase().contains("windows")) {
            this.LOG_DIRECTORY =
                    new File(System.getProperty("user.home") + "\\Documents\\FRC-CSV\\");
        } else {
            this.LOG_DIRECTORY = new File("/var/log/FRC-CSV/");
        }
        this.ROOT_DIRECTORY =
                new File(Paths.get(this.LOG_DIRECTORY.getPath()).getRoot().toString());
        this.FILENAME = fileName;
        this.MAX_LOG_FILE_COUNT = maxLogFileCount;
        this.MIN_REQUIRED_FREE_SPACE = minimumRequiredFreeSpace;
        this.MAX_DIRECTORY_SIZE = maxDirectorySize;
        this.MAX_LOG_SIZE = maxLogSize;
        this.INIT_TIME_SECONDS = System.currentTimeMillis() / 1000L;
        this.lastFlush = 0;
        createNewLogFile();
    }

    private void createNewLogFile() {
        try {
            headersInitialized = false;

            if (!LOG_DIRECTORY.exists()) {
                LOG_DIRECTORY.mkdirs();
            }

            // Close the previous file if one existed
            if (csvWriter != null) {
                csvWriter.close();
            }

            File[] files = LOG_DIRECTORY
                    .listFiles((dir, name) -> name.contains(FILENAME + FILE_EXTENSION));

            if (files.length >= MAX_LOG_FILE_COUNT) {
                System.out.println("MAX_LOG_FILE_COUNT exceeded, purging oldest files first.");
                for (int i = 0; i < files.length - MAX_LOG_FILE_COUNT; i++) {
                    files[i].delete();
                }
            }

            log = new File(LOG_DIRECTORY.getPath() + File.separatorChar + FILENAME + FILE_EXTENSION
                    + "-" + System.currentTimeMillis());
            csvWriter = new FileWriter(log);
            System.out.println(String.format("New file created: %s", log.getName()));

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Log and filesystem size checks.
     */
    private boolean isDiskSpaceLimitExceeded() {
        return ROOT_DIRECTORY.getFreeSpace() <= MIN_REQUIRED_FREE_SPACE * STORAGE_PURGE_PERCENT;
    }

    private boolean isFileSizeLimitExceeded() {
        return log.length() > MAX_LOG_SIZE;
    }

    private boolean isDirectorySizeLimitExceeded() {
        return getDirectorySize() > MAX_DIRECTORY_SIZE * STORAGE_PURGE_PERCENT;
    }

    private long getDirectorySize() {
        File[] files = LOG_DIRECTORY.listFiles();
        long dirSize = 0;

        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                dirSize += files[i].length();
            }
        }
        return dirSize;
    }

    private void purgeOldestFilesDirectorySpace() {
        File[] files = LOG_DIRECTORY.listFiles((dir, name) -> name.contains(FILENAME + FILE_EXTENSION));
        long dirSize = getDirectorySize();
        long fileSize = 0;

        for (File file : files) {
            fileSize = file.length();
            if (file.delete()) {
                dirSize = dirSize - fileSize;
            }

            if (dirSize < MAX_DIRECTORY_SIZE * 0.8) {
                break;
            }
        }
    }

    private void purgeOldestFilesRootDirectorySpace() {
        File[] files = LOG_DIRECTORY.listFiles((dir, name) -> name.contains(FILENAME + FILE_EXTENSION));
        long dirSize = ROOT_DIRECTORY.getFreeSpace();
        long fileSize = 0;

        for (File file : files) {
            fileSize = file.length();
            if (file.delete()) {
                dirSize = dirSize + fileSize;
            }

            if (dirSize > MIN_REQUIRED_FREE_SPACE * 1.2) {
                break;
            }
        }
    }

    private void flushLog(boolean force) throws IOException {
        Long currentTimeInSeconds = System.currentTimeMillis() / 1000L;
        if (currentTimeInSeconds > lastFlush || force) {
            csvWriter.flush();
            lastFlush = currentTimeInSeconds + 5000;
        }
    }

    protected void writeToLog(String csv) {
        try {
            if (isDiskSpaceLimitExceeded()) {
                System.out.println(
                        "MIN_REQUIRED_FREE_SPACE exceeded, logging paused until addressed. Purging oldest files first.");
                purgeOldestFilesRootDirectorySpace();
            } else {
                csvWriter.append(csv);
                flushLog(false);

                if (isDirectorySizeLimitExceeded()) {
                    System.out.println("MAX_DIRECTORY_SIZE exceeded, purging oldest files first.");
                    purgeOldestFilesDirectorySpace();
                }

                if (isFileSizeLimitExceeded()) {
                    createNewLogFile();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    protected void writeHeadersToLog(String csv) {
        writeToLog(csv);
        headersInitialized = true;
    }

    protected long getInitTimeSeconds() {
        return INIT_TIME_SECONDS;
    }

}
