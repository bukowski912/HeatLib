package heatlib.base;

import heatlib.api.IHeatCapacitor;
import heatlib.api.IHeatContact;
import heatlib.api.IHeatIsland;
import heatlib.api.IHeatManifold;

import java.util.HashSet;
import java.util.Set;

public class HeatIsland implements IHeatIsland {

	private final Set<IHeatCapacitor> capacitors = new HashSet<>();
	private final Set<IHeatContact> contacts = new HashSet<>();
	private final Set<IHeatManifold> manifolds = new HashSet<>();

	private long lastSimulationTime = -1;
	private long lastUpdateTime = -1;

	@Override
	public boolean hasHeatCapacitor(IHeatCapacitor heatCapacitor) {
		return capacitors.contains(heatCapacitor);
	}

	@Override
	public boolean hasHeatContact(IHeatContact heatContact) {
		return contacts.contains(heatContact);
	}

	@Override
	public boolean hasHeatManifold(IHeatManifold heatManifold) {
		return manifolds.contains(heatManifold);
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
}
