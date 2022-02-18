package Robot;

import Command.Command;
import World.Obstructions.*;
import World.*;
import Server.*;
import io.swagger.models.auth.In;

import java.util.*;


public abstract class Robot {

    private World world;

    private Position position;
    private Direction currentDirection;
    private String status;

    public String getMessage() {
        return message;
    }

    private String message;
    private final String name;

    private int shield;
    private int shots;
    private int maxNumberOfShots;
    private int maxShield;
    private boolean alive;
    private boolean emptyGun;
    private final int repairSpeed;
    private final int mineSpeed;
    private final int reloadSpeed;
    private final Random random = new Random();

    private final int visibility;

    private HashMap<Object,ArrayList<Object>> objects = new HashMap<>();

    public void setMaxShield(int maxShield) {this.maxShield = maxShield;}

    public void setMaxNumberOfShots(int maxNumberOfShots) {this.maxNumberOfShots = maxNumberOfShots;}

    public void setShield(int shield) {
        this.shield = shield;
    }

    public void setShots(int shots) {
        this.shots = shots;
    }

    public String getName() { return this.name;}

    public Robot(String name, World world) {
        this.name = name;
        this.world = world;
        this.currentDirection = Direction.NORTH;
        this.alive = true;
        this.repairSpeed = world.getRepairSpeed();
        this.visibility = world.getVisibility();
        this.mineSpeed = world.getMineSpeed();
        this.reloadSpeed = world.getReloadSpeed();
        this.status = "DONE";
        this.message = "NORMAL";
        if (World.isOccupied(0,0)){
            this.position = getRandomStartPosition();
        }
        else{
            this.position = new Position(0,0);
        }
    }

    public Position getRandomStartPosition() {
        HashMap<List<Integer>, Character> worldMap = world.getWorldMap();
        int index = random.nextInt(worldMap.keySet().size());

        List<Integer> position = (List<Integer>) worldMap.keySet().toArray()[index];
        if (worldMap.get(position) != '*')
            return getRandomStartPosition();
        return new Position(position.get(0), position.get(1));
    }

    private void moveNorth(int x, int oldY, int newY) {
        for (int y = oldY+1; y <= newY; y++) {
            switch (world.getWorldMap().get(World.makeMapKey(x, y))) {
                case 'O':
                    this.message = "Obstructed by obstacle";
                    return;
                case 'R':
                    this.message = "Obstructed by robot";
                    return;
                case 'M':
                    doSteppedOnMine(new Position(x,y));
                    return;
                case 'P':
                    this.message = "Fell in pit";
                    return;
                case '#':
                    this.message = "At the NORTH edge";
                    return;
                default:
                    this.position = new Position(x,y);
            }
        }
        this.message = "DONE";
    }

    private void moveSouth(int x, int oldY, int newY) {
        for (int y = oldY-1; y >= newY; y--) {
            switch (world.getWorldMap().get(World.makeMapKey(x, y))) {
                case 'O':
                    this.message = "Obstructed by obstacle";
                    return;
                case 'R':
                    this.message = "Obstructed by robot";
                    return;
                case 'M':
                    doSteppedOnMine(new Position(x,y));
                    return;
                case 'P':
                    this.message = "Fell in pit";
                    return;
                case '#':
                    this.message = "At the SOUTH edge";
                    return;
                default:
                    this.position = new Position(x,y);
            }
        }
        this.message = "DONE";
    }

    private void moveWest(int y, int oldX, int newX) {
        for (int x = oldX-1; x >= newX; x--) {
            switch (world.getWorldMap().get(World.makeMapKey(x, y))) {
                case 'O':
                    this.message = "Obstructed by obstacle";
                    return;
                case 'R':
                    this.message = "Obstructed by robot";
                    return;
                case 'M':
                    doSteppedOnMine(new Position(x,y));
                    return;
                case 'P':
                    this.message = "Fell in pit";
                    return;
                case '#':
                    this.message = "At the WEST edge";
                    return;
                default:
                    this.position = new Position(x,y);
            }
        }
        this.message = "DONE";
    }

    private void moveEast(int y, int oldX, int newX) {
        for (int x = oldX+1; x <= newX; x++) {
            switch (world.getWorldMap().get(World.makeMapKey(x, y))) {
                case 'O':
                    this.message = "Obstructed by obstacle";
                    return;
                case 'R':
                    this.message = "Obstructed by robot";
                    return;
                case 'M':
                    doSteppedOnMine(new Position(x,y));
                    return;
                case 'P':
                    this.message = "Fell in pit";
                    this.position = new Position(x,y);
                    return;
                case '#':
                    this.message = "At the EAST edge";
                    return;
                default:
                    this.position = new Position(x,y);
            }
        }
        this.message = "DONE";
    }

    public void updatePosition(int nrSteps) {
        int x = this.position.getX();
        int y = this.position.getY();

        if (Direction.NORTH.equals(this.currentDirection)) {
            doVerticalMove(x, y, nrSteps);
        }
        else if (Direction.SOUTH.equals(this.currentDirection)) {
            doVerticalMove(x, y, -nrSteps);
        }
        else if (Direction.WEST.equals(this.currentDirection)) {
            doHorizontalMove(x, y, -nrSteps);
        }
        else if (Direction.EAST.equals(this.currentDirection)) {
            doHorizontalMove(x, y, nrSteps);
        }
    }

    public void doVerticalMove(int x, int y, int nrSteps) {
        if (nrSteps > 0)
            moveNorth(x, y, y+nrSteps);
        else
            moveSouth(x, y, y+nrSteps);
    }

    public void doHorizontalMove(int x, int y, int nrSteps) {
        if (nrSteps > 0)
            moveEast(y, x, x+nrSteps);
        else
            moveWest(y, x, x+nrSteps);
    }

    public void updateShield(String option){

        if(option.equalsIgnoreCase("shot")) {
            shield -= 1;
        }
        if(option.equalsIgnoreCase("mine")) {
            shield -= 2;
        }
        if (shield <= 0) {
            world.removeRobot(this.getName());
            world.removeRobotFromWorldMap(this);
            status = "You died";
//            alive = false;
//            shield = 0;
        }
        if (option.equals("repair")) {
            sleep(this.repairSpeed);
            shield = maxShield;
        }

    }

    public void setMine() {
        int originalShield = this.shield;
        this.shield = 0;
        sleep(this.mineSpeed);
        Mine mine = new Mine(this.position);

        this.shield = originalShield;
        if(this.currentDirection.equals(Direction.NORTH) ||
        this.currentDirection.equals(Direction.SOUTH)) {
            this.position = new Position(position.getX(), position.getY()+1);
        }
        else {
            this.position = new Position(position.getX()-1, position.getY());
        }
        world.addMine(mine.getTopLeftPosition());
        world.addMineToList(mine);

    }

    public void updateShots(String option) {
        if (!emptyGun && option.equals("shoot")) {
            this.shots -= 1;
            if (shots == 0) {
                emptyGun = true;
            }
        }
        if(option.equals("reload")) {
            sleep(this.reloadSpeed);
            this.shots = maxNumberOfShots;
            this.emptyGun = false;
        }
    }

    public void updateDirection(boolean turnRight) {
        if (turnRight) {
            this.currentDirection = this.currentDirection.right();
        }
        else {
            this.currentDirection = this.currentDirection.left();
        }
    }

    public Direction getCurrentDirection() {
        return this.currentDirection;
    }

    public Position getPosition() {
        return this.position;
    }

    public boolean isNewPositionAllowed(Position position) {
        return world.getWorldMap().containsKey(World.makeMapKey(position));
    }

    public boolean handleCommand(Command command) {
        return command.execute(this);
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static Robot create(String name, String type, World world) {
        if (world.robotExists(name)){
            throw new IllegalArgumentException("Too many of you in this world");
        }
        else if (world.worldIsFull()){
            throw new IllegalArgumentException("No more space in this world");
        }
        switch (type) {
            case "test":
            case "robot":
            case "shooter":
                return new StandardRobot(name, world);
            case "sniper":
                return new SniperRobot(name, world);
            case "fighter":
                return new FighterRobot(name, world);
            case "tank":
                return new TankRobot(name, world);
            default:
                throw new IllegalArgumentException("Unsupported type: "+type );
        }
    }

    public String getStatus() { return status; }

    public void addRobotPair() {
        world.addRobot(this);
    }

    public int getShield() {
        return shield;
    }

    private void sleep(int seconds) {
        try
        {
            long millisecs = seconds * 1000L;
            Thread.sleep(millisecs);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }

    public boolean blocksPosition(Position position) {
        Boolean checkY = this.position.getY() == position.getY();
        Boolean checkX = this.position.getX() == position.getX();

        return checkX && checkY;
    }

    public void doSteppedOnMine(Position position) {
        this.message = "Mine";
        world.removeMine(position);
        this.position = position;
        updateShield("MINE");
    }

    public HashMap<Object, ArrayList<Object>> getObjects() { return this.objects; }

    public ArrayList<ObjectData> lookAround() {
       ArrayList<ObjectData> objects = new ArrayList<>();
       objects.addAll(world.lookNorth(this));
       objects.addAll(world.lookSouth(this));
       objects.addAll(world.lookEast(this));
       objects.addAll(world.lookWest(this));

       return objects;
    }

    public boolean updateBullet() {
//        int newX = this.position.getX();
//        int newY = this.position.getY();
//        int distance = 0;
//
//        if (emptyGun)
//            return false;
//
//        if (maxNumberOfShots == 5) {distance = 1;}
//        if (maxNumberOfShots == 4) {distance = 2;}
//        if (maxNumberOfShots == 3) {distance = 3;}
//        if (maxNumberOfShots == 2) {distance = 4;}
//        if (maxNumberOfShots == 1) {distance = 5;}
//
//        switch (this.currentDirection){
//            case NORTH:
//                doShotNorth();
//            case EAST:
//                doShotEast();
//            case WEST:
//                doShotWest();
//            case SOUTH:
//                doShotSouth();
//        }
//
////        for (Robot robot : world.getRobotList()) {
////            if (robot.blocksPath(this.getPosition(), newPosition)) {
////                robot.updateShield("shot");
////                ServerCommandLine.robotStates.put(robot.getName(), ServerCommandLine.getState(robot));
////                return true;
////            }
////        }
        return false;
    }

    private void doShotNorth(){
    }

    private void doShotSouth(){
    }

    private void doShotEast(){
    }

    private void doShotWest(){
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getShots() {
        return shots;
    }

    public Boolean getEmptyGun() { return emptyGun; }

    public boolean isAlive() {
        return this.alive;
    }

    public World getWorld() {return this.world;}
}

