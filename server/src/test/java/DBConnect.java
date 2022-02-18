import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnect
{
    public static final String IN_MEMORY_DB_URL = "jdbc:sqlite::memory:";


    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(IN_MEMORY_DB_URL);
    }
}