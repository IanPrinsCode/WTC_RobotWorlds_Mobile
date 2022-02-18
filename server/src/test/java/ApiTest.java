import kong.unirest.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiTest {

    @AfterEach
    private void stop(){
        Unirest.delete("http://localhost:5001/admin/robot/jeff").asJson();
    }

    @Test
    @DisplayName("GET /world")
    public void defaultShouldGetWorlds() {
        HttpResponse<String> response = Unirest.get("http://localhost:5001/world").asString();
        assertEquals(200, response.getStatus());
        assertEquals("{\"name\":\"current\",\"size\":1,\"world\":\"{\"[1, 0]\":\"#\",\"[0, -1]\":\"#\",\"[1, 1]\":\"#\",\"[0, 0]\":\"*\"," +
                "\"[-1, -1]\":\"#\",\"[0, 1]\":\"#\",\"[-1, 0]\":\"#\",\"[-1, 1]\":\"#\",\"[1, -1]\":\"#\"}\"}",
                response.getBody().replace("\\", ""));
    }

    @Test
    @DisplayName("POST /robot/{name}")
    public void defaultShouldLook() throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.post("http://localhost:5001/robot/jeff")
                .header("Content-Type", "application/json")
                .body("{\"robot\":\"jeff\",\"command\":\"launch\",\"arguments\": [\"shooter\",5,5]}").asJson();
        assertEquals(200, response.getStatus());
        assertEquals("{\"result\":\"OK\"," +
                        "\"data\":{\"mine\":3,\"repair\":3,\"shields\":3,\"reload\":3,\"visibility\":10,\"position\":[0,0]}," +
                        "\"state\":{\"shield\":3,\"position\":[0,0],\"shots\":3,\"direction\":\"NORTH\",\"status\":\"Normal\"}}",
                response.getBody().toString());
        response = Unirest.post("http://localhost:5001/robot/jeff")
                .header("Content-Type", "application/json")
                .body("{\"robot\":\"jeff\",\"command\":\"look\",\"arguments\":[]}").asJson();
        assertEquals(200, response.getStatus());
        assertEquals(
                "{\"result\":\"OK\",\"data\":{\"objects\":" +
                "[" +
                "{\"distance\":1,\"type\":\"EDGE\",\"direction\":\"NORTH\"}," +
                "{\"distance\":1,\"type\":\"EDGE\",\"direction\":\"SOUTH\"}," +
                "{\"distance\":1,\"type\":\"EDGE\",\"direction\":\"EAST\"}," +
                "{\"distance\":1,\"type\":\"EDGE\",\"direction\":\"WEST\"}" +
                "]," +
                        "\"message\":\"DONE\"}," +
                "\"state\":" +
                        "{\"shield\":3,\"position\":[0,0],\"shots\":3,\"direction\":\"NORTH\",\"status\":\"LOOK\"}}",
                response.getBody().toString());
    }

}