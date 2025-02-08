package heatlib.common;

public enum Direction {
	DOWN, UP, NORTH, SOUTH, WEST, EAST;

	public Direction opposite() {
		return switch (this) {
			case DOWN -> UP;
			case UP -> DOWN;
			case NORTH -> SOUTH;
			case SOUTH -> NORTH;
			case WEST -> EAST;
			case EAST -> WEST;
		};
	}
}
