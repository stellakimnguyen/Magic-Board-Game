public class Move {
    private MoveType type;
    private boolean isEnabled;

    // CONSTRUCTORS
    public Move() {
        type = MoveType.NORTH;
        isEnabled = true;
    }

    public Move(MoveType move) {
        type = move;
        isEnabled = true;
    }

    public Move(MoveType move, boolean isOnBoard) {
        type = MoveType.NORTH;
        isEnabled = isOnBoard;
    }

    // GETTERS & SETTERS
    public MoveType getType() {
        return type;
    }

    public void setType(MoveType type) {
        this.type = type;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    enum MoveType {
        NORTH,
        EAST,
        SOUTH,
        WEST
    }
}
