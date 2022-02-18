import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


class LookRobotTests {
    private final static int DEFAULT_PORT = 5000;
    private final static String DEFAULT_IP = "localhost";
    private final RobotWorldClient serverClient = new RobotWorldJsonClient();
    private final RobotWorldClient serverClient1 = new RobotWorldJsonClient();
    private final RobotWorldClient serverClient2 = new RobotWorldJsonClient();
    private final RobotWorldClient serverClient3 = new RobotWorldJsonClient();
    private final RobotWorldClient serverClient4 = new RobotWorldJsonClient();
    private final RobotWorldClient serverClient5 = new RobotWorldJsonClient();
    private final RobotWorldClient serverClient6 = new RobotWorldJsonClient();
    private final RobotWorldClient serverClient7 = new RobotWorldJsonClient();
    private final RobotWorldClient serverClient8 = new RobotWorldJsonClient();

    @BeforeEach
    void connectToServer(){
        serverClient.connect(DEFAULT_IP, DEFAULT_PORT);
        serverClient1.connect(DEFAULT_IP, DEFAULT_PORT);
        serverClient2.connect(DEFAULT_IP, DEFAULT_PORT);
        serverClient3.connect(DEFAULT_IP, DEFAULT_PORT);
        serverClient4.connect(DEFAULT_IP, DEFAULT_PORT);
        serverClient5.connect(DEFAULT_IP, DEFAULT_PORT);
        serverClient6.connect(DEFAULT_IP, DEFAULT_PORT);
        serverClient7.connect(DEFAULT_IP, DEFAULT_PORT);
        serverClient8.connect(DEFAULT_IP, DEFAULT_PORT);
    }

    @AfterEach
    void disconnectFromServer(){
        serverClient.disconnect();
        serverClient1.disconnect();
        serverClient2.disconnect();
        serverClient3.disconnect();
        serverClient4.disconnect();
        serverClient5.disconnect();
        serverClient6.disconnect();
        serverClient7.disconnect();
        serverClient8.disconnect();
    }

    @Test
    void defaultWorldValidEmptyWorldResponse(){
        // Run with 1X1 world and return 4 edges in response
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
                "  \"command\": \"look\"," +
                "  \"arguments\": []" +
                "}";

        response = serverClient.sendRequest(request);

        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());
        assertTrue(response.get("data").has("objects"));
        JsonNode objects = response.get("data").get("objects");
        objects.forEach(JsonNode -> {
            assertEquals("EDGE", JsonNode.get("type").asText());
        });
    }

    @Test
    void oneObsTwoWorldValidLookRobotsObstaclesResponse(){

        assertTrue(serverClient.isConnected());

        String requestOne = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";

        String requestTwo = "{" +
                "  \"robot\": \"STEVE\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";

        String requestThree = "{" +
                "  \"robot\": \"DAVE\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";

        String requestFour = "{" +
                "  \"robot\": \"ALLIE\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";

        String requestFive = "{" +
                "  \"robot\": \"ALIE\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";

        String requestSix = "{" +
                "  \"robot\": \"RICHARD\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";

        String requestSeven = "{" +
                "  \"robot\": \"CHARLIE\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        String requestEight = "{" +
                "  \"robot\": \"WYATT\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";

        String lookRequest = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"look\"," +
                "  \"arguments\": []" +
                "}";

        serverClient.sendRequest(requestOne);
        serverClient1.sendRequest(requestTwo);
        serverClient2.sendRequest(requestThree);
        serverClient3.sendRequest(requestFour);
        serverClient4.sendRequest(requestFive);
        serverClient5.sendRequest(requestSix);
        serverClient6.sendRequest(requestSeven);
        serverClient7.sendRequest(requestEight);

        JsonNode response = serverClient.sendRequest(lookRequest);
        int robotCount = 0;

        for (JsonNode object : response.get("data").get("objects")){
            if (object.get("type").asText().equals("OBSTACLE")){
                assertEquals("NORTH", object.get("direction").asText());
                assertEquals(1, object.get("distance").asInt());
            }
            else if (object.get("type").asText().equals("ROBOT")){
                robotCount++;
            }
        }

        assertEquals(robotCount, 3);
        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());
        assertTrue(response.get("data").has("objects"));
        serverClient.disconnect();
        serverClient1.disconnect();
        serverClient2.disconnect();
        serverClient3.disconnect();
        serverClient4.disconnect();
        serverClient5.disconnect();
        serverClient6.disconnect();
        serverClient7.disconnect();
    }

    @Test
    void oneObsTwoWorldValidLookObstacleResponse(){
        assertTrue(serverClient.isConnected());
        JsonNode response;

        String launchRequest = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";

        serverClient.sendRequest(launchRequest);

        String lookRequest ="{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"look\"," +
                "  \"arguments\": []" +
                "}";

        response = serverClient.sendRequest(lookRequest);

        int edgeCount = 0;

        for (JsonNode object : response.get("data").get("objects")){
            if (object.get("type").asText().equals("OBSTACLE")){
                assertEquals("NORTH", object.get("direction").asText());
                assertEquals(1, object.get("distance").asInt());
            }
            else if (object.get("type").asText().equals("EDGE")){
                edgeCount++;
            }
        }

        assertNotNull(response.get("result"));
        assertEquals("OK", response.get("result").asText());
        assertTrue(response.get("data").has("objects"));
        assertEquals(4, edgeCount);
        serverClient.disconnect();
    }
}