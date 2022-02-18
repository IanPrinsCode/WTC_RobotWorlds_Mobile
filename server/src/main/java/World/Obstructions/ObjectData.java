package World.Obstructions;

import World.Direction;
import World.Position;

public class ObjectData extends Obstructions {


    private final Integer distance;
    private final String type;
    private final Direction direction;

    public ObjectData(Position position, Integer distance, Direction direction, String type) {
        super(position);
        this.direction = direction;
        this.type = type;
        this.distance = distance;
    }

    public Integer getDistance(){return this.distance; }

    public String getType(){ return this.type; }

    public Direction getDirection(){ return this.direction; }

}
