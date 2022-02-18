package Command;

import Command.Command;
import Robot.Robot;

public class ReloadCommand extends Command {
    public ReloadCommand() { super("reload"); }

    @Override
    public boolean execute(Robot target) {
        target.updateShots("reload");
        target.setStatus("RELOAD");
        return true;
    }
}
