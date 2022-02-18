import org.json.JSONArray;

public class MineRequest extends Request {
    public MineRequest(String name) {
        super(name, "mine", new JSONArray("[]"));
    }
}
