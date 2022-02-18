import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class StateRobotTests {
    private final static int DEFAULT_PORT = 5000;
    private final static String DEFAULT_IP = "localhost";
    private final RobotWorldClient serverClient = new RobotWorldJsonClient();

    @BeforeEach
    void connectToServer() {
        serverClient.connect(DEFAULT_IP, DEFAULT_PORT);
    }

    @AfterEach
    void disconnectFromServer() {
        serverClient.disconnect();
    }


    @Test
    void defaultNoRobotInWorldResponse() {
        assertTrue(serverClient.isConnected());
        JsonNode response;

        String request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"state\"," +
                "  \"arguments\": []" +
                "}";

        response = serverClient.sendRequest(request);

        assertNotNull(response.get("result"));
        assertEquals("ERROR", response.get("result").asText());
    }

    @Test
    void defaultRobotInWorldResponse(){
        assertTrue(serverClient.isConnected());
        JsonNode response;

        String request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";

        response = serverClient.sendRequest(request);

        assertNotNull(response.get("result"));

        request ="{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"state\"," +
                "  \"arguments\": []" +
                "}";

        response = serverClient.sendRequest(request);

        assertTrue(response.has("state"));
        assertNotNull(response.get("state"));
    }
}
