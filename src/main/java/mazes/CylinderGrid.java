package mazes;

//Looks weird because this is meant to wrap around
public class CylinderGrid extends Grid {
    public CylinderGrid(int rows, int columns) {
        super(rows, columns);
    }

    /**
     * Attempts to retrieve the cell at the given coordinates.
     *
     * @param row The row of the cell, 0 <= value && value => total rows
     * @param col The column of the cell, value >= 0
     * @return The cell if valid coordinates otherwise returns null.
     * @implNote We need to override this method to handle special logic of the cylinder grid.
     */
    public Cell get(int row, int col) {
        if (!isWithinRowRange(row)) {
            return null;
        }
        return grid[row][Math.floorMod(col, grid[row].length)];
    }

    public String gridString() {
        StringBuilder output = new StringBuilder("+").append(repeat("---+", columns)).append("\n");
        final Cell emptyCell = new Cell(-1, -1);
        for (Cell[] row : grid) {
            StringBuilder top = row[0].hasLink(Direction.WEST) ? new StringBuilder(" ") : new StringBuilder("|");
            StringBuilder bottom = new StringBuilder("+");

            for (Cell cell : row) {
                cell = cell != null ? cell : emptyCell;
                String body = "   ";

                String eastBoundary = cell.hasLink(cell.getNeighbor(Direction.EAST).orElse(emptyCell)) ? " " : "|";
                top.append(body).append(eastBoundary);

                String southBoundary = cell.hasLink(cell.getNeighbor(Direction.SOUTH).orElse(emptyCell)) ? "   "
                        : "---";
                bottom.append(southBoundary).append("+");
            }

            output.append(top).append("\n");
            output.append(bottom).append("\n");
        }
        return output.toString();
    }
}
