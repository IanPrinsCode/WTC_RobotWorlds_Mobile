package World;

public enum Direction {
    NORTH, EAST, SOUTH, WEST;

    static
    private final Direction[] values = values();

    public Direction left() {
        return values[Math.floorMod(ordinal() - 1, values.length)];
    }

    public Direction right() {
        return values[Math.floorMod(ordinal() + 1, values.length)];
    }
}
