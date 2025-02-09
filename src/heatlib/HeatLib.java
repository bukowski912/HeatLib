package heatlib;

import heatlib.api.IHeatContact;
import heatlib.api.IHeatIsland;
import heatlib.api.IHeatManifold;
import heatlib.base.BasicHeatIsland;
import heatlib.base.BasicHeatManifold;

import java.util.Objects;
import java.util.Scanner;

public class HeatLib {
	public static void main(String[] args) {
		final Scanner scanner = new Scanner(System.in);

		System.out.print("Enter heat capacity of first capacitor (in joules per kelvin): ");
		long firstCapacity = scanner.nextLong();
		scanner.nextLine();

		System.out.print("Enter heat capacity of second capacitor (in joules per kelvin): ");
		long secondCapacity = scanner.nextLong();
		scanner.nextLine();

		IHeatManifold[] manifolds = new IHeatManifold[] {
			new BasicHeatManifold(firstCapacity),
			new BasicHeatManifold(secondCapacity),
		};

		System.out.print("Enter initial temperature of first capacitor (in kelvin): ");
		manifolds[0].getCapacitor().setTemp(scanner.nextDouble());
		scanner.nextLine();

		System.out.print("Enter initial temperature of second capacitor (in kelvin): ");
		manifolds[1].getCapacitor().setTemp(scanner.nextDouble());
		scanner.nextLine();

		IHeatContact contact = manifolds[0].addAdjacent(manifolds[1]);
		Objects.requireNonNull(contact);

		IHeatIsland island = new BasicHeatIsland(manifolds);
		island.simulateAll(0);
		island.updateAll(0);

		System.out.println("Final temperature of first capacitor: " + manifolds[0].getCapacitor().getTemp());
		System.out.println("Final temperature of second capacitor: " + manifolds[1].getCapacitor().getTemp());
	}
}
