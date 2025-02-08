package heatlib.base;

import heatlib.api.IHeatCapacitor;
import heatlib.api.IHeatContact.IDyadicHeatContact;
import heatlib.api.Thermals;
import heatlib.common.Direction;

public class BasicHeatContact extends IDyadicHeatContact {

	public final IHeatCapacitor first, second;
	public final Direction direction;

	public BasicHeatContact(IHeatCapacitor first, IHeatCapacitor second, Direction direction) {
		if (first == second) {
			throw new IllegalArgumentException("Circular contact");
		}
		this.first = first;
		this.second = second;
		this.direction = direction;
	}

	@Override
	public final IHeatCapacitor getFirstCapacitor() {
		return first;
	}

	@Override
	public final IHeatCapacitor getSecondCapacitor() {
		return second;
	}

	@Override
	public final Direction getFirstDirection() {
		return direction;
	}

	@Override
	public double simulate() {
		double invConduction = Thermals.adjacentConduction(this);
		double tempToTransfer = (first.getTemperature() - second.getTemperature()) / invConduction;
		double heatToTransfer = tempToTransfer * first.getCapacity();
		first.handleHeat(-heatToTransfer);
		second.handleHeat(heatToTransfer);
		return tempToTransfer;
	}
}
