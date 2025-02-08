package heatlib.common;

public record Vec3i(int x, int y, int z) {
	public static final Vec3i ZERO = new Vec3i(0, 0, 0);
}
