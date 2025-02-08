package heatlib.api;

import heatlib.common.Direction;

import java.util.HashSet;
import java.util.Set;

/**
 * A heat manifold stores all of the {@link IHeatContact}s for a single capacitor.
 * <p>
 * A given capacitor can only belong to (at most) a single {@link IHeatManifold} at a time.
 */
public interface IHeatManifold {

	abstract class HeatManifold implements IHeatManifold {

		private IHeatIsland island = null;
		private final Set<IHeatCapacitor> linked = new HashSet<>();
		private final Set<IHeatContact> contacts = new HashSet<>();

		@Override
		public void setCapacitor(IHeatCapacitor capacitor) {
			linked.add(capacitor);
		}

		@Override
		public boolean addContact(IHeatContact contact) {
			if (!contacts.add(contact)) {
				return false;
			}
			linked.addAll(contact.getCapacitorSet());
			return true;
		}

		// Tracking methods
		@Override
		public final IHeatIsland getIsland() {
			return island;
		}

		@Override
		public final void setIsland(IHeatIsland island) {
			this.island = island;
		}

		@Override
		public final Set<IHeatCapacitor> getLinkedSet() {
			return linked;
		}

		@Override
		public final Set<IHeatContact> getContactSet() {
			return contacts;
		}
	}

	IHeatCapacitor getCapacitor();

	void setCapacitor(IHeatCapacitor heatCapacitor);

	IHeatContact getContact(Direction side);

	/**
	 * Creates and adds a new contact to this {@link IHeatManifold}.
	 *
	 * @return The heat contact that was created and added, or {@code null}.
	 */
	boolean addContact(IHeatContact contact);

	/**
	 * Establishes a new contact between this {@link IHeatManifold} and another.
	 * Automatically adds the new contact to the other manifold.
	 *
	 * @param side   Optional side upon which to establish contact.
	 * @param target Target capacitor which is opposite {@code side}.
	 * @return The heat contact that was created and added, or {@code null}.
	 */
	IHeatContact addAdjacent(IHeatManifold target, Direction side);

	// Tracking methods
	IHeatIsland getIsland();
	void setIsland(IHeatIsland island);
	Set<IHeatCapacitor> getLinkedSet();
	Set<IHeatContact> getContactSet();
}
