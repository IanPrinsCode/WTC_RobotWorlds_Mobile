import org.json.JSONArray;

public class TurnRequest extends Request {
    public TurnRequest(String name, boolean right) {
        super(name, "turn", new JSONArray("[" + (right? "right" : "left") + "]"));
    }
}
