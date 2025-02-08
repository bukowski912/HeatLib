package heatlib.base;

import heatlib.api.HeatAPI;
import heatlib.api.IHeatCapacitor;
import heatlib.api.Thermals;
import heatlib.common.Direction;

import java.util.function.DoubleSupplier;

public class AmbientHeatContactBasic extends BasicMonadicHeatContact {

	private final @Nullable DoubleSupplier ambientTempSupplier;

	public AmbientHeatContactBasic(@NotNull IHeatCapacitor source, @Nullable Direction side) {
		super(source, side);
	}

	protected double getAmbientTemperature() {
		return ambientTempSupplier == null ? HeatAPI.AMBIENT_TEMP : ambientTempSupplier.getAsDouble();
	}

	@Override
	public double simulate() {
		double heatCapacity = source.getHeatCapacity();
		//transfer to air otherwise
		double invConduction = Thermals.environmentConduction(source, side);
		//transfer heat difference based on environment temperature (ambient)
		double tempToTransfer = (source.getTemperature() - HeatAPI.AMBIENT_TEMP) / invConduction;
		source.handleHeat(-tempToTransfer * heatCapacity);
		return Math.max(0, tempToTransfer);
	}
}
