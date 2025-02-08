package heatlib.api;

import heatlib.api.IHeatContact.IDyadicHeatContact;
import heatlib.common.Direction;

public record Thermals(double inverseConduction, double inverseInsulation) {

	public static Thermals AMBIENT = new Thermals();

	public Thermals() {
		this(HeatAPI.DEFAULT_INVERSE_CONDUCTION, HeatAPI.DEFAULT_INVERSE_INSULATION);
	}

	public double environmentConduction() {
		return HeatAPI.AIR_INVERSE_COEFFICIENT + inverseInsulation() + inverseConduction();
	}

	public static double adjacentConduction(Thermals first, Thermals second) {
		return first.inverseConduction() + second.inverseConduction();
	}
}
