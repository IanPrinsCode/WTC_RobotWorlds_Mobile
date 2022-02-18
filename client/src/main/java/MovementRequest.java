import org.json.JSONArray;

public class MovementRequest extends Request {
    public MovementRequest(String name, boolean forward, int steps) {
        super(name, forward? "forward" : "back", new JSONArray("[" + steps + "]"));
    }
}
