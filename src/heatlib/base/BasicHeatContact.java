package heatlib.base;

import heatlib.api.IHeatCapacitor;
import heatlib.api.IHeatContact.IDyadicHeatContact;
import heatlib.api.Thermals;
import heatlib.common.Direction;

public class BasicHeatContact extends IDyadicHeatContact {

	public final @NotNull IHeatCapacitor first;
	public final @NotNull IHeatCapacitor second;
	public final @Nullable Direction direction;

	public BasicHeatContact(@NotNull IHeatCapacitor first, @NotNull IHeatCapacitor second, @Nullable Direction direction) {
		if (first == second) {
			throw new IllegalArgumentException("Circular contact");
		}
		this.first = first;
		this.second = second;
		this.direction = direction;
	}

	@Override
	public final @NotNull IHeatCapacitor getFirstCapacitor() {
		return first;
	}

	@Override
	public final @NotNull IHeatCapacitor getSecondCapacitor() {
		return second;
	}

	@Override
	public final @Nullable Direction getFirstDirection() {
		return direction;
	}

	@Override
	public double simulate() {
		double heatCapacity = first.getHeatCapacity();
		double invConduction = Thermals.adjacentConduction(this);
		double tempToTransfer = (first.getTemperature() - second.getTemperature()) / invConduction;
		double heatToTransfer = tempToTransfer * heatCapacity;
		first.handleHeat(-heatToTransfer);
		//Note: Our sinks in mek are "lazy" but they will update the next tick if needed
		second.handleHeat(heatToTransfer);
		return tempToTransfer;
	}
}
