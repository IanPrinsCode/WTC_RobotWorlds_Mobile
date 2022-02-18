package Command;

import Robot.Robot;

import static java.util.Objects.isNull;

public class ForwardCommand extends Command {

    @Override
    public boolean execute(Robot target) {
        int nrSteps = Integer.parseInt(getArgument());
        target.updatePosition(nrSteps);
        return true;
    }

    public ForwardCommand(String argument) {
        super("forward", argument);
    }
}