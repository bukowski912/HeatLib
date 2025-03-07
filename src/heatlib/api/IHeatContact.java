package heatlib.api;

import heatlib.common.Direction;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public interface IHeatContact {

	record Entry(IHeatCapacitor capacitor, Optional<Direction> direction) {
		public Entry(IHeatCapacitor capacitor) {
			this(capacitor, Optional.empty());
		}

		public Entry(IHeatCapacitor capacitor, Direction direction) {
			this(capacitor, Optional.of(direction));
		}

		public Thermals thermals() {
			return direction.map(capacitor::getThermals).orElseGet(capacitor::getThermals);
		}

		public boolean has(IHeatCapacitor capacitor) {
			return Objects.equals(capacitor(), capacitor);
		}
	}

	abstract class MonadicHeatContact implements IHeatContact {

		protected final Entry entry;

		protected MonadicHeatContact(Entry entry) {
			this.entry = entry;
		}

		public final IHeatCapacitor capacitor() {
			return entry.capacitor();
		}

		@Override
		public final Stream<Entry> streamEntries() {
			return Stream.of(entry);
		}
	}

	abstract class IDyadicHeatContact implements IHeatContact {

		protected final Entry firstEntry, secondEntry;

		protected IDyadicHeatContact(Entry firstEntry, Entry secondEntry) {
			this.firstEntry = firstEntry;
			this.secondEntry = secondEntry;
		}

		public final IHeatCapacitor firstCapacitor() {
			return firstEntry.capacitor();
		}

		public final IHeatCapacitor secondCapacitor() {
			return secondEntry.capacitor();
		}

		public final Thermals firstThermals() {
			return firstEntry.thermals();
		}

		public final Thermals secondThermals() {
			return secondEntry.thermals();
		}

		@Override
		public final Stream<Entry> streamEntries() {
			return Stream.of(firstEntry, secondEntry);
		}
	}

	Stream<Entry> streamEntries();

	default Stream<IHeatCapacitor> streamCapacitors() {
		return streamEntries().map(Entry::capacitor);
	}

	default boolean contains(IHeatCapacitor capacitor) {
		return streamCapacitors().anyMatch(capacitor::equals);
	}

	default Optional<Direction> getDirection(IHeatCapacitor capacitor) {
		return streamEntries().filter(entry -> entry.has(capacitor)).findFirst().flatMap(Entry::direction);
	}

	void simulate();
}
