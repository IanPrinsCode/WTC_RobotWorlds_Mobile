package Services;

import World.World;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.sql.*;
import java.util.*;

import Services.Models.WorldDO;
import Services.Models.WorldDAI;
import net.lemnik.eodsql.EoDException;
import net.lemnik.eodsql.QueryTool;

public class DatabaseController
{
    public static final String DISK_DB_URL = "jdbc:sqlite:";

    public final String DB_NAME = "RobotsWorld.db";

    private HashMap<List<Integer>, Character> currentMap;

    private final World world;

    private String dbUrl;

    public DatabaseController(World world) throws SQLException {
        dbUrl = DISK_DB_URL + DB_NAME;
        this.world = world;
        currentMap = world.getWorldMap();
    }

    public Connection getConnection() throws SQLException {
        DriverManager.registerDriver(new org.sqlite.JDBC());
            return DriverManager.getConnection(dbUrl);
    }

    public String toJsonString(HashMap<List<Integer>, Character> worldMap) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(worldMap);
    }

    public HashMap<List<Integer>, Character> fromJsonString(String jsonString) throws IOException {
        HashMap map = new ObjectMapper().readValue(jsonString, HashMap.class);
        HashMap<List<Integer>, Character> finalMap = new HashMap<>();
        for (Object key:map.keySet()) {
            List<Integer> newKey = new ArrayList<>();
            List<String> list = Arrays.asList(key.toString().
                    replace("]","").replace("[","").
                    replace(" ","").split(","));
            newKey.add(Integer.parseInt(list.get(0)));
            newKey.add(Integer.parseInt(list.get(1)));
            finalMap.put(newKey, map.get(key.toString()).toString().charAt(0));
        }
        return finalMap;
    }


    //##################
    //CRUD
    //##################


    public void createWorldTable() throws SQLException {
        final WorldDAI worldQuery = QueryTool.getQuery(getConnection(), WorldDAI.class);
        try {
            worldQuery.createWorldTable();
        }catch (EoDException e){
            System.out.println("Worlds table could not be created.");
        }
    }

    public List<String> readAllWorldsName() throws SQLException {
        List<WorldDO> worldsData = readAllWorldsData();
        List<String> worldNames= new ArrayList<>();
        for (WorldDO world:worldsData) {
            worldNames.add(world.getName());
        };
        return worldNames;
    }

    public List<WorldDO> readAllWorldsData() throws SQLException {
        final WorldDAI worldQuery = QueryTool.getQuery(getConnection(), WorldDAI.class);
        List<WorldDO> worldNames= new ArrayList<>();
        try {
            worldNames = worldQuery.getAllWorldsDO();
            getConnection().close();
        } catch (EoDException e) {
            System.out.println("Could not find any worlds");
        }
        return worldNames;
    }


    public void addWorld(String name) throws SQLException, JsonProcessingException {
        final WorldDAI worldQuery = QueryTool.getQuery(getConnection(), WorldDAI.class);
        WorldDO worldDO = new WorldDO(name, this.world.getSize(), toJsonString(currentMap) );
        try{
            worldQuery.addWorld(worldDO);
            getConnection().close();
        }catch (EoDException e){
            System.out.println("Name already taken");
        }
    }


    public void deleteWorld(String name) throws SQLException{
        final WorldDAI worldQuery = QueryTool.getQuery(getConnection(), WorldDAI.class);
        try {
            worldQuery.deleteWorld(name);
            System.out.printf("%s has been deleted.\n", name);
            getConnection().close();
        } catch (EoDException e) {
            System.out.printf("%s does not exist", name);
        }
    }


    public HashMap<List<Integer>, Character> readWorldData(String name) throws SQLException, IOException {
        HashMap<List<Integer>, Character> savedMap = new HashMap<>();

        final WorldDAI worldQuery = QueryTool.getQuery(getConnection(),WorldDAI.class);
        WorldDO worldDO = worldQuery.getWorld(name);
        getConnection().close();

        try {
            savedMap = fromJsonString(worldDO.getWorld());
        }
        catch (NullPointerException e){
            System.out.println("World does not exist");
        }
        return savedMap;
    }


    public Integer readConfigData(String name) throws SQLException
    {
        final WorldDAI worldQuery = QueryTool.getQuery(getConnection(),WorldDAI.class);
        int size = 0;
        WorldDO worldDO = worldQuery.getWorld(name);
        getConnection().close();

        try {
            size = worldDO.getSize();
        }
        catch (NullPointerException e){
            System.out.println("World does not exist");
        }
        return size;
    }
}