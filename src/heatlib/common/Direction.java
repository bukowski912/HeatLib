package heatlib.common;

public enum Direction {
	DOWN("down", new Vec3i(0, -1, 0)),
	UP("up", new Vec3i(0, 1, 0)),
	NORTH("north", new Vec3i(0, 0, -1)),
	SOUTH("south", new Vec3i(0, 0, 1)),
	WEST("west", new Vec3i(-1, 0, 0)),
	EAST("east", new Vec3i(1, 0, 0));

	public final String name;
	public final Vec3i normal;

	private static final Direction[] VALUES = values();

	Direction(String name, Vec3i normal) {
		this.name = name;
		this.normal = normal;
	}

	public Direction getOpposite() {
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
