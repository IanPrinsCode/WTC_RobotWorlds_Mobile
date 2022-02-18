import World.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WorldTest {
    @Test
    public void testConfig() throws IOException {
        World world = new World();
        assertEquals(100, world.getWidth());
        assertEquals(100, world.getHeight());
        assertEquals(10, world.getVisibility());
        assertEquals(5, world.getReloadSpeed());
        assertEquals(5, world.getRepairSpeed());
        assertEquals(5, world.getMineSpeed());

    }

}
