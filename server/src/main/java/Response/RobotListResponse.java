package Response;

import Robot.Robot;
import World.Direction;
import org.json.JSONArray;
import org.json.JSONObject;

public class RobotListResponse extends Response {
    Response response;

    public RobotListResponse(Robot robot) {
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
        State.put("shots", robot.getShots());

        response.put("name", robot.getName());
        response.put("state", State);

        return response;
    }
}
