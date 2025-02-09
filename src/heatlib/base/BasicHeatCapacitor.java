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
		setHeatCapacity(heatCapacity);
		this.thermals = thermals;
		setTemp(HeatAPI.AMBIENT_TEMP);
	}

	@Override
	public void handleHeat(long transfer) {
		System.out.format("Handle heat: %d J, %.2f K\n", transfer, (double) transfer / getCapacity());
		heatToHandle += transfer;
	}

	@Override
	public void updateHeat() {
		System.out.format("Update heat: (%d J, %.2f K)", getHeat(), getTemp());
		storedHeat += heatToHandle;
		heatToHandle = 0;
		System.out.format(" -> (%d J, %.2f K)\n", getHeat(), getTemp());
	}

	@Override
	public final long getHeat() {
		if (storedHeat < 0) {
			throw new IllegalStateException(String.format("Negative stored heat: %d", storedHeat));
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
	public final long getCapacity() {
		return heatCapacity;
	}

	@Override
	public void setManifold(IHeatManifold heatManifold) {
		this.ownerManifold = heatManifold;
	}

	@Override
	public void setCapacity(long capacity, boolean updateHeat) {
		if (updateHeat && storedHeat != -1) {
			setHeat((long) (storedHeat + (capacity - heatCapacity) * HeatAPI.AMBIENT_TEMP));
		}
		setHeatCapacity(capacity);
	}

	private void setHeatCapacity(long heatCapacity) {
		if (heatCapacity < 1) {
			throw new IllegalArgumentException("Heat capacity must be at least one");
		}
		this.heatCapacity = heatCapacity;
	}

	@Override
	public Thermals getThermals(Direction side) {
		return getThermals();
	}

	public final Thermals getThermals() {
		return thermals;
	}

	@Override
	public void setThermals(Thermals thermals, Direction side) {
		setThermals(thermals);
	}

	@Override
	public final void setThermals(Thermals thermals) {
		this.thermals = thermals;
	}
}
