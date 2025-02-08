package heatlib.base;

import heatlib.api.IHeatContact;
import heatlib.api.IHeatManifold;
import heatlib.api.IHeatManifold.HeatManifold;
import heatlib.api.Thermals;
import heatlib.common.Direction;

import java.util.EnumMap;
import java.util.Optional;
import java.util.stream.Stream;

public class BasicHeatManifold extends HeatManifold {

	private final EnumMap<Direction, IHeatContact> sided = new EnumMap<>(Direction.class);
	private IHeatContact sideless;

	public BasicHeatManifold(long heatCapacity) {
		super(new BasicHeatCapacitor(heatCapacity));
	}

	public BasicHeatManifold(long heatCapacity, Thermals thermals) {
		super(new BasicHeatCapacitor(heatCapacity, thermals));
	}

	private boolean validate() {
//		return sideless.contains(capacitor) && sided.values().stream().allMatch(contact -> contact.contains(capacitor));
		return streamContacts().allMatch(contact -> contact.contains(capacitor));
	}

	@Override
	public Optional<IHeatContact> sided(Direction direction) {
		return Optional.ofNullable(sided.get(direction));
	}

	@Override
	public Optional<IHeatContact> sideless() {
		return Optional.ofNullable(sideless);
	}

	private boolean sidedContact(IHeatContact contact, Direction direction) {
		return sided.putIfAbsent(direction, contact) == null;
	}

	private boolean sidelessContact(IHeatContact contact) {
		if (sideless != null && sideless != contact) {
			return false;
		}
		sideless = contact;
		return true;
	}

	@Override
	public boolean contact(IHeatContact contact) {
		return contact.contains(capacitor) && contact.direction(capacitor)
				.map(direction -> sidedContact(contact, direction))
				.orElseGet(() -> sidelessContact(contact));
	}

	@Override
	public IHeatContact addAdjacent(IHeatManifold target, Direction side) {
		IHeatContact contact = new BasicHeatContact(capacitor(), side, target.capacitor());
		return contact(contact) && target.contact(contact) ? contact : null;
	}

	@Override
	public IHeatContact addAdjacent(IHeatManifold target) {
		IHeatContact contact = new BasicHeatContact(capacitor(), target.capacitor());
		return contact(contact) && target.contact(contact) ? contact : null;
	}

	@Override
	public Stream<IHeatContact> streamContacts() {
		return Stream.concat(sided.values().stream(), Stream.ofNullable(sideless));
	}

}
