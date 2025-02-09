package heatlib.api;

import heatlib.common.Direction;

/**
 * A heat capacitor is the smallest unit of heat storage in Mekanism. It stores heat energy in the form of joules,
 * and is goverened by its heat capacity and thermal properties ('thermals' for short).
 */
public interface IHeatCapacitor {

	IHeatManifold getManifold();

	void setManifold(IHeatManifold heatManifold);

	long getCapacity();

	void setCapacity(long capacity, boolean updateHeat);

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

	default double getTemp() {
		return (double) getHeat() / getCapacity();
	}

	default void setTemp(double temperature) {
		setHeat((long) (temperature * getCapacity()));
	}

	long getHeat();

	/**
	 * Overrides the amount of heat in this {@link IHeatCapacitor}.
	 *
	 * @param heat Heat to set this capacitor's storage to (may be {@code 0}).
	 * @throws UnsupportedOperationException if heat assignment is either undefined or disallowed in this context.
	 */
	default void setHeat(long heat) {
		throw new UnsupportedOperationException();
	}

	/**
	 * Handles a change of heat in this capacitor. Typically this is stored into an accumulator for {@link #updateHeat} to process.
	 *
	 * @param transfer The amount being transferred. Can be positive (for insertion) or negative (for removal).
	 * @implNote Can be called several times per tick; the values of successive calls should be accumulated.
	 */
	void handleHeat(long transfer);

	/**
	 * Convenience method for the above, but in terms of a delta temperature.
	 * @param temperature The temperature to apply to the capacitor.
	 */
	default void handleTemp(double temperature) {
		handleHeat((long) (temperature * getCapacity()));
	}

	/**
	 * Applies any heat accumulated by {@link #handleHeat(long)} to this capacitor.
	 *
	 * @implNote Should ideally only be called once per tick.
	 */
	void updateHeat();
}
