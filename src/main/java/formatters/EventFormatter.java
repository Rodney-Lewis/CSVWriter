package formatters;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class EventFormatter extends Formatter {

    private final String eventFormat;
    private String eventFileHeader;
    private String eventFileTail;

    public EventFormatter() {
        this.eventFormat = "[%1$tF %1$tH:%1$tM:%1$tS.%1$tL %1$tZ] [%2$-7s] %3$s %n";
        this.eventFileHeader = "";
        this.eventFileTail = "";
    }

    @Override
    public String format(LogRecord lr) {
        return String.format(eventFormat, new Date(lr.getMillis()),
                lr.getLevel().getLocalizedName(), lr.getMessage());
    }

    @Override
    public String getHead(Handler h) {
        return eventFileHeader;
    }

    public String getEventFileHeader() {
        return eventFileHeader;
    }

    public void setEventFileHeader(String csvFileHeader) {
        this.eventFileHeader = csvFileHeader + "\n";
    }

    public String getEventFileTail() {
        return eventFileTail;
    }

    public void setEventFileTail(String csvFileTail) {
        this.eventFileTail = csvFileTail;
    }

}
