import org.junit.jupiter.api.Test;
import World.Direction;

import static org.junit.jupiter.api.Assertions.*;

public class DirectionTest {
    @Test
    void turnLeft() {
        Direction d = Direction.NORTH;
        d = d.left();
        assertSame(Direction.WEST, d);
    }

    @Test
    void turnRight() {
        Direction d = Direction.WEST;
        d = d.right();
        assertSame(Direction.NORTH, d);
    }
}
