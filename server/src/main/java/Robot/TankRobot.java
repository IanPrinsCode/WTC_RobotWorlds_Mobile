package Robot;

import World.World;

public class TankRobot extends Robot {
    private final int maxNumberOfShots = 1;
    private final int maxShield = 5;


    public TankRobot(String name, World world) {
        super(name,world);
        this.setShield(maxShield);
        this.setShots(maxNumberOfShots);
        this.setMaxShield(maxShield);
        this.setMaxNumberOfShots(maxNumberOfShots);
    }
}