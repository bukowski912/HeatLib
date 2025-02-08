package heatlib.api;

import heatlib.common.Direction;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public abstract class IHeatContact {

	private Set<IHeatCapacitor> capacitorSet = null;
	private Map<IHeatCapacitor, Direction> directionMap = null;

	protected abstract Set<IHeatCapacitor> createCapacitorSet();
	protected abstract Map<IHeatCapacitor, Direction> createDirectionMap();

	@FunctionalInterface
	interface IMonadicHeatContactFactory<T extends MonadicHeatContact> {

		T create(IHeatCapacitor capacitor, Direction side);
	}

	@FunctionalInterface
	interface IDyadicHeatContactFactory<T extends IDyadicHeatContact> {

		T create(IHeatCapacitor firstCapacitor, IHeatCapacitor secondCapacitor, Direction side);
	}

	public abstract static class MonadicHeatContact extends IHeatContact {

		@Override
		public final Set<IHeatCapacitor> createCapacitorSet() {
			return Set.of(getCapacitor());
		}

		@Override
		public final Map<IHeatCapacitor, Direction> createDirectionMap() {
			Direction direction = getDirection();
			if (direction != null) {
				return Map.of(getCapacitor(), direction);
			}
			return Map.of();
		}

		public abstract IHeatCapacitor getCapacitor();

		@Override
		public final Set<Thermals> getThermalsSet() {
			return Set.of(getThermals());
		}

		public Thermals getThermals() {
			return getCapacitor().getThermals(getDirection());
		}

		public abstract Direction getDirection();
	}

	public abstract static class IDyadicHeatContact extends IHeatContact {

		@Override
		public final Set<IHeatCapacitor> createCapacitorSet() {
			return Set.of(getFirstCapacitor(), getSecondCapacitor());
		}

		@Override
		public final Map<IHeatCapacitor, Direction> createDirectionMap() {
			final Direction first = getFirstDirection(), second = getSecondDirection();
			if (first != null && second != null) {
				return Map.of(getFirstCapacitor(), first, getSecondCapacitor(), second);
			}
			if (first != null) {
				return Map.of(getFirstCapacitor(), first);
			}
			if (second != null) {
				return Map.of(getSecondCapacitor(), second);
			}
			return Map.of();
		}

		public abstract IHeatCapacitor getFirstCapacitor();

		public abstract IHeatCapacitor getSecondCapacitor();

		@Override
		public final Set<Thermals> getThermalsSet() {
			return Set.of(getFirstThermals(), getSecondThermals());
		}

		public Thermals getFirstThermals() {
			return getFirstCapacitor().getThermals(getFirstDirection());
		}

		public Thermals getSecondThermals() {
			return getSecondCapacitor().getThermals(getSecondDirection());
		}

		public abstract Direction getFirstDirection();

		public Direction getSecondDirection() {
			final Direction first = getFirstDirection();
			return first != null ? first.getOpposite() : null;
		}
	}

	public final Set<IHeatCapacitor> getCapacitorSet() {
		if (capacitorSet == null) {
			capacitorSet = Objects.requireNonNull(createCapacitorSet());
		}
		return capacitorSet;
	}

	public boolean hasCapacitor(IHeatCapacitor capacitor) {
		return getCapacitorSet().contains(capacitor);
	}

	public final Map<IHeatCapacitor, Direction> getDirectionMap() {
		if (directionMap == null) {
			directionMap = Objects.requireNonNull(createDirectionMap());
		}
		return directionMap;
	}

	public Direction getDirection(IHeatCapacitor capacitor) {
		return getDirectionMap().get(capacitor);
	}

	public abstract Set<Thermals> getThermalsSet();

	public abstract double simulate();
}
