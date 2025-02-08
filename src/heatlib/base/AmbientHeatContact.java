package heatlib.base;

import heatlib.api.HeatAPI;
import heatlib.api.IHeatCapacitor;
import heatlib.api.Thermals;
import heatlib.common.Direction;

public class AmbientHeatContact extends BasicMonadicHeatContact {

	public AmbientHeatContact(IHeatCapacitor source, Direction side) {
		super(source, side);
	}

	@Override
	public double simulate() {
		double invConduction = Thermals.environmentConduction(capacitor, direction);
		double tempToTransfer = (capacitor.getTemperature() - HeatAPI.AMBIENT_TEMP) / invConduction;
		capacitor.handleHeat(-tempToTransfer * capacitor.getCapacity());
		return Math.max(0, tempToTransfer);
	}
}
