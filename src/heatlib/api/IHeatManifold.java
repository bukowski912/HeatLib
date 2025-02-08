package heatlib.api;

import heatlib.common.Direction;

/**
 * A heat manifold stores all of the {@link IHeatContact}s for a single capacitor.
 * <p>
 * A given capacitor can only belong to (at most) a single {@link IHeatManifold} at a time.
 */
public interface IHeatManifold {

	@Nullable IHeatIsland getIsland();

	@NotNull IHeatCapacitor getCapacitor();

	void setCapacitor(@NotNull IHeatCapacitor heatCapacitor);

	@Nullable IHeatContact getContact(@Nullable Direction side);

	/**
	 * Creates and adds a new contact to this {@link IHeatManifold}.
	 *
	 * @param side Optional side upon which to establish contact.
	 * @return The heat contact that was created and added, or {@code null}.
	 */
	boolean addContact(@NotNull IHeatContact contact);

	/**
	 * Establishes a new contact between this {@link IHeatManifold} and another.
	 * Automatically adds the new contact to the other manifold.
	 *
	 * @param side   Optional side upon which to establish contact.
	 * @param target Target capacitor which is opposite {@code side}.
	 * @return The heat contact that was created and added, or {@code null}.
	 */
	IHeatContact addAdjacent(IHeatManifold target, @Nullable Direction side);
}
