import org.json.JSONArray;

public class ReloadRequest extends Request {
    public ReloadRequest(String name) {
        super(name, "reload", new JSONArray("[]"));
    }
}
