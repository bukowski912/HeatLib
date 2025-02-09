package heatlib.api;

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
	 * Default inverse conduction coefficient
	 */
	public static final double DEFAULT_INVERSE_CONDUCTION = 1;
	/**
	 * Default inverse insulation coefficient
	 */
	public static final double DEFAULT_INVERSE_INSULATION = 0;

}
