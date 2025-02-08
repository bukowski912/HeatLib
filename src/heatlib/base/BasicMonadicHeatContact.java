package heatlib.base;

import heatlib.api.IHeatCapacitor;
import heatlib.api.IHeatContact.MonadicHeatContact;
import heatlib.common.Direction;

public abstract class BasicMonadicHeatContact extends MonadicHeatContact {

	protected @NotNull IHeatCapacitor capacitor;
	protected @Nullable Direction direction;

	public BasicMonadicHeatContact(@NotNull IHeatCapacitor capacitor) {
		this(capacitor, null);
	}

	public BasicMonadicHeatContact(@NotNull IHeatCapacitor capacitor, @Nullable Direction direction) {
		this.capacitor = capacitor;
		this.direction = direction;
	}

	@Override
	public final @NotNull IHeatCapacitor getCapacitor() {
		return capacitor;
	}

	@Override
	public final @Nullable Direction getDirection() {
		return direction;
	}
}
