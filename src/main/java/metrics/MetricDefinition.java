package metrics;

public abstract class MetricDefinition {

    private String name;

    protected MetricDefinition(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract String getDataAsString();
}