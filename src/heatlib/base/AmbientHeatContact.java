package heatlib.base;

import heatlib.api.HeatAPI;
import heatlib.api.IHeatCapacitor;
import heatlib.api.IHeatContact.MonadicHeatContact;
import heatlib.common.Direction;

public class AmbientHeatContact extends MonadicHeatContact {

	public AmbientHeatContact(IHeatCapacitor source, Direction side) {
		super(new Entry(source, side));
	}

	@Override
	public void simulate() {
		double invConduction = capacitor().getThermals().environmentConduction();
		double tempToTransfer = (capacitor().getTemp() - HeatAPI.AMBIENT_TEMP) / invConduction;
		capacitor().handleHeat((long) (-tempToTransfer * capacitor().getCapacity()));
	}
}
