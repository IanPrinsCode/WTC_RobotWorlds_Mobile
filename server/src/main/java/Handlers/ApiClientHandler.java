package Handlers;

import Command.Command;
import Response.Response;
import Response.ErrorResponse;
import Response.LaunchResponse;
import Response.RobotListResponse;
import Server.ServerCommandLine;
import Robot.Robot;
import Services.DatabaseController;
import Services.Models.WorldDO;
import World.World;
import World.Position;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import io.javalin.plugin.openapi.annotations.HttpMethod;
import io.javalin.plugin.openapi.annotations.OpenApi;
import io.javalin.plugin.openapi.annotations.OpenApiContent;
import io.javalin.plugin.openapi.annotations.OpenApiRequestBody;
import org.json.JSONArray;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class ApiClientHandler {

    public static void returnWorld(Context context, World world) {
        context.json(world.toJson());
    }

    public static void getAllWorlds(Context context, World world) throws SQLException {
        DatabaseController databaseController = new DatabaseController(world);
        context.json(databaseController.readAllWorldsData());
    }

    public static void restoreWorld(Context context, World world) {
        String name = context.pathParamAsClass("name", String.class).get();
        ServerCommandLine.loadCommand(name);
        world.clearRobots();
        context.json(new WorldDO(name, world.getSize(), world.getWorldMap().toString()));
    }

    public static void saveWorld(Context context, World world) {
        String name = context.pathParamAsClass("world-name", String.class).get();
        ServerCommandLine.saveCommand(name);
        world.clearRobots();
        world.doGraphicalDump();
        returnWorld(context, world);
    }

    @OpenApi(
            summary = "Get all robots",
            method = HttpMethod.POST,
            description = "Returns all robots in the world",
            requestBody = @OpenApiRequestBody(content = {@OpenApiContent(from = Command.class)})
    )
    public static void doCommand(Context context, World world) throws IOException {
        String name = context.pathParamAsClass("name", String.class).get();
        String command = context.bodyAsClass(JsonNode.class).get("command").asText();
        try {
            if (command.equalsIgnoreCase("launch")) {
                Robot robot = Robot.create(name, getLaunchArgs(context.body()).replace("\"",""), world);
                world.addRobotToWorldMap(robot);
                robot.addRobotPair();
                context.json(new LaunchResponse(robot).executeRsponse().toString());

            }else if (!world.robotExists(name)) {
                context.json(new ErrorResponse("Robot does not exist").executeRsponse().toString());

            } else {
                Robot robot = world.getRobotByName(name);
                String commandArgs = processArgs(context.body());
                robot.handleCommand(Command.create(command+" "+commandArgs));
                context.json(Response.setResult(command+" "+commandArgs, robot).toString());
            }
            world.updateMap();
        }catch (IllegalArgumentException e){
            context.json(new ErrorResponse(e.getMessage()).executeRsponse().toString());
        }
    }

    public static void removeRobot(Context context, World world) {
        String name = context.pathParamAsClass("name", String.class).get();
        System.out.println("Quitting robot: " + name);
            if (!world.robotExists(name)) {
                context.status(HttpCode.NOT_FOUND);
                context.json(new ErrorResponse("Robot does not exist").executeRsponse().toString());

            } else {
                Robot robot = world.getRobotByName(name);
                world.removeRobotFromWorldMap(robot);
                world.removeRobot(name);
                context.status(HttpCode.OK);
                context.json(name+" Removed from World");
                System.out.println(name+" Removed from World");
            }
    }

    public static void addObstacles(Context context, World world) {
        for (JsonNode obstacle : context.bodyAsClass(JsonNode.class)) {

            Position ObsPosition = new Position(obstacle.get("x").asInt(), obstacle.get("y").asInt());
            World.addObstacle(ObsPosition);
            World.addObstacleToObstacleList(ObsPosition);
        }
        world.doGraphicalDump();
        context.status(HttpCode.OK);
    }

    public static void getAllObstacles(Context context, World world) {
        JSONArray obstacles = new JSONArray();
        for (List<Integer> obstacle : world.getAllObstacles()) {
            HashMap<String, Integer> obstacleMap = new HashMap<>();
            obstacleMap.put("x", obstacle.get(0));
            obstacleMap.put("y", obstacle.get(1));
            obstacles.put(obstacleMap);
        }
        context.status(HttpCode.OK);
        context.json(obstacles.toString());
    }

    public static void removeObstacles(Context context, World world) {
        for (JsonNode obstacle : context.bodyAsClass(JsonNode.class)) {
            World.removeObstacle(new Position(obstacle.get("x").asInt(), obstacle.get("y").asInt()));
        }
        world.doGraphicalDump();
        context.status(HttpCode.OK);
    }

    private static String processArgs(String body) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode args = mapper.readTree(body).get("arguments");

        String argsString = "";
        for (int i = 0; i < args.size(); i++) {
            argsString = argsString.concat(args.get(i).asText());
        }
        return argsString.trim();
    }

    private static String getLaunchArgs(String body) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode args = mapper.readTree(body).get("arguments");

        return args.get(0).toString();
    }

    public static void getRobots(Context context, World world) {
        JSONArray robots = new JSONArray();

        for (Robot robot : world.getRobotList()) {
            robots.put(new RobotListResponse(robot).executeRsponse());
        }

        context.status(HttpCode.OK);
        context.json(robots.toString());
    }
}
