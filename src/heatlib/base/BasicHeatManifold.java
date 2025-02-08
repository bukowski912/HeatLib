package heatlib.base;

import heatlib.api.*;
import heatlib.api.IHeatManifold.HeatManifold;
import heatlib.common.Direction;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class BasicHeatManifold extends HeatManifold {

	private IHeatCapacitor capacitor;
	private final EnumMap<Direction, IHeatContact> sided = new EnumMap<>(Direction.class);
	private IHeatContact sideless = null;

	public BasicHeatManifold(int heatCapacity) {
		this(new BasicHeatCapacitor(heatCapacity));
	}

	public BasicHeatManifold(int heatCapacity, Thermals thermals) {
		this(new BasicHeatCapacitor(heatCapacity, thermals));
	}

	public BasicHeatManifold(IHeatCapacitor capacitor) {
		this.capacitor = capacitor;
	}

	private boolean areContactsValid() {
		return sideless.hasCapacitor(capacitor) && sided.values().stream().allMatch((x) -> x.hasCapacitor(capacitor));
	}

	protected boolean isValid() {
		return capacitor != null && areContactsValid();
	}

	@Override
	public IHeatCapacitor getCapacitor() {
		return Objects.requireNonNull(capacitor);
	}

	@Override
	public void setCapacitor(IHeatCapacitor capacitor) {
		if (this.capacitor != null) {
			this.capacitor.setManifold(null);
		}
		if (capacitor.getManifold() != this) {
			throw new IllegalArgumentException("New capacitor already belongs to manifold");
		}
		super.setCapacitor(this.capacitor = capacitor);
		capacitor.setManifold(this);
	}

	@Override
	public IHeatContact getContact(Direction direction) {
		return direction != null ? sided.get(direction) : sideless;
	}

	@Override
	public boolean addContact(IHeatContact contact) {
		Objects.requireNonNull(capacitor);
		if (!contact.hasCapacitor(capacitor)) {
			return false;
		}
		var direction = contact.getDirection(capacitor);
		if (direction == null) {
			if (sideless != null && sideless != contact) {
				return false;
			}
			sideless = contact;
		} else {
			var side = sided.putIfAbsent(direction, contact);
			if (side != null && side != contact) {
				return false;
			}
		}
		assert super.addContact(contact);
		return true;
	}

	@Override
	public IHeatContact addAdjacent(IHeatManifold target, Direction side) {
		IHeatContact contact = new BasicHeatContact(getCapacitor(), target.getCapacitor(), side);
		return addContact(contact) && target.addContact(contact) ? contact : null;
	}
}
