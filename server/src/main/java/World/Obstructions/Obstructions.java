package World.Obstructions;
import World.Position;

public abstract class Obstructions {
    private final Position topLeft;
    private final Position bottomRight;
    private final Position bottomLeft;
    private final int x;
    private final int y;
    private final int size;



    public Obstructions(Position position) {
        this.topLeft = position;
        this.bottomRight = position;
        this.bottomLeft = position;
        this.size = 1;
        this.x = bottomLeft.getX();
        this.y = bottomLeft.getY();

    }


    public Position getBottomRightPosition() {
        return this.bottomRight;
    }


    public Position getTopLeftPosition() {
        return this.topLeft;
    }


    public Position getBottomLeftPosition() {return  this.bottomLeft;}


    public int getSize() {
        return this.size;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public boolean blocksPosition(Position position) {
        Boolean checkY = this.y <= position.getY() && (this.y + 4) >= position.getY();
        Boolean checkX = this.x <= position.getX() && (this.x + 4) >= position.getX();

        if (checkX && checkY) {
            return true;
        }
        return  false;
    }


    public boolean blocksPath(Position a, Position b) {
        if (a.getX() == b.getX() && (this.x <= a.getX() && a.getX() <= this.x + 4)) {
            if (b.getY() < this.y) {
                return a.getY() >= this.y;
            }
            else if (b.getY() > (this.y + 4)) {
                return a.getY() <= this.y + 4;
            }
        }
        else if (a.getY() == b.getY() && (this.y <= a.getY() && a.getY() <= this.y + 4)) {
            if (b.getX() < this.x) {
                return a.getX() >= this.x;
            }
            else if (b.getX() > (this.x + 4)) {
                return a.getX() <= this.x + 4;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return topLeft + " to " + bottomRight;
    }
}
