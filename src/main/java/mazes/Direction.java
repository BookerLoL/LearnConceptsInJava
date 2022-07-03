package mazes;

import java.util.EnumSet;
import java.util.Set;

public enum Direction {
    NORTH(0, -1), SOUTH(0, 1), WEST(-1, 0), EAST(1, 0);

    public final int xDir, yDir;
    private Direction opposite;

    Direction(int xDir, int yDir) {
        this.xDir = xDir;
        this.yDir = yDir;
    }

    static {
        NORTH.opposite = SOUTH;
        SOUTH.opposite = NORTH;
        EAST.opposite = WEST;
        WEST.opposite = EAST;
    }

    public Direction getOpposite() {
        return this.opposite;
    }

    public static final Set<Direction> RIGHT_UP_ELBOW = EnumSet.of(EAST, NORTH),
            RIGHT_DOWN_ELBOW = EnumSet.of(EAST, SOUTH),
            LEFT_UP_ELBOW = EnumSet.of(WEST, NORTH),
            LEFT_DOWN_ELBOW = EnumSet.of(WEST, SOUTH);

    public static final Set<Direction> HORIZONTAL_DIRECTIONS = EnumSet.of(EAST, WEST),
            VERTICAL_DIRECTIONS = EnumSet.of(NORTH, EAST);
}