import org.json.JSONArray;

public class LookRequest extends Request {
    public LookRequest(String name) {
        super(name, "look", new JSONArray("[]"));
    }
}
