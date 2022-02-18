package Response;
import Robot.*;
import World.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class StateResponse extends Response {
    Response response;

    public StateResponse(Robot robot) {
        super(robot);
        this.robot = robot;
    }

    public JSONObject executeRsponse() {

        response = new Response(robot);

        JSONObject response = new JSONObject();
        JSONArray position = new JSONArray();
        JSONObject State = new JSONObject();

        position.put(robot.getPosition().getX());
        position.put(robot.getPosition().getY());
        Direction direction = robot.getCurrentDirection();

        String status = "NORMAL";

        State.put("position", position);
        State.put("direction", direction);
        State.put("shield", robot.getShield());
        State.put("shots", robot.getShots());
        State.put("status", status);
        State.put("status", status);
        State.put("shots", robot.getShots());

        response.put("state", State);

        return response;
    }

    public JSONObject executeRsponse(String errorType) {
        response = new Response(robot);
        JSONObject response = new JSONObject();
        JSONObject Data = new JSONObject();

        String result = "ERROR";
        String message = "Robot does not exist";
        Data.put("message", message);
        response.put("result", result);
        response.put("data", Data);

        return response;
    }
}
