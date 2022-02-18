package Command;

import Robot.Robot;

public class LookCommand extends Command{
    public LookCommand() {super("look");}

    @Override
    public boolean execute(Robot target) {
        target.setStatus("LOOKING");
        return true;
    }
}
