package mazes;

//Cut the grid in half, need to add logic when printing to image
public class MoebiusGrid extends CylinderGrid {
    // Double columns to compensate for other side
    public MoebiusGrid(int rows, int columns) {
        super(rows, columns * 2);
    }
}
