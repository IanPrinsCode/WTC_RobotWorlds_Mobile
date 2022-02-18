import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Request {
    JSONObject request;

    public Request(String name, String command, JSONArray arguments) {
        this.request = new JSONObject();
        request.put("robot", name);
        request.put("command", command);
        request.put("arguments", arguments);
    }

    public String getRequest() {
        return request.toString();
    }

    public static Request create(String instruction, String name) {
        String[] args = instruction.toLowerCase().trim().split(" ");
        if (args.length == 1) {
            switch (args[0]) {

                case "repair":
                    return new RepairRequest(name);
                case "reload":
                    return new ReloadRequest(name);
                case "look":
                    return new LookRequest(name);
                case "mine":
                    return new MineRequest(name);
                case "fire":
                    return new FireRequest(name);
                case "state":
                    return new StateRequest(name);
                case "right":
                    return new TurnRequest(name, true);
                case "left":
                    return new TurnRequest(name, false);
                default:
                    throw new IllegalArgumentException("Unsupported command: " + instruction);
            }
        }
        else {
            switch (args[0]) {
                case "forward":
                    return new MovementRequest(name, true, Integer.parseInt(args[1]));
                case "back":
                    return new MovementRequest(name, false, Integer.parseInt(args[1]));

                default:
                    throw new IllegalArgumentException("Unsupported command: " + instruction);
            }
        }
    }
}
