package Services.Models;

import net.lemnik.eodsql.ResultColumn;

public class WorldDO {
    private String name;
    private int size;
    private String world;

    public WorldDO(){};

    public WorldDO(String name, int size, String world){
        this.name = name;
        this.size = size;
        this.world = world;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public String getWorld() {
        return world;
    }

    @ResultColumn(value = "name")
    public void setName(String name) {
        this.name = name;
    }

    @ResultColumn(value = "size")
    public void setSize(int size) {
        this.size = size;
    }

    @ResultColumn(value = "world")
    public void setWorld(String world) {
        this.world = world;
    }
}
