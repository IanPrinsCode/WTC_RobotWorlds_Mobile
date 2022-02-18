package Command;

import Robot.Robot;

public class MineCommand extends Command {
    public MineCommand() { super("mine"); }

    @Override
    public boolean execute(Robot target) {
        target.setMine();
        target.setStatus("SETMINE");
        return true;
    }
}
