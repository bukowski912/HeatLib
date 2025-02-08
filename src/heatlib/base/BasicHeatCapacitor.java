package heatlib.base;

import heatlib.api.HeatAPI;
import heatlib.api.IHeatCapacitor;
import heatlib.api.IHeatManifold;
import heatlib.api.Thermals;
import heatlib.common.Direction;

public class BasicHeatCapacitor implements IHeatCapacitor {

	private IHeatManifold ownerManifold;

	private double heatCapacity;
	private Thermals thermals;

	private double storedHeat;
	private double heatToHandle;

	public BasicHeatCapacitor(double heatCapacity) {
		this(heatCapacity, Thermals.AMBIENT);
	}

	public BasicHeatCapacitor(double heatCapacity, Thermals thermals) {
		this.heatCapacity = HeatAPI.validateHeatCapacity(heatCapacity);
		this.thermals = HeatAPI.validateThermals(thermals);
		setTemperature(HeatAPI.AMBIENT_TEMP);
	}

	@Override
	public void handleHeat(double transfer) {
		heatToHandle += transfer;
	}

	@Override
	public void updateHeat() {
		if (Math.abs(heatToHandle) > HeatAPI.EPSILON) {
			storedHeat += heatToHandle;
			heatToHandle = 0;
		}
	}

	@Override
	public final double getHeat() {
		if (storedHeat < 0) {
			throw new IllegalStateException(String.format("Negative stored heat: %f", storedHeat));
		}
		return storedHeat;
	}

	@Override
	public final void setHeat(double heat) {
		if (getHeat() != heat) {
			storedHeat = heat;
		}
	}

	@Override
	public IHeatManifold getManifold() {
		return ownerManifold;
	}

	@Override
	public final double getCapacity() {
		return heatCapacity;
	}

	@Override
	public void setManifold(IHeatManifold heatManifold) {
		this.ownerManifold = heatManifold;
	}

	@Override
	public void setCapacity(double capacity, boolean updateHeat) {
		if (updateHeat && storedHeat != -1) {
			setHeat(storedHeat + (capacity - heatCapacity) * HeatAPI.AMBIENT_TEMP);
		}
		setHeatCapacity(capacity);
	}

	private void setHeatCapacity(double heatCapacity) {
		this.heatCapacity = HeatAPI.validateHeatCapacity(heatCapacity);
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
		this.thermals = HeatAPI.validateThermals(thermals);
	}
}
