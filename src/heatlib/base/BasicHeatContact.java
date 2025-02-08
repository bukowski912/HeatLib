package heatlib.base;

import heatlib.api.IHeatCapacitor;
import heatlib.api.IHeatContact.IDyadicHeatContact;
import heatlib.api.Thermals;
import heatlib.common.Direction;

public class BasicHeatContact extends IDyadicHeatContact {

	public BasicHeatContact(IHeatCapacitor first, IHeatCapacitor second) {
		super(new Entry(first), new Entry(second));
	}

	public BasicHeatContact(IHeatCapacitor first, Direction firstToSecond, IHeatCapacitor second) {
		super(new Entry(first, firstToSecond), new Entry(second, firstToSecond.opposite()));
	}

	@Override
	public void simulate() {
		final IHeatCapacitor first = firstCapacitor(), second = secondCapacitor();
		double invConduction = Thermals.adjacentConduction(firstThermals(), secondThermals());

		double firstTemp = firstCapacitor().temperature();
		double secondTemp = secondCapacitor().temperature();

		long firstCap = firstCapacitor().capacity();
		long secondCap = secondCapacitor().capacity();

		double finalTemp = (firstTemp * firstCap + secondTemp * secondCap) / (firstCap + secondCap);

		first.handleHeat((long) ((finalTemp - firstTemp) * firstCap));
		second.handleHeat((long) ((finalTemp - secondTemp) * secondCap));
	}
}
