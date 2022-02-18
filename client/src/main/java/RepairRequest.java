import org.json.JSONArray;

public class RepairRequest extends Request {
    public RepairRequest(String name) {
        super(name, "repair", new JSONArray("[]"));
    }
}
