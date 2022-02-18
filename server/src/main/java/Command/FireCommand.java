package Command;

import Robot.Robot;

public class FireCommand extends Command{
    public boolean hit;
    public boolean miss;
    public FireCommand() { super("fire"); }

    @Override
    public boolean execute(Robot target) {
        if(target.updateBullet()) {
            this.hit = true;
            this.miss = false;
            target.setStatus("Hit");
        }
        else {
            if(!target.getEmptyGun()) {
                this.miss = true;
                this.hit = false;
                target.setStatus("Miss");
            }
            else {
                target.setStatus("Out Of Ammo");
            }
        }
        target.updateShots("shoot");
        return true;
    }
}
