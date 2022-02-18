import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * As a player
 * I want to command my robot to move forward a specified number of steps
 * so that I can explore the world and not be a sitting duck in a battle.
 */
class MovementCommandTests {
    private final static int DEFAULT_PORT = 5000;
    private final static String DEFAULT_IP = "localhost";
    private final RobotWorldClient serverClient = new RobotWorldJsonClient();

    @BeforeEach
    void connectToServer(){ serverClient.connect(DEFAULT_IP, DEFAULT_PORT); }

    @AfterEach
    void disconnectFromServer(){
        serverClient.disconnect();
    }

    @Test
    void defaultMoveForward() {
        assertTrue(serverClient.isConnected());

        //Given that I am connected to a running Robot Worlds server
        //And the world is of size 1x1 with no obstacles or pits
        //And a robot called "HAL" is already connected and launched
        String request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"launch\"," +
                "  \"arguments\": [\"shooter\"]" +
                "}";
        serverClient.sendRequest(request);

        request = "{" +
                "  \"robot\": \"HAL\"," +
                "  \"command\": \"forward\"," +
                "  \"arguments\": [5]" +
                "}";


        //When I send a command for "HAL" to move forward by 5 steps
        JsonNode response = serverClient.sendRequest(request);
//        response = serverClient.sendRequest(request);


        //Then I should get an "OK" response with the message "At the NORTH edge"
        //and the position information returned should be at co-ordinates [0,0]
        assertNotNull(response.get("result"));
        assertNotNull(response.get("data"));
        assertEquals("OK", response.get("result").asText());
        assertEquals("At the NORTH edge", response.get("data").get("message").asText());
        assertEquals("[0,0]", response.get("state").get("position").toString());

    }
}
