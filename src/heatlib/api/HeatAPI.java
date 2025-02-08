package heatlib.api;

import heatlib.common.Constants;

import java.util.Objects;

public class HeatAPI {

	private HeatAPI() {
	}

	/**
	 * Default atmospheric temperature, automatically set in all heat capacitors. Heat is grounded in 0 degrees Kelvin.
	 */
	public static final double AMBIENT_TEMP = 300;
	/**
	 * The heat transfer coefficient for air
	 */
	public static final double AIR_INVERSE_COEFFICIENT = 10_000;
	/**
	 * Default heat capacity
	 */
	public static final double DEFAULT_HEAT_CAPACITY = 1;
	/**
	 * Default inverse conduction coefficient
	 */
	public static final double DEFAULT_INVERSE_CONDUCTION = 1;
	/**
	 * Default inverse insulation coefficient
	 */
	public static final double DEFAULT_INVERSE_INSULATION = 0;
	/**
	 * Represents the value at which changes to heat below this amount will not be taken into account by Mekanism.
	 *
	 * @since 10.4.0
	 */
	public static final double EPSILON = Constants.EPSILON;

	public static double validateHeatCapacity(double heatCapacity) {
		if (heatCapacity < 1) {
			throw new IllegalArgumentException("Heat capacity must be at least one");
		}
		return heatCapacity;
	}

	public static Thermals validateThermals(Thermals thermals) {
		Objects.requireNonNull(thermals);
		if (thermals.inverseConduction() < 1) {
			throw new IllegalArgumentException("Inverse conduction coefficient must be at least one");
		}
		return thermals;
	}
}
