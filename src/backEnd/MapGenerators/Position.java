package backEnd.MapGenerators;

public class Position {
    private int x;
    private int y;

    /**
     * Default constructor
     * initiated as (-1,-1)
     */
    public Position() {
        x = -1;
        y = -1;
    }

    /**
     * construcor
     *
     * @param x
     * @param y
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * copy constructor
     *
     * @param position
     */
    public Position(Position position) {
        if (position == null) throw new NullPointerException("the position is null");
        x = position.x;
        y = position.y;
    }

    /**
     * get x
     *
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * get y
     *
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * set x
     *
     * @param x- the new x value
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * set y
     *
     * @param y - the new y value
     */
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "{" + x + "," + y + "}";
    }


    public boolean equals(Position other) {
        return (other.x == x && other.y == y) ? true : false;
    }


    public int hashcode() {
        return (((x + y) * (x + y + 1)) / 2) + x;
    }
}