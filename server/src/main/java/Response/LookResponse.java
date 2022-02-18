package Response;
import Robot.*;
import World.Obstructions.*;
import World.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LookResponse extends Response {

    Response response;
    ArrayList<HashMap<String, String>> objects = new ArrayList<>();


    public LookResponse(Robot robot) {
        super(robot);
        this.robot = robot;
    }


    public JSONObject executeRsponse() {


        response = new Response(robot);
        String message = "DONE";
        JSONObject response = new JSONObject();
        JSONArray position = new JSONArray();
        JSONObject Data = new JSONObject();
        JSONObject State = new JSONObject();


        position.put(robot.getPosition().getX());
        position.put(robot.getPosition().getY());
        Direction direction = robot.getCurrentDirection();
        String result = "OK";
        String status = "LOOK";

        Data.put("message", message);
        Data.put("objects", setObjects());
        System.out.println(Data.get("objects"));
        State.put("position", position);
        State.put("direction", direction);
        State.put("shield",robot.getShield());
        State.put("shots",robot.getShots());
        State.put("status",status);
        State.put("status", status);



        State.put("shots", robot.getShots());
        response.put("result", result);
        response.put("data", Data);
        response.put("state", State);

        return response;
    }

    private JSONArray setObjects() {
        JSONArray objects = new JSONArray();

        for (ObjectData object : robot.lookAround()) {
            JSONObject tempObject = new JSONObject();
            tempObject.put("direction", object.getDirection());
            tempObject.put("type", object.getType());
            tempObject.put("distance", object.getDistance());
            objects.put(tempObject);
        }

        return objects;
    }

}
