package heatlib.api;

import heatlib.common.Direction;

/**
 * A heat capacitor is the smallest unit of heat storage in Mekanism. It stores heat energy in the form of joules,
 * and is goverened by its heat capacity and thermal properties ('thermals' for short).
 */
public interface IHeatCapacitor {

	IHeatManifold getManifold();

	void setManifold(IHeatManifold heatManifold);

	double getCapacity();

	void setCapacity(double capacity, boolean updateHeat);

	Thermals getThermals(Direction side);

	default Thermals getThermals() {
		return getThermals(null);
	}

	default void setThermals(Thermals thermals, Direction side) {
		throw new UnsupportedOperationException();
	}

	default void setThermals(Thermals thermals) {
		setThermals(thermals, null);
	}

	default double getTemperature() {
		return getHeat() * getCapacity();
	}

	default void setTemperature(double temperature) {
		setHeat(temperature * getCapacity());
	}

	double getHeat();

	/**
	 * Overrides the amount of heat in this {@link IHeatCapacitor}.
	 *
	 * @param heat Heat to set this capacitor's storage to (may be {@code 0}).
	 * @throws UnsupportedOperationException if heat assignment is either undefined or disallowed in this context.
	 */
	default void setHeat(double heat) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Handles a change of heat in this capacitor.
	 *
	 * @param transfer The amount being transferred. Can be positive (for insertion) or negative (for removal).
	 * @implNote Can be called several times per tick; the values of successive calls should be accumulated.
	 */
	void handleHeat(double transfer);

	/**
	 * Applies any heat accumulated by {@link #handleHeat(double)} to this capacitor.
	 *
	 * @implNote Should ideally only be called once per tick.
	 */
	void updateHeat();
}
