package Command;

import Command.Command;
import Robot.Robot;

public class RepairCommand extends Command {
    public RepairCommand() {
        super("repair");
    }

    @Override
    public boolean execute(Robot target) {
        target.updateShield("repair");
        System.out.println();
        target.setStatus("REPAIR");
        return true;
    }
}
