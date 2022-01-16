package mazes;

import java.util.EnumSet;

public enum Direction {
    NORTH(0, -1), SOUTH(0, 1), WEST(-1, 0), EAST(1, 0);

    int xDir, yDir;
    Direction opposite;

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

    public static final EnumSet<Direction> RIGHT_UP_ELBOW = EnumSet.of(EAST, NORTH),
            RIGHT_DOWN_ELBOW = EnumSet.of(EAST, SOUTH),
            LEFT_UP_ELBOW = EnumSet.of(WEST, NORTH),
            LEFT_DOWN_ELBOW = EnumSet.of(WEST, SOUTH);

    public static final EnumSet<Direction> HORIZONTAL_DIRECTIONS = EnumSet.of(EAST, WEST),
            VERTICAL_DIRECTIONS = EnumSet.of(NORTH, EAST);
}