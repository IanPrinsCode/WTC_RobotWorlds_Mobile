package Services.Models;

import net.lemnik.eodsql.BaseQuery;
import net.lemnik.eodsql.Select;
import net.lemnik.eodsql.Update;

import java.util.List;

public interface WorldDAI extends BaseQuery {

    @Select(
            "SELECT name "
                    +"FROM worlds "
    )
    List<WorldDO> getAllWorlds();

    @Select(
            "SELECT * "
                    +"FROM worlds "
    )
    List<WorldDO> getAllWorldsDO();

    @Select(
            "SELECT * "
                    +"FROM worlds "
                    +"WHERE name = ?{1}"
    )
    WorldDO getWorld(String name);

    @Update(
            "CREATE TABLE IF NOT EXISTS worlds(name, size, world, PRIMARY KEY (name))")
    void createWorldTable();

    @Update(
            "INSERT INTO worlds(name, size, world) "
                    +"VALUES (?{1.name}, ?{1.size}, ?{1.world})"
    )
    void addWorld(WorldDO world);

    @Update(
            "DELETE FROM worlds "
                    +"WHERE name = ?{1}")
    void deleteWorld(String name);
}
