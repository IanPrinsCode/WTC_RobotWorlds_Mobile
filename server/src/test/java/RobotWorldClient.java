import com.fasterxml.jackson.databind.JsonNode;

public interface RobotWorldClient {
    /**
     * Connects to the Robot.Robot Worlds server on specified ip address and port
     * @param ipAddress either `localhost` or actual IP address
     * @param port port that server is configured to receive connections on
     */
    void connect(String ipAddress, int port);

    /**
     * Checks if client is connected to server
     * @return
     */
    boolean isConnected();

    /**
     * Disconnect from server
     */
    void disconnect();

    /**
     * Sends a request presented by the Json object to the server
     * @param requestJsonString a String representing the Json string to send to server
     * @return the response as a JsonNode
     */
    JsonNode sendRequest(String requestJsonString);

    String sendRequestAsString(String requestString);
}
