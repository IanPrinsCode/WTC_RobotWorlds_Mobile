package Robot;

import World.World;

public class StandardRobot extends Robot {
    private final int maxNumberOfShots = 3;
    private final int maxShield = 3;

    public StandardRobot(String name, World world) {
        super(name,world);
        this.setShield(maxShield);
        this.setShots(maxNumberOfShots);
        this.setMaxShield(maxShield);
        this.setMaxNumberOfShots(maxNumberOfShots);
    }
}
