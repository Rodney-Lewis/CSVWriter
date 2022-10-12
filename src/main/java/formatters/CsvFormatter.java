package formatters;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class CsvFormatter extends Formatter {

    private final String csvFormat;
    private String csvFileHeader;
    private String csvFileTail;

    public CsvFormatter() {
        this.csvFormat = "%1$2s %n";
        this.csvFileHeader = "";
        this.csvFileTail = "";
    }

    @Override
    public String format(LogRecord lr) {
        return String.format(csvFormat, lr.getMessage());
    }

    @Override
    public String getHead(Handler h) {
        return csvFileHeader;
    }

    public String getCsvFileHeader() {
        return csvFileHeader;
    }

    public void setCsvFileHeader(String csvFileHeader) {
        this.csvFileHeader = csvFileHeader + "\n";
    }

    public String getCsvFileTail() {
        return csvFileTail;
    }

    public void setCsvFileTail(String csvFileTail) {
        this.csvFileTail = csvFileTail;
    }

}
