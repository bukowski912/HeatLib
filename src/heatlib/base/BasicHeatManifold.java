package heatlib.base;

import heatlib.api.IHeatContact;
import heatlib.api.IHeatManifold;
import heatlib.api.IHeatManifold.HeatManifold;
import heatlib.common.Direction;

import java.util.EnumMap;
import java.util.stream.Stream;

public class BasicHeatManifold extends HeatManifold {

	private EnumMap<Direction, IHeatContact> sided;
	private IHeatContact sideless;

	public BasicHeatManifold(long heatCapacity) {
		super(new BasicHeatCapacitor(heatCapacity));
	}

	private boolean addSidedContact(IHeatContact contact, Direction direction) {
		if (sided == null) {
			sided = new EnumMap<>(Direction.class);
		}
		return sided.putIfAbsent(direction, contact) == null;
	}

	private boolean addSidelessContact(IHeatContact contact) {
		if (sideless != null && sideless != contact) {
			return false;
		}
		sideless = contact;
		return true;
	}

	@Override
	public boolean addContact(IHeatContact contact) {
		return contact.contains(capacitor) && contact.getDirection(capacitor)
				.map(direction -> addSidedContact(contact, direction))
				.orElseGet(() -> addSidelessContact(contact));
	}

	@Override
	public IHeatContact addAdjacent(IHeatManifold target, Direction side) {
		IHeatContact contact = new BasicHeatContact(getCapacitor(), side, target.getCapacitor());
		return addContact(contact) && target.addContact(contact) ? contact : null;
	}

	@Override
	public IHeatContact addAdjacent(IHeatManifold target) {
		IHeatContact contact = new BasicHeatContact(getCapacitor(), target.getCapacitor());
		return addContact(contact) && target.addContact(contact) ? contact : null;
	}

	@Override
	public Stream<IHeatContact> streamContacts() {
		return Stream.concat(sided != null ? sided.values().stream() : Stream.empty(), Stream.ofNullable(sideless));
	}

}
