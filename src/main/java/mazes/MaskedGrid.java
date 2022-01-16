package mazes;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

import static java.util.stream.Collectors.*;

public class MaskedGrid extends Grid {
    // Indicates cells to hide if true
    private boolean[][] maskedCells;

    public MaskedGrid(int rows, int columns) {
        super(rows, columns);
        maskedCells = new boolean[rows][columns];
    }

    public void clear() {
        super.clear();
        maskedCells = new boolean[rows][columns];
    }

    public void setMask(int row, int col, boolean isMasked) {
        setMask(super.get(row, col), isMasked);
    }

    public void setMask(Cell cell, boolean isMasked) {
        if (cell == null)
            return;
        maskedCells[cell.row][cell.col] = isMasked;
        Consumer<Cell> linkAction = isMasked ? Cell::removeNeighbors : this::setCellNeighbors;
        linkAction.accept(cell);
    }

    public boolean isMasked(Cell cell) {
        if (cell == null)
            return false;
        return maskedCells[cell.row][cell.col];
    }

    public boolean isMasked(int row, int col) {
        return isMasked(super.get(row, col));
    }

    public boolean hide(Cell cell) {
        if (cell == null)
            return false;
        setMask(cell, true);
        return true;
    }

    public boolean hide(int row, int col) {
        return hide(super.get(row, col));
    }

    public boolean show(Cell cell) {
        if (cell == null)
            return false;
        setMask(cell, false);
        return true;
    }

    public Cell get(int row, int col) {
        Cell cell = super.get(row, col);
        return cell == null || isMasked(cell) ? null : cell;
    }

    public boolean show(int row, int col) {
        return show(super.get(row, col));
    }

    public int count() {
        int count = 0;
        for (boolean[] row : maskedCells) {
            for (boolean maskedCell : row) {
                if (!maskedCell)
                    count++;
            }
        }
        return count;
    }

    public List<Cell> cells() {
        return Arrays.stream(grid).flatMap(Arrays::stream).filter(c -> !isMasked(c)).collect(toList());
    }

    public Cell randomCell() {
        Random random = new Random(System.nanoTime());
        Cell cell = null;
        do {
            int row = random.nextInt(rows), col = random.nextInt(columns);
            if (!maskedCells[row][col])
                cell = grid[row][col];
        } while (cell == null);
        return cell;
    }

    public static void main(String[] args) {
        MaskedGrid grid = new MaskedGrid(4, 4);
        grid.hide(1, 1);
        grid.hide(1, 0);
        grid.clear();
        Grid.binaryTreeMaze(grid);
        System.out.println(grid.gridString());
    }
}
