package Robot;

import Robot.Robot;
import World.World;

public class SniperRobot extends Robot {
    private final int maxNumberOfShots = 1;
    private final int maxShield = 1;


    public SniperRobot(String name, World world) {
        super(name, world);
        this.setShield(maxShield);
        this.setShots(maxNumberOfShots);
        this.setMaxShield(maxShield);
        this.setMaxNumberOfShots(maxNumberOfShots);
    }

}
