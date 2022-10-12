package metrics;

import java.util.function.DoubleSupplier;

public class DoubleMetric extends MetricDefinition {

    private DoubleSupplier doubleSupplier;

    public DoubleMetric(String name, DoubleSupplier doubleSupplier) {
        super(name);
        this.doubleSupplier = doubleSupplier;
    }

    @Override
    public String getDataAsString() {
        return String.valueOf(doubleSupplier.getAsDouble());
    }
}