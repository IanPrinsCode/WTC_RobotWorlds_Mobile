package Response;

import Robot.Robot;
import World.*;
import Command.FireCommand;
import org.json.JSONArray;
import org.json.JSONObject;

public class FireResponse extends Response {
    Response response;
    FireCommand fireCommand;


    public FireResponse(Robot robot){
        super(robot);
        this.robot = robot;
    }


    public JSONObject executeRsponse () {

        fireCommand = new FireCommand();
        response = new Response(robot);
        String message = "";
        JSONObject response = new JSONObject();
        JSONArray position = new JSONArray();
        JSONObject Data = new JSONObject();
        JSONObject State = new JSONObject();


        position.put(robot.getPosition().getX());
        position.put(robot.getPosition().getY());
        Direction direction = robot.getCurrentDirection();
        String result = "OK";
        String status = "FIRE";
        if(fireCommand.hit && !fireCommand.miss){
            message = "HIT";
        }else if(fireCommand.miss && !fireCommand.hit){
            message = "MISS";
        }
        Data.put("message", robot.getStatus());

        State.put("position",position );
        State.put("direction", direction);

        State.put("status",status);
        State.put("shots",robot.getShots());
        State.put("shield",robot.getShield());
        State.put("shots",robot.getShots());
        State.put("status","FIRE");
        response.put("result",result);
        response.put("data",Data);
        response.put("state",State);

        return response;
    }
}
