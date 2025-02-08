package heatlib.api;

public interface IHeatIsland {

	boolean hasCapacitor(IHeatCapacitor heatCapacitor);

	boolean hasContact(IHeatContact heatContact);

	boolean hasManifold(IHeatManifold heatManifold);

	void registerManifold(IHeatManifold manifold);

	void simulateAll(long currentTime);

	void updateAll(long currentTime);
}
