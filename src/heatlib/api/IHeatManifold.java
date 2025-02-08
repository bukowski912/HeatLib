package heatlib.api;

import heatlib.common.Direction;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * A heat manifold stores all of the {@link IHeatContact}s for a single capacitor.
 * <p>
 * A given capacitor can only belong to (at most) a single {@link IHeatManifold} at a time.
 */
public interface IHeatManifold {

	abstract class HeatManifold implements IHeatManifold {

		private IHeatIsland island;
		protected final IHeatCapacitor capacitor;

		protected HeatManifold(IHeatCapacitor capacitor) {
			if (capacitor.getManifold() != null) {
				throw new IllegalArgumentException("Capacitor already belongs to a manifold");
			}
			this.capacitor = capacitor;
			capacitor.setManifold(this);
		}

		@Override
		public final IHeatIsland getIsland() {
			return island;
		}

		@Override
		public final void setIsland(IHeatIsland island) {
			this.island = island;
		}

		@Override
		public final IHeatCapacitor capacitor() {
			return capacitor;
		}
	}

	IHeatIsland getIsland();

	void setIsland(IHeatIsland island);

	IHeatCapacitor capacitor();

	Optional<IHeatContact> sided(Direction side);

	Optional<IHeatContact> sideless();

	/**
	 * Creates and adds a new contact to this {@link IHeatManifold}.
	 *
	 * @return The heat contact that was created and added, or {@code null}.
	 */
	boolean contact(IHeatContact contact);

	/**
	 * Establishes a new contact between this {@link IHeatManifold} and another.
	 * Automatically adds the new contact to the other manifold.
	 *
	 * @param side   Optional side upon which to establish contact.
	 * @param target Target capacitor which is opposite {@code side}.
	 * @return The heat contact that was created and added, or {@code null}.
	 */
	IHeatContact addAdjacent(IHeatManifold target, Direction side);

	IHeatContact addAdjacent(IHeatManifold target);

	Stream<IHeatContact> streamContacts();

	default Stream<IHeatCapacitor> streamLinked() {
		final IHeatCapacitor thisCapacitor = capacitor();
		return streamContacts()
				.flatMap(IHeatContact::capacitors)
				.filter(capacitor -> capacitor != thisCapacitor);
	}
}
