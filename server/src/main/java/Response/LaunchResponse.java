package Response;

import Robot.Robot;
import World.*;
import org.json.JSONArray;
import org.json.JSONObject;


public class LaunchResponse extends Response {

    Response response;

    public LaunchResponse(Robot robot){
        super(robot);
        this.robot = robot;
    }


    public JSONObject executeRsponse () {
        response = new Response(robot);
        JSONObject response = new JSONObject();
        JSONArray position = new JSONArray();
        JSONObject Data = new JSONObject();
        JSONObject State = new JSONObject();

        position.put(robot.getPosition().getX());
        position.put(robot.getPosition().getY());
        Direction direction = robot.getCurrentDirection();
        String result = "OK";
        String status = "Normal";
        Data.put("position", position);
        Data.put("visibility", robot.getWorld().getVisibility());
        Data.put("reload", robot.getWorld().getReloadSpeed());
        Data.put("repair", robot.getWorld().getRepairSpeed());
        Data.put("mine", robot.getWorld().getMineSpeed());
        Data.put("shields", robot.getShield());
        State.put("position",position );
        State.put("direction", direction);
        State.put("shield",robot.getShield());
        State.put("shots",robot.getShots());
        State.put("status",status);
        response.put("result",result);
        response.put("data",Data);
        response.put("state",State);

        return response;
    }


    public JSONObject executeRsponse (String errorType) {
        switch (errorType) {
            case "RobotExists":
                return RobotExistsResponse();
            case "WorldIsFull":
                return WorldIsFullResponse ();
        }
        return new JSONObject();
    }


    private JSONObject RobotExistsResponse () {
        response = new Response(robot);
        JSONObject response = new JSONObject();
        JSONObject Data = new JSONObject();

        String result = "ERROR";
        String message = "Too many of you in this world";
        Data.put("message", message);
        response.put("result", result);
        response.put("data", Data);

        return response;
    }


    private JSONObject WorldIsFullResponse () {
        response = new Response(robot);
        JSONObject response = new JSONObject();
        JSONObject Data = new JSONObject();

        String result = "ERROR";
        String message = "No more space in this world";
        Data.put("message", message);
        response.put("result", result);
        response.put("data", Data);

        return response;
    }
}