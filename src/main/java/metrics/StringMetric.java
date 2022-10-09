package metrics;

import java.util.function.Supplier;

public class StringMetric extends Metric {

    private Supplier<String> stringSupplier;

    public StringMetric(String name, Supplier<String> stringSupplier) {
        super(name);
        this.stringSupplier = stringSupplier;
    }

    @Override
    public String getDataAsString() {
        return stringSupplier.get();
    }
}