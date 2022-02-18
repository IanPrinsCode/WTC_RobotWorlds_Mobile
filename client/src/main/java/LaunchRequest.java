import org.json.JSONArray;

public class LaunchRequest extends Request{
    public LaunchRequest(String robot, String kind, int maxShieldStrength, int maxShots) {
        super(robot, "launch",
                new JSONArray("[" + kind +", " + maxShieldStrength + ", " + maxShots + "]") );
    }
}
