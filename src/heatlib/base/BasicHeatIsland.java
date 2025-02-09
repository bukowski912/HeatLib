package heatlib.base;

import heatlib.api.IHeatCapacitor;
import heatlib.api.IHeatContact;
import heatlib.api.IHeatIsland;
import heatlib.api.IHeatManifold;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BasicHeatIsland implements IHeatIsland {

	private final Set<IHeatManifold> manifolds = new HashSet<>();
	private final Set<IHeatCapacitor> capacitors = new HashSet<>();
	private final Set<IHeatContact> contacts = new HashSet<>();

	private long lastSimulationTime = -1;
	private long lastUpdateTime = -1;

	public BasicHeatIsland(IHeatManifold... manifolds) {
		for (IHeatManifold manifold : manifolds) {
			registerManifold(manifold);
		}
	}

	@Override
	public void registerManifold(IHeatManifold manifold) {
		if (manifolds.add(manifold)) {
			capacitors.add(manifold.getCapacitor());
			manifold.streamLinked().forEachOrdered(capacitors::add);
			manifold.streamContacts().forEachOrdered(contacts::add);
		}
	}

	@Override
	public void simulateAll(long currentTime) {
		if (currentTime <= lastSimulationTime) {
			return;
		}
		for (IHeatContact heatContact : contacts) {
			heatContact.simulate();
		}
		lastSimulationTime = currentTime;
	}

	@Override
	public void updateAll(long currentTime) {
		if (currentTime <= lastUpdateTime) {
			return;
		}
		for (IHeatCapacitor capacitor : capacitors) {
			capacitor.updateHeat();
		}
		lastUpdateTime = currentTime;
	}

	@Override
	public String toString() {
		return "Manifolds: " +
				Arrays.toString(manifolds.toArray()) +
				'\n' +
				"Capacitors: " +
				Arrays.toString(capacitors.toArray()) +
				'\n' +
				"Contacts: " +
				Arrays.toString(contacts.toArray());
	}
}
