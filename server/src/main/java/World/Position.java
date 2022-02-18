package World;

import java.util.ArrayList;
import java.util.Arrays;

public class Position {
    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Position position = (Position) o;

        if (x != position.x) return false;
        return y == position.y;
    }

    public boolean isIn(Position topLeft, Position bottomRight) {
        boolean withinTop = this.y <= topLeft.getY();
        boolean withinBottom = this.y >= bottomRight.getY();
        boolean withinLeft = this.x >= topLeft.getX();
        boolean withinRight = this.x <= bottomRight.getX();
        return withinTop && withinBottom && withinLeft && withinRight;
    }

    public static Position toPosition(String position) {
        ArrayList<String> stringPosition = new ArrayList<>(
                Arrays.asList(position.split(",")));
        return new Position(Integer.parseInt(stringPosition.get(0)), Integer.parseInt(stringPosition.get(1)));
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }
}
