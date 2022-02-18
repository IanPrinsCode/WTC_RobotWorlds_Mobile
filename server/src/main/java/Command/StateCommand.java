package Command;

import Command.Command;
import Robot.Robot;

public class StateCommand extends Command {

    @Override
    public boolean execute(Robot target) {
        return true;
    }

    public StateCommand() {super("state");}
}
