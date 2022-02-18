package World;

import Robot.Robot;
import Server.ServerConfig;
import World.Obstructions.Mine;
import World.Obstructions.ObjectData;
import World.Obstructions.Obstacle;
import World.Obstructions.Pit;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.*;

public class World {
    private int width;
    private int height;
    private int visibility;
    private int reloadSpeed; // Reload speed in seconds
    private int repairSpeed; // Repair speed in seconds
    private int mineSpeed;   // Speed of which a mine is placed in seconds

    private static ArrayList<Obstacle> obstacleList;
    private static ArrayList<Pit> pitList;
    private static ArrayList<Mine> mineList;
    private static int actualSize;

    // Mapping values:
    // Open = '*' ,  Edge = '#' , Obstacle = 'O' , Pit = 'P' , Mine = 'M' , Robot = 'R'
    private static HashMap<List<Integer>, Character> worldMap;
    private static ArrayList<Robot> robots;

    public World(ServerConfig config) throws  IOException {
        obstacleList = new ArrayList<>();
        pitList = new ArrayList<>();
        mineList = new ArrayList<>();
        robots = new ArrayList<>();
        setConfig(config);
        setupWorld(config);
    }

    public World() {
        obstacleList = new ArrayList<>();
        pitList = new ArrayList<>();
        mineList = new ArrayList<>();
        robots = new ArrayList<>();
        this.width = 100;
        this.height = 100;
        this.visibility = 10;
        this.reloadSpeed = 5;
        this.repairSpeed = 5;
        this.mineSpeed = 5;
        worldMap = createEmptyMap();
    }

    private void setConfig(ServerConfig config) {
        this.width = config.getSize();
        this.height = config.getSize();
        this.visibility = config.getVisibility();
        this.reloadSpeed = config.getReload();
        this.repairSpeed = config.getRepair();
        this.mineSpeed = config.getMine();
        actualSize = config.getSize();
        worldMap = createEmptyMap();
    }

    public static List<Integer> makeMapKey(int x, int y) {
        List<Integer> key = new ArrayList<>();
        key.add(0, x);
        key.add(1, y);
        return key;
    }

    public static List<Integer> makeMapKey(Position position) {
        List<Integer> key = new ArrayList<>();
        key.add(0, position.getX());
        key.add(1, position.getY());
        return key;
    }

    private void updateActualSize(){
        if (height == 1){
            actualSize = 1;
        }
        else if (height%2 == 0) {
            actualSize = height;
        } else {
            actualSize = height - 1;
        }
    }

    public HashMap<List<Integer>, Character> createEmptyMap() {
        HashMap<List<Integer>, Character> worldMap = new HashMap<>();

        updateActualSize();

        for (int y = (actualSize/2) + 1; y >= -(actualSize/2) - 1; y--) {
            for (int x = (actualSize/2) + 1; x >= -(actualSize/2) - 1; x--) {
                List<Integer> position = makeMapKey(x, y);
                if ((y==(actualSize/2)+1) || (y==-(actualSize/2)-1) || (x==(actualSize/2)+1) || (x==-(actualSize/2)-1))
                    worldMap.put(position, '#');
                else
                    worldMap.put(position, '*');
            }
        }
        return worldMap;
    }

    public void doGraphicalDump() {
        List<Integer> currentPosition = new ArrayList<>(2);

        for (int j = (actualSize/2) + 1; j >= -(actualSize/2) - 1; j--) {
            for (int i = -(actualSize/2) - 1; i <= (actualSize/2) + 1; i++) {
                currentPosition.clear();
                currentPosition.add(i);
                currentPosition.add(j);
                if (worldMap.containsKey(currentPosition))
                    System.out.print(worldMap.get(currentPosition));
            }
            System.out.println();
        }
        System.out.println();
    }

    public void addRobotToWorldMap(Robot robot) {
        worldMap.put(makeMapKey(robot.getPosition()), 'R');
    }

    public static void addObstacle(Position position) {
        List<Integer> key = makeMapKey(position);
        if (worldMap.get(key)=='*')
            worldMap.put(key, 'O');
    }

    public static void addObstacleToObstacleList(Position position) {
        Obstacle obstacle = new Obstacle(position);
        obstacleList.add(obstacle);
    }

    public static void removeObstacle(Position position) {
        List<Integer> key = makeMapKey(position);
        if (worldMap.get(key)=='O')
            worldMap.put(key, '*');
            removeObstacleFromList(position);
    }

    private static void removeObstacleFromList(Position position) {
        for (Obstacle obstacle : obstacleList) {
            if (obstacle.getX() == position.getX() && obstacle.getY() == position.getY()) {
                obstacleList.remove(obstacle);
                break;
            }
        }
    }

    public void addMine(Position position) {
        List<Integer> key = makeMapKey(position);
        if (worldMap.get(key)=='*')
            worldMap.put(key, 'M');
    }

    public void addMineToList(Mine mine) {
        mineList.add(mine);
    }

    public void removeMine(Position position) {
        List<Integer> key = makeMapKey(position);
        if (worldMap.get(key)=='M')
            worldMap.put(key, '*');
            removeMineFromMineList(position);
    }

    private static void removeMineFromMineList(Position position) {
        for (Mine mine : mineList) {
            if (mine.getX() == position.getX() && mine.getY() == position.getY()) {
                mineList.remove(mine);
                break;
            }
        }
    }

    public static Boolean isOccupied(int x, int y) {
        List<Integer> coordinates = makeMapKey(x, y);
        return worldMap.get(coordinates) != '*';
    }

    public void clearRobots() {
        for (Robot robot :robots) {
            removeRobotFromWorldMap(robot);
        }
        robots.clear();
    }

    public void setObstructionsEmpty() {
        obstacleList = new ArrayList<>();
        pitList = new ArrayList<>();
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getReloadSpeed() {
        return reloadSpeed;
    }

    public int getRepairSpeed() {
        return repairSpeed;
    }

    public int getVisibility() {
        return visibility;
    }

    public int getMineSpeed() { return mineSpeed; }

    public ArrayList<Mine> getMineList() {
        return mineList;
    }

    public HashMap<List<Integer>, Character> getWorldMap() {
        return worldMap;
    }

    public void addRobot(Robot robot) {
        robots.add(robot);
    }

    public void removeRobot(String robotName) {
        robots.removeIf(robot -> Objects.equals(robot.getName(), robotName));
    }

    public void removeRobotFromWorldMap(Robot robot) {
        worldMap.put(makeMapKey(robot.getPosition()),'*');
    }
    
    // ACTUAL METHOD TO BE USED WHEN WORLD IS WORKING PROPERLY
    public Boolean worldIsFull() {
        return !worldMap.containsValue('*');
    }

    public ArrayList<Robot> getRobotList() {
        return robots;}

    public Boolean robotExists(String robotName) {
        for (Robot robot : robots) {
            if (Objects.equals(robot.getName(), robotName)){
                return true;
            }
        }
        return false;
    }

    public Robot getRobotByName(String robotName) {
        return robots.stream().filter(c ->
                Optional.ofNullable(c.getName()).filter(name ->
                        name.equalsIgnoreCase(robotName)).isPresent()).iterator().next();
    }

    public ArrayList<ObjectData> lookNorth(Robot robot) {
        ArrayList<ObjectData> objects = new ArrayList<>();
        Direction direction = Direction.NORTH;
        int y = robot.getPosition().getY();
        int x = robot.getPosition().getX();

        for (int i = y+1; i < y+visibility+1; i++){
            if (isOccupied(x,i)){
                Position position = new Position(robot.getPosition().getX(), i);
                int distance = i-robot.getPosition().getY();
                objects.add(new ObjectData(
                        position, distance, direction, checkObjectType(worldMap.get(makeMapKey(position)))));
                if (checkObjectType(worldMap.get(makeMapKey(position))).equals("EDGE")){
                    break;
                }
            }
        }
        return objects;
    }

    public ArrayList<List<Integer>> getAllObstacles() {
        ArrayList<List<Integer>> obstacles = new ArrayList<>();
        for (Map.Entry<List<Integer>, Character> entry : worldMap.entrySet()) {
            if (entry.getValue() == 'O') {
                obstacles.add(entry.getKey());
            }
        }
        return obstacles;
    }

    public ArrayList<ObjectData> lookSouth(Robot robot) {
        ArrayList<ObjectData> objects = new ArrayList<>();
        Direction direction = Direction.SOUTH;
        int y = robot.getPosition().getY();
        int x = robot.getPosition().getX();

        for (int i = y-1; i > y-visibility-1; i--){
            if (isOccupied(x,i)){
                Position position = new Position(x, i);
                int distance = y -i;
                objects.add(new ObjectData(
                        position, distance, direction, checkObjectType(worldMap.get(makeMapKey(position)))));
                if (checkObjectType(worldMap.get(makeMapKey(position))).equals("EDGE")) {
                    break;
                }
            }
        }
        return objects;
    }

    public ArrayList<ObjectData> lookEast(Robot robot) {
        ArrayList<ObjectData> objects = new ArrayList<>();
        Direction direction = Direction.EAST;
        int y = robot.getPosition().getY();
        int x = robot.getPosition().getX();

        for (int i = x+1; i < y+visibility+1; i++){
            if (isOccupied(i,y)){
                Position position = new Position(i, y);
                int distance = i-x;
                objects.add(new ObjectData(
                        position, distance, direction, checkObjectType(worldMap.get(makeMapKey(position)))));
                if (checkObjectType(worldMap.get(makeMapKey(position))).equals("EDGE")) {
                    break;
                }
            }
        }
        return objects;
    }

    public ArrayList<ObjectData> lookWest(Robot robot) {
        ArrayList<ObjectData> objects = new ArrayList<>();
        Direction direction = Direction.WEST;
        int y = robot.getPosition().getY();
        int x = robot.getPosition().getX();

        for (int i = x-1; i > x-visibility-1; i--){
            if (isOccupied(i,y)){
                Position position = new Position(i, y);
                int distance = x-i;
                objects.add(new ObjectData(
                        position, distance, direction, checkObjectType(worldMap.get(makeMapKey(position)))));
                if (checkObjectType(worldMap.get(makeMapKey(position))).equals("EDGE")) {
                    break;
                }
            }
        }
        return objects;
    }

    private String checkObjectType(Character value) {
        if (value.equals('R')) {
            return "ROBOT";
        } else if (value.equals('O')) {
            return "OBSTACLE";
        } else if (value.equals('P')) {
            return "PIT";
        } else if (value.equals('M')) {
            return "MINE";
        }else {
            return "EDGE";
        }
    }

    private void setupWorld(ServerConfig config){
        String obstaclePosition = config.getObstacles();
        if (!obstaclePosition.equalsIgnoreCase("none")){
            Position position = Position.toPosition(config.getObstacles());
            addObstacle(position);
            addObstacleToObstacleList(position);
        }
        doGraphicalDump();
    }

    public void setupWorld(HashMap<List<Integer>, Character> map, Integer size){
        worldMap = map;
        height = size;
        width = size;
        updateActualSize();
    }

    public int getSize() { return actualSize;}

    public void updateMap(){
        worldMap = createEmptyMap();
        for (Robot robot : robots) {
            addRobotToWorldMap(robot);
        }
        for (Obstacle obstacle : obstacleList) {
            addObstacle(obstacle.getBottomLeftPosition());
        }
        for (Mine mine : mineList) {
            addMine(mine.getTopLeftPosition());
        }
    }

    public JsonNode toJson(){
        updateActualSize();
        Gson gson = new Gson();
        String json = gson.toJson(worldMap);
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode root = mapper.createObjectNode();

        root.put("name", "current");
        root.put("size", actualSize);
        root.put("world", json);
        return root;
    }


}
