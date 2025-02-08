package heatlib.base;

import heatlib.api.HeatAPI;
import heatlib.api.IHeatCapacitor;
import heatlib.api.IHeatManifold;
import heatlib.api.Thermals;
import heatlib.common.Direction;

import java.util.function.DoubleSupplier;

public class BasicHeatCapacitor implements IHeatCapacitor {

	private @Nullable IHeatManifold heatManifold;

	private double heatCapacity;
	private Thermals thermals;

	private double storedHeat;
	private double heatToHandle;

	private final @Nullable DoubleSupplier ambientTempSupplier;

	public BasicHeatCapacitor(double heatCapacity) {
		this(heatCapacity, Thermals.AMBIENT);
	}

	public BasicHeatCapacitor(double heatCapacity, Thermals thermals) {
		this(heatCapacity, thermals, null);
	}

	public BasicHeatCapacitor(double heatCapacity, Thermals thermals, @Nullable DoubleSupplier ambientTempSupplier) {
		this.heatCapacity = HeatAPI.validateHeatCapacity(heatCapacity);
		this.thermals = HeatAPI.validateThermals(thermals);
		this.storedHeat = getAmbientTemperature();
		this.ambientTempSupplier = ambientTempSupplier;
	}

	protected double getAmbientTemperature() {
		return ambientTempSupplier == null ? HeatAPI.AMBIENT_TEMP : ambientTempSupplier.getAsDouble();
	}

	@Override
	public void onContentsChanged() {
		if (listener != null) {
			listener.onContentsChanged();
		}
	}

	@Override
	public void handleHeat(double transfer) {
		heatToHandle += transfer;
	}

	@Override
	public void updateHeat() {
		if (Math.abs(heatToHandle) > HeatAPI.EPSILON) {
			storedHeat += heatToHandle;
			//notify listeners
			onContentsChanged();
			// reset our handling heat
			heatToHandle = 0;
		}
	}

	@Override
	public final double getHeat() {
		if (storedHeat <= 0) {
			throw new IllegalStateException(String.format("Negative stored heat: %f", storedHeat));
		}
		return storedHeat;
	}

	@Override
	public final void setHeat(double heat) {
		if (getHeat() != heat) {
			storedHeat = heat;
			onContentsChanged();
		}
	}

	@Override
	public @Nullable IHeatManifold getManifold() {
		return heatManifold;
	}

	@Override
	public final double getHeatCapacity() {
		return heatCapacity;
	}

	@Override
	public void setManifold(@Nullable IHeatManifold heatManifold) {
		this.heatManifold = heatManifold;
	}

	@Override
	public void setHeatCapacity(double newCapacity, boolean updateHeat) {
		if (updateHeat && storedHeat != -1) {
			setHeat(storedHeat + (newCapacity - heatCapacity) * getAmbientTemperature());
		}
		setHeatCapacity(newCapacity);
	}

	private final void setHeatCapacity(double heatCapacity) {
		this.heatCapacity = HeatAPI.validateHeatCapacity(heatCapacity);
	}

	@Override
	public @NotNull Thermals getThermals(@Nullable Direction side) {
		return getThermals();
	}

	public final @NotNull Thermals getThermals() {
		return thermals;
	}

	@Override
	public void setThermals(Thermals thermals, @Nullable Direction side) {
		setThermals(thermals);
	}

	@Override
	public final void setThermals(Thermals thermals) {
		this.thermals = HeatAPI.validateThermals(thermals);
	}
}
