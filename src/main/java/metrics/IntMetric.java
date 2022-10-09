package metrics;

import java.util.function.IntSupplier;

public class IntMetric extends Metric {

	private IntSupplier intSupplier;

	public IntMetric(String name, IntSupplier intSupplier) {
		super(name);
		this.intSupplier = intSupplier;
	}

	@Override
	public String getDataAsString() {
		return String.valueOf(intSupplier.getAsInt());
	}
}