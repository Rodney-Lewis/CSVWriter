package metrics;

import java.util.function.LongSupplier;

public class LongMetric extends Metric {

    private LongSupplier longSupplier;

    public LongMetric(String name, LongSupplier longSupplier) {
        super(name);
        this.longSupplier = longSupplier;
    }
    
    @Override
    public String getDataAsString() {
        return String.valueOf(longSupplier.getAsLong());
    }
}