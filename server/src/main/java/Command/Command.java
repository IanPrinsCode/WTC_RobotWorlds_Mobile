package Command;

import Robot.Robot;

public abstract class Command {
    public static boolean back;
    public static boolean forward;
    public static boolean mine;
    public static boolean repair;
    private final String name;
    private String argument;


    public abstract boolean execute(Robot target);

    public Command(String name){
        this.name = name.trim().toLowerCase();
        this.argument = "";
    }

    public Command(String name, String argument) {
        this(name);
        this.argument = argument.trim();
    }

    public String getName() {                                                                           //<2>
        return name;
    }


    public String getArgument() {
        return this.argument;
    }


    public static Command create(String instruction) {
        String[] args = instruction.toLowerCase().trim().split(" ");
        try {
            switch (args[0]) {
                case "forward":
                    return new ForwardCommand(args[1]);
                case "back":
                    return new BackCommand(args[1]);
                case "turn":
                    return  new TurnCommand(args[1]);
                case "repair":
                    return new RepairCommand();
                case "reload":
                    return new ReloadCommand();
                case "mine":
                    return new MineCommand();
                case "fire":
                    return new FireCommand();
                case "look":
                    return new LookCommand();
                case "state":
                    return new StateCommand();
                default:
                    throw new IllegalArgumentException("Unsupported command: " + instruction);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Could not parse arguments");
        }
    }
}
