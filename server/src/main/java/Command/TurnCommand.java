package Command;

import Robot.Robot;

public class TurnCommand extends Command {
    @Override
    public boolean execute(Robot target) {
        String turn_direction = getArgument();
        if (turn_direction.equalsIgnoreCase("right")) {
            target.updateDirection(true);
            target.setStatus("Turned right.");
        }else if (turn_direction.equalsIgnoreCase("left")) {
            target.updateDirection(false);
            target.setStatus("Turned left.");
        }else {
            throw new IllegalArgumentException("Could not parse arguments");
        }
        return true;
    }

    public TurnCommand(String argument) {
        super("turn", argument);
    }
}

