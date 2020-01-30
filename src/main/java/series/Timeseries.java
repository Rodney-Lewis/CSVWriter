package series;

public class Timeseries { 

    private String name;
    private double value;

    public Timeseries(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

}