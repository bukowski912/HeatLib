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

		IHeatContact contact = manifolds[0].addAdjacent(manifolds[1], null);
		Objects.requireNonNull(contact);

		IHeatIsland island = new BasicHeatIsland(manifolds);
		System.out.println(island);
	}
}
