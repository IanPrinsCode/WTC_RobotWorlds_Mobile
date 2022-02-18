package Server;

import Display.Print;
import Services.DatabaseController;
import World.World;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class ServerCommandLine implements Runnable {
    private final static Scanner scanner = new Scanner(System.in);
    public static ArrayList<Thread> serverThreads = new ArrayList<>();
    public static HashMap<String, String> robotStates = new HashMap<>();
    public static HashMap<String, Integer> robotThreadIndexes = new HashMap<>();
    private static World world;
    public static DatabaseController controller;
    boolean running = true;

    public ServerCommandLine(World world) { ServerCommandLine.world = world;}

    @Override
    public void run() {
        String command;
        while(running) {
            command = getInput();
            if (command.equalsIgnoreCase("robots")) {
                robotsCommand();
            }
            else if (command.equalsIgnoreCase("quit")) {
                quitCommand();
            }
            else if ((command.split(" "))[0].equalsIgnoreCase("purge")) {
                purgeCommand((command.split(" "))[1]);
            }
            else if (command.equalsIgnoreCase("dump")) {
                dumpCommand();
            }
            else if (command.equalsIgnoreCase("save")) {
                saveCommand(Print.nameWorldPrompt());
            }
            else if (command.equalsIgnoreCase("load")){
                loadCommand(Print.nameWorldPrompt());
            }
            else if (command.equalsIgnoreCase("list")){
                listCommand();
            }
            else if (command.equalsIgnoreCase("delete")){
                deleteWorldCommand();
            }
        }
    }

    public static void saveCommand(String name){
        try {
            controller = new DatabaseController(world);
            controller.addWorld(name);
        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public static void loadCommand(String name){
        try {
            controller = new DatabaseController(world);
            world.setupWorld(controller.readWorldData(name),
                                controller.readConfigData(name));
            purgeCommand("all");
            world.clearRobots();
            world.doGraphicalDump();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void listCommand(){
        try {
            controller = new DatabaseController(world);
            controller.readAllWorldsName().forEach(System.out::println);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteWorldCommand() {
        System.out.println("Enter name of world to delete: ");
        String name = scanner.nextLine();
        try {
            controller = new DatabaseController(world);
            controller.deleteWorld(name);
        } catch (SQLException e) {e.printStackTrace();}
    }

    public static String getInput() {
        String input ="";
        try{
        input = scanner.nextLine();

        while (input.isBlank()) {
            input = scanner.nextLine();
        }}catch (NoSuchElementException ignored){
        }
        return input;
    }

    public void robotsCommand() {
        for(int i = 0; i < MainServer.userNames.size(); i++){
            String botName = MainServer.userNames.get(i);
            if (botName != null) {
                System.out.println(botName + ":\n" + robotStates.get(botName) + "\n");
            }
        }
    }

    public void quitCommand() {
        for (Thread t : serverThreads) {
            t.interrupt();
        }
        Print.terminated();
        running = false;
        MainServer.endServer();
    }

    public static void purgeCommand(String name) {
        for (int i = 0; i < MainServer.userNames.size(); i++) {
            String botName = MainServer.userNames.get(i);
            if (botName.equalsIgnoreCase(name)) {
                serverThreads.get(robotThreadIndexes.get(name)).interrupt();
                robotStates.remove(name);
                robotThreadIndexes.remove(name);
                MainServer.userNames.remove(name);
                Print.purged(name);
            }
        }
        if (name.equalsIgnoreCase("all") || name.isBlank()){
            for (Thread t : serverThreads) { t.interrupt();}
        }
    }

    public void dumpCommand() {
        world.doGraphicalDump();
    }

//    public static String getState(Robot robot) {
//        return "state: {\n" +
//                "position: [" + robot.getPosition().getX() + "," + robot.getPosition().getY() + "]\n" +
//                "direction: " + robot.getCurrentDirection() + "\n" +
//                "shields: " + robot.getShield() + "\n" +
//                "shots: " + robot.getShots() + "\n" +
//                "status: " + robot.getStatus() + "\n"+
//                "}";
//    }
}
