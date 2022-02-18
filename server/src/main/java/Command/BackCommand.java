package Command;

import Robot.Robot;

public class BackCommand extends Command {

    @Override
    public boolean execute(Robot target) {
        int nrSteps = Integer.parseInt(getArgument());
        target.updatePosition(-nrSteps);
        return true;
    }

    public BackCommand(String argument) {
        super("back", argument);
    }
}
