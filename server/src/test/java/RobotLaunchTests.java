import Response.Response;
import World.World;
import com.fasterxml.jackson.databind.JsonNode;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * As a player
 * I want to launch my robot in the online robot world
 * So that I can break the record for the most robot kills
 */
class RobotLaunchTests {
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
    private final String requestOne = launchRequest("jeff");
    private final String requestTwo = launchRequest("hal");
    private final String requestThree = launchRequest("jack");
    private final String requestFour = launchRequest("black");
    private final String requestFive = launchRequest("peter");
    private final String requestSix = launchRequest("parker");
    private final String requestSeven = launchRequest("man");
    private final String requestEight = launchRequest("bolt");
    private final String requestNine = launchRequest("ricus");

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
    void defaultWorldValidLaunchShouldSucceed() throws IOException {
        // Given that I am connected to a running Robot.Robot Worlds server
        // And the world is of size 1x1 (The world is configured or hardcoded to this size)
        World world = new World();
        assertTrue(serverClient.isConnected());

        // When I send a valid launch request to the server
        String request = launchRequest("jeff");
        JsonNode response = serverClient.sendRequest(request);

        doAssertNotNullResponse(response);

        // Then I should get a valid response from the server
        assertEquals("OK", response.get("result").asText());

        // And the position should be (x:0, y:0)
        assertNotNull(response.get("data").get("position"));

        assertTrue(-(world.getWidth()) < response.get("data").get("position").get(0).asInt());
        assertTrue(response.get("data").get("position").get(0).asInt() < (world.getWidth()));
        assertTrue(-(world.getHeight()) < response.get("data").get("position").get(1).asInt());
        assertTrue(response.get("data").get("position").get(1).asInt() < (world.getHeight()));
        assertNotNull(response.get("state"));
    }

    @Test
    void twoWorldRobotExistsShouldFail() throws IOException {
        // Given that I am connected to a running Robot.Robot Worlds server
        // And the world is of size 1x1 (The world is configured or hardcoded to this size)
        assertTrue(serverClient.isConnected());


        String request = launchRequest("jeff");
        // When I send a launch command twice with the same name
        serverClient.sendRequest(request);
        JsonNode response = serverClient.sendRequest(request);

        // Then I should get an error response from the server if a
        // robot with the name already exists
        doAssertNotNullResponse(response);
        assertEquals("ERROR", response.get("result").asText());
        assertEquals("Too many of you in this world", response.get("data").get("message").asText());
    }

    @Test
    void twoWorldFullShouldFail() throws IOException {
        // Given that I am connected to a running Robot.Robot Worlds server
        // And the world is of size 2x2 (The world is configured or hardcoded to this size)
        assertTrue(serverClient.isConnected());

        String requestServerAlreadyFull = launchRequest("R2D2");
        sendEightRequests();
        serverClient.sendRequest(requestNine);
        JsonNode response = serverClient.sendRequest(requestServerAlreadyFull);

        doAssertNotNullResponse(response);
        doErrorNoSpaceAssertion(response);
    }

    @Test
    void oneObsTwoWorldFullWithObstacleShouldFail() throws IOException {
        // With obstacle at [0,1] and 2x2 world
        assertTrue(serverClient.isConnected());

        String requestServerAlreadyFull = launchRequest("oil");

        sendEightRequests();
        JsonNode response = serverClient8.sendRequest(requestServerAlreadyFull);

        doAssertNotNullResponse(response);
        doErrorNoSpaceAssertion(response);
    }

    @Test
    void defaultWorldInvalidLaunchShouldFail(){
        // Given that I am connected to a running Robot.Robot Worlds server
        assertTrue(serverClient.isConnected());

        // When I send a invalid launch request with the command "luanch" instead of "launch"
        String request = "{" +
                "\"robot\": \"Jeff\"," +
                "\"command\": \"luanch\"," +
                "\"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
        JsonNode response = serverClient.sendRequest(request);

        doAssertNotNullResponse(response);

        // Then I should get an error response
        assertEquals("ERROR", response.get("result").asText());

        // And the message "Unsupported command"
        assertNotNull(response.get("data").get("message"));
        assertTrue(response.get("data").get("message").asText().contains("Unsupported command"));
    }

    @Test
    void oneObsTwoWorldLaunchWithOneObstacle() throws IOException {
        // In a 2x2 world if there is an obstacle at [0,1] and I launch 3 robots
        // None of them can be at position [0,1]
        assertTrue(serverClient.isConnected());

        JsonNode responseOne = serverClient.sendRequest(requestOne);
        JsonNode responseTwo = serverClient1.sendRequest(requestTwo);
        JsonNode responseThree = serverClient2.sendRequest(requestThree);

        doAssertNotNullResponse(responseOne);
        doAssertNotNullResponse(responseTwo);
        doAssertNotNullResponse(responseThree);

        assertNotEquals(responseOne.get("data").get("position").asText(), "[0,1]");
        assertNotEquals(responseTwo.get("data").get("position").asText(), "[0,1]");
        assertNotEquals(responseThree.get("data").get("position").asText(), "[0,1]");
    }

    @Test
    void twoWorldLaunchTwoRobotsShouldPass() {
        assertTrue(serverClient.isConnected());

//        String request = launchRequest("jack");
        JsonNode response1 = serverClient.sendRequest(requestOne);

//        String request2 = launchRequest("jeff");
        JsonNode response = serverClient.sendRequest(requestTwo);

        assertEquals(response.get("result").textValue(),"OK");
        assertEquals(response1.get("result").textValue(),"OK");
    }


    private String launchRequest(String name){
        return "{" +
                "\"robot\":\"" + name +"\"," +
                "\"command\": \"launch\"," +
                "\"arguments\": [\"shooter\",\"5\",\"5\"]" +
                "}";
    }

    private void sendEightRequests(){
        serverClient.sendRequest(requestOne);
        serverClient1.sendRequest(requestTwo);
        serverClient2.sendRequest(requestThree);
        serverClient3.sendRequest(requestFour);
        serverClient4.sendRequest(requestFive);
        serverClient5.sendRequest(requestSix);
        serverClient6.sendRequest(requestSeven);
        serverClient7.sendRequest(requestEight);
    }

    private void doErrorNoSpaceAssertion(JsonNode response) {
        assertEquals("ERROR", response.get("result").asText());
        assertEquals("No more space in this world", response.get("data").get("message").asText());
    }

    private void doAssertNotNullResponse(JsonNode response){
        assertNotNull(response.get("result"));
        assertNotNull(response.get("data"));
    }
}