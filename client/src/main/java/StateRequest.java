import org.json.JSONArray;

public class StateRequest extends Request {
    public StateRequest(String name) {
        super(name, "state", new JSONArray("[]"));
    }
}
