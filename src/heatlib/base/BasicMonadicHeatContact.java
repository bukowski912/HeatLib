package heatlib.base;

import heatlib.api.IHeatCapacitor;
import heatlib.api.IHeatContact.MonadicHeatContact;
import heatlib.common.Direction;

public abstract class BasicMonadicHeatContact extends MonadicHeatContact {

	protected IHeatCapacitor capacitor;
	protected Direction direction;

	public BasicMonadicHeatContact(IHeatCapacitor capacitor) {
		this(capacitor, null);
	}

	public BasicMonadicHeatContact(IHeatCapacitor capacitor, Direction direction) {
		this.capacitor = capacitor;
		this.direction = direction;
	}

	@Override
	public final IHeatCapacitor getCapacitor() {
		return capacitor;
	}

	@Override
	public final Direction getDirection() {
		return direction;
	}
}
