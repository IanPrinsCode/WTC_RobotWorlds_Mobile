package Response;

import Robot.Robot;
import org.json.JSONObject;

public class Response {

    Robot robot;
    public  Response( Robot robot){
        this.robot = robot;

    }

    public Response() {

    }

    public static JSONObject setResult(String instruction, Robot robot){


        String[] args = instruction.toLowerCase().trim().split(" ");
        switch (args[0]){
            case "forward":
                return new ForwardResponse(robot).executeRsponse(instruction);
            case "back":
                return new BackResponse(robot).executeRsponse(instruction);
            case "mine":
                return new MineResponse(robot).executeRsponse();
            case "repair":
                return new RepairResponse(robot).executeRsponse();
            case "fire":
                return new FireResponse(robot).executeRsponse();
            case "look":
                return new LookResponse(robot).executeRsponse();
            case "reload":
                return new ReloadResponse(robot).executeRsponse();
            case "launch":
                return new LaunchResponse(robot).executeRsponse();
            case "state":
                return new StateResponse(robot).executeRsponse();
            case "turn":
                if (args[1].equals("right")) {
                    return new LeftResponse(robot).executeRsponse();
                }
                if (args[1].equals("left")) {
                    return new LeftResponse(robot).executeRsponse();
                }
        }

        return null;
    }





}