public class Square {
    private int xCoord;
    private int yCoord;
    private int squareValue;
    private boolean isVisited;
    private Move moveNorth;
    private Move moveEast;
    private Move moveSouth;
    private Move moveWest;

    //CONSTRUCTORS
    public Square() {
        xCoord = 0;
        yCoord = 0;
        squareValue = 0;
        isVisited = false;
        moveNorth = new Move(Move.MoveType.NORTH);
        moveEast = new Move(Move.MoveType.EAST);
        moveSouth = new Move(Move.MoveType.SOUTH);
        moveWest = new Move(Move.MoveType.WEST);
    }

    public Square(int x, int y, int value) {
        xCoord = x;
        yCoord = y;
        squareValue = value;
        isVisited = false;
        moveNorth = new Move(Move.MoveType.NORTH, !(x - this.squareValue <= 0));
        moveEast = new Move(Move.MoveType.EAST, !(y + this.squareValue >= Main.input));
        moveSouth = new Move(Move.MoveType.SOUTH, !(x + this.squareValue >= Main.input));
        moveWest = new Move(Move.MoveType.WEST, !(y - this.squareValue <= 0));
    }

    // GETTERS & SETTERS
    public int getXCoord() {
        return xCoord;
    }

    public void setXCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getYCoord() {
        return yCoord;
    }

    public void setYCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    public int getSquareValue() {
        return squareValue;
    }

    public void setSquareValue(int squareValue) {
        this.squareValue = squareValue;
    }

    public Move getMoveNorth() {
        return moveNorth;
    }

    public void setMoveNorth(Move moveNorth) {
        this.moveNorth = moveNorth;
    }

    public Move getMoveEast() {
        return moveEast;
    }

    public void setMoveEast(Move moveEast) {
        this.moveEast = moveEast;
    }

    public Move getMoveSouth() {
        return moveSouth;
    }

    public void setMoveSouth(Move moveSouth) {
        this.moveSouth = moveSouth;
    }

    public Move getMoveWest() {
        return moveWest;
    }

    public void setMoveWest(Move moveWest) {
        this.moveWest = moveWest;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }
}
