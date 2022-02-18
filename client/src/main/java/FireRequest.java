import org.json.JSONArray;

public class FireRequest extends Request {
    public FireRequest(String name) {
        super(name, "fire", new JSONArray("[]"));
    }
}
