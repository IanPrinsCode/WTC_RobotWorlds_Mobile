package Robot;

import World.World;

public class FighterRobot extends Robot {
    private final int maxNumberOfShots = 5;
    private final int maxShield = 2;


    public FighterRobot(String name, World world) {
        super(name,world);
        this.setShield(maxShield);
        this.setShots(maxNumberOfShots);
        this.setMaxShield(maxShield);
        this.setMaxNumberOfShots(maxNumberOfShots);
    }
}