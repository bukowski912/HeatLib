package heatlib.api;

public interface IHeatIsland {

	void registerManifold(IHeatManifold manifold);

	void simulateAll(long currentTime);

	void updateAll(long currentTime);
}
