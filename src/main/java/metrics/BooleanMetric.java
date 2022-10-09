package metrics;

import java.util.function.BooleanSupplier;

public class BooleanMetric extends Metric {

    private BooleanSupplier booleanSupplier;

    public BooleanMetric(String name, BooleanSupplier booleanSupplier) {
        super(name);
        this.booleanSupplier = booleanSupplier;
    }

    @Override
    public String getDataAsString() {
        return String.valueOf(booleanSupplier.getAsBoolean());
    }
}