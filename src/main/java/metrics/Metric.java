package metrics;

public abstract class Metric {

    private String name;

    protected Metric(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract String getDataAsString();
}