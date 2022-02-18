import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseTests {

    private final DBConnect dbConnect = new DBConnect();
    private Connection connection;

    @BeforeEach
    void connectToServer() throws SQLException {
        connection = dbConnect.getConnection();
    }

    @AfterEach
    void disconnectFromServer() throws SQLException {
        connection.close();
    }



}
