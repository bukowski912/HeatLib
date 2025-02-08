package heatlib;

import heatlib.api.IHeatContact;
import heatlib.api.IHeatIsland;
import heatlib.api.IHeatManifold;
import heatlib.base.BasicHeatIsland;
import heatlib.base.BasicHeatManifold;

import java.util.Objects;

public class HeatLib {
	public static void main(String[] args) {
		IHeatManifold[] manifolds = new IHeatManifold[] {
			new BasicHeatManifold(100),
			new BasicHeatManifold(1_000),
		};

		manifolds[0].capacitor().setTemperature(30D);
		manifolds[1].capacitor().setTemperature(40D);

		IHeatContact contact = manifolds[0].addAdjacent(manifolds[1]);
		Objects.requireNonNull(contact);

		IHeatIsland island = new BasicHeatIsland(manifolds);
		island.simulateAll(0);
		island.updateAll(0);
	}
}
