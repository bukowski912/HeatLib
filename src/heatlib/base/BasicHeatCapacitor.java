package heatlib.base;

import heatlib.api.HeatAPI;
import heatlib.api.IHeatCapacitor;
import heatlib.api.IHeatManifold;
import heatlib.api.Thermals;
import heatlib.common.Direction;

public class BasicHeatCapacitor implements IHeatCapacitor {

	private IHeatManifold ownerManifold;

	private long heatCapacity;
	private Thermals thermals;

	private long storedHeat;
	private long heatToHandle;

	public BasicHeatCapacitor(long capacity) {
		this(capacity, Thermals.AMBIENT);
	}

	public BasicHeatCapacitor(long heatCapacity, Thermals thermals) {
		this.heatCapacity = HeatAPI.validateHeatCapacity(heatCapacity);
		this.thermals = HeatAPI.validateThermals(thermals);
		temperature(HeatAPI.AMBIENT_TEMP);
	}

	@Override
	public void handleHeat(long transfer) {
		System.out.format("Handle heat: %d J, %.2f K\n", transfer, (double) transfer / capacity());
		heatToHandle += transfer;
	}

	@Override
	public void updateHeat() {
		System.out.format("Update heat: (%d J, %.2f K)", getHeat(), temperature());
		storedHeat += heatToHandle;
		heatToHandle = 0;
		System.out.format(" -> (%d J, %.2f K)\n", getHeat(), temperature());
	}

	@Override
	public final long getHeat() {
		if (storedHeat < 0) {
			throw new IllegalStateException(String.format("Negative stored heat: %f", storedHeat));
		}
		return storedHeat;
	}

	@Override
	public final void setHeat(long heat) {
		if (getHeat() != heat) {
			storedHeat = heat;
		}
	}

	@Override
	public IHeatManifold getManifold() {
		return ownerManifold;
	}

	@Override
	public final long capacity() {
		return heatCapacity;
	}

	@Override
	public void setManifold(IHeatManifold heatManifold) {
		this.ownerManifold = heatManifold;
	}

	@Override
	public void capacity(long capacity, boolean updateHeat) {
		if (updateHeat && storedHeat != -1) {
			setHeat((long) (storedHeat + (capacity - heatCapacity) * HeatAPI.AMBIENT_TEMP));
		}
		setHeatCapacity(capacity);
	}

	private void setHeatCapacity(long heatCapacity) {
		this.heatCapacity = HeatAPI.validateHeatCapacity(heatCapacity);
	}

	@Override
	public Thermals thermals(Direction side) {
		return thermals();
	}

	public final Thermals thermals() {
		return thermals;
	}

	@Override
	public void setThermals(Thermals thermals, Direction side) {
		setThermals(thermals);
	}

	@Override
	public final void setThermals(Thermals thermals) {
		this.thermals = HeatAPI.validateThermals(thermals);
	}
}
