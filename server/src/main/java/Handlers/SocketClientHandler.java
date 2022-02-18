package Handlers;

import Command.Command;
import Response.Response;
import Robot.*;
import Response.*;
import Server.MainServer;
import Server.ServerCommandLine;
import World.World;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class SocketClientHandler implements Runnable{
    private final BufferedReader in;
    private final PrintStream out;
    private final String clientMachine;
    private JSONObject JsonData;
    private Response response;
    private final int index;
    private static Robot currentRobot;
    private static World world;


    public SocketClientHandler(Socket socket, World world) throws IOException {
        clientMachine = socket.getInetAddress().getHostName();
        System.out.println("Connection from " + clientMachine);
        SocketClientHandler.world = world;

        out = new PrintStream(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));
        System.out.println("Waiting for client...");

        index = ServerCommandLine.serverThreads.size();

    }

    public void run() {
        try {
            Robot robot = null;
            Command command;

            String messageFromClient;
            String jsonString; //String that was converted from a string to a JsonObject
            boolean requestUsed;  //Boolean used to determined if a request is being sent or if a name is being used



            while((messageFromClient = in.readLine()) != null && !Thread.interrupted()) {

                requestUsed = messageFromClient.contains("{");
                if (requestUsed) {
                    JsonData = new JSONObject(messageFromClient);
                    jsonString = getCommand();
                    try {
                    if (jsonString.equals("launch")) {
                        robot = Robot.create(getName(),getArgument().get(0).toString(), world);
                        world.addRobotToWorldMap(robot);
                        currentRobot = robot;

                        robot.addRobotPair();
                        out.println(new LaunchResponse(robot).executeRsponse());

                        continue;
                    }
                    jsonString = jsonString.concat(" ");
                    for (int i = 0; i < getArgument().length(); i++) {
                        jsonString = jsonString.concat(getArgument().get(i).toString());
                    }
                    jsonString = jsonString.trim();
                        command = Command.create(jsonString);


                        if (!robot.getName().equals("Init")) {
                            boolean shouldContinue = robot.handleCommand(command);
                        }
                        else{
                            throw new NullPointerException("Robot Does not exist");
                        }
                    }catch (IllegalArgumentException | NullPointerException e){
                        out.println(new ErrorResponse(e.getMessage()).executeRsponse());
                    }

//                    ServerCommandLine.robotStates.put(robot.getName(), ServerCommandLine.getState(robot));

                    System.out.println("Message \"" + messageFromClient + "\" from " + clientMachine);

                    String response = Response.setResult(jsonString, robot).toString();
                    out.println(response);

                }
                else {
                    if(MainServer.userNames.contains(messageFromClient)) {
                        out.println("Too many of you in this world");
                    }
                    else {
                        MainServer.userNames.add(messageFromClient);
                        System.out.println("name");
                        out.println("Username accepted!");
                        ServerCommandLine.robotStates.put(messageFromClient, "");
                        ServerCommandLine.robotThreadIndexes.put(messageFromClient, index);
                    }
                }
            }
        } catch(IOException ex) {
            System.out.println("Shutting down single client server");
        } finally {
            try {
                MainServer.userNames.remove(currentRobot.getName());
                world.removeRobot(currentRobot.getName());
                world.removeRobotFromWorldMap(currentRobot);
            } catch (NullPointerException e){
                out.println(new ErrorResponse("Robot does not exist").executeRsponse());
            }
            MainServer.getWorld().clearRobots();
            closeQuietly();
        }
    }

    private void closeQuietly() {
        try { in.close(); out.close();
        } catch(IOException ex) {}
    }

    private String getCommand(){return (String) this.JsonData.get("command");}

    private JSONArray getArgument(){return (JSONArray) this.JsonData.get("arguments");}

    private String getName(){return (String) this.JsonData.get("robot");}


}