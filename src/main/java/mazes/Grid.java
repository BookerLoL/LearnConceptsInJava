package mazes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import static java.util.stream.Collectors.*;

public class Grid {
    protected Cell[][] grid;
    protected int rows;
    protected int columns;

    public Grid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        initGrid();
        configureCells();
    }

    private void initGrid() {
        grid = new Cell[rows][columns];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                grid[row][col] = new Cell(row, col);
            }
        }
    }

    protected void configureCells() {
        for (Cell[] row : grid) {
            Arrays.asList(row).forEach(this::setCellNeighbors);
        }
    }

    protected void setCellNeighbors(Cell cell) {
        Arrays.asList(Direction.values()).forEach(d -> setCellNeighbor(cell, d));
    }

    protected void setCellNeighbor(Cell cell, Direction direction) {
        int row = cell.row + direction.yDir;
        int col = cell.col + direction.xDir;

        Cell neighbor = getHelper(row, col);
        cell.setNeighbor(neighbor, direction);
    }

    public void clear() {
        initGrid();
        configureCells();
    }

    public Cell get(int row, int col) {
        return getHelper(row, col);
    }

    private Cell getHelper(int row, int col) {
        if (!isWithinRowRange(row) || !isWithinColumnRange(col))
            return null;
        return grid[row][col];
    }

    protected boolean isWithinRowRange(int row) {
        return row >= 0 && row < rows;
    }

    protected boolean isWithinColumnRange(int col) {
        return col >= 0 && col < columns;
    }

    public Cell randomCell() {
        Random random = new Random(System.nanoTime());
        int row = random.nextInt(rows);
        int col = random.nextInt(columns);
        return get(row, col);
    }

    public int size() {
        return rows * columns;
    }

    public List<Cell> cells() {
        return Arrays.stream(grid).flatMap(Arrays::stream).collect(toList());
    }

    public boolean contains(Cell cell) {
        if (cell == null)
            return false;
        return get(cell.row, cell.col) == cell;
    }

    public String gridString() {
        StringBuilder output = new StringBuilder("+").append(repeat("---+", columns)).append("\n");
        final Cell emptyCell = new Cell(-1, -1);
        for (Cell[] row : grid) {
            StringBuilder top = new StringBuilder("|");
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

    public static void binaryTreeMaze(Grid grid) {
        final EnumSet<Direction> allowedDirections = EnumSet.of(Direction.NORTH, Direction.EAST);
        for (Cell cell : grid.cells()) {
            List<Cell> neighbors = cell.neighbors(allowedDirections);
            System.out.println(cell + "\tNeighbors: " + neighbors);
            random(neighbors).ifPresent(cell::link);
        }
    }

    public static void sideWinderMaze(Grid grid) {
        final Direction verticalDirection = Direction.NORTH, horizontalDirection = Direction.EAST;
        final EnumSet<Direction> runDirections = EnumSet.of(verticalDirection, horizontalDirection);

        for (Cell[] row : grid.grid) {
            List<Cell> runOfCells = new ArrayList<>();
            for (Cell cell : row) {
                runOfCells.add(cell);
                List<Direction> neighborDirections = cell.neighborDirections(runDirections);

                if (neighborDirections.isEmpty())
                    continue;

                Direction linkDirection = random(neighborDirections).get();
                if (linkDirection == verticalDirection) {
                    random(runOfCells).ifPresent(runCell -> runCell.linkNeighbor(linkDirection));
                    runOfCells.clear();
                } else {
                    cell.linkNeighbor(linkDirection);
                }
            }
        }
    }

    public static void aldousBroderMaze(Grid grid) {
        Cell cell = grid.randomCell();
        int unvisited = grid.size() - 1;

        while (unvisited > 0) {
            List<Cell> neighbors = cell.neighbors();
            Cell neighbor = random(neighbors).get();

            if (!neighbor.hasLinks()) {
                cell.link(neighbor);
                unvisited--;
            }
            cell = neighbor;
        }
    }

    public static void wilsonsMaze(Grid grid) {
        Set<Cell> unvisited = new HashSet<>(grid.cells());
        Cell cell = grid.randomCell();
        unvisited.remove(cell);

        while (!unvisited.isEmpty()) {
            cell = random(unvisited).get();
            List<Cell> path = new ArrayList<>();
            path.add(cell);

            while (unvisited.contains(cell)) {
                cell = random(cell.neighbors()).get();

                if (path.contains(cell)) {
                    path.clear();
                } else {
                    path.add(cell);
                }
            }

            link(path);
            unvisited.removeAll(path);
        }
    }

    private static void link(List<Cell> pathOfCells) {
        for (int i = 0; i < pathOfCells.size() - 1; i++) {
            Cell before = pathOfCells.get(i);
            Cell after = pathOfCells.get(i + 1);
            before.link(after);
        }
    }

    public static void huntAndKillMaze(Grid grid) {
        Cell cell = grid.randomCell();
        Set<Cell> visited = new HashSet<>();

        while (true) {
            Optional<Cell> unvisitedCell = random(cell.neighborsWithoutLinks());

            if (unvisitedCell.isEmpty()) {
                for (Cell visitedCell : visited) {
                    Optional<Cell> newNext = random(visitedCell.neighborsWithoutLinks());
                    if (newNext.isPresent()) {
                        cell = visitedCell;
                        unvisitedCell = newNext;
                        break;
                    }
                }
                if (unvisitedCell.isEmpty())
                    break;
            }

            visited.add(cell);
            cell.link(unvisitedCell.get());
            cell = unvisitedCell.get();
        }
    }

    public static void recursiveBacktrackerMaze(Grid grid) {
        recursiveBacktrackerMaze(grid, grid.randomCell());
    }

    public static void recursiveBacktrackerMaze(Grid grid, Cell start) {
        Cell cell = start;
        Stack<Cell> stack = new Stack<>();
        stack.add(cell);

        while (!stack.isEmpty()) {
            cell = stack.peek();

            Optional<Cell> unvisitedCell = random(cell.neighborsWithoutLinks());
            if (unvisitedCell.isPresent()) {
                Cell next = unvisitedCell.get();
                cell.link(next);
                stack.add(next);
            } else {
                stack.pop();
            }
        }
    }

    public static <T> Optional<T> random(List<? extends T> list) {
        if (list == null || list.isEmpty())
            return Optional.empty();

        Random random = new Random(System.nanoTime());
        return Optional.ofNullable(list.get(random.nextInt(list.size())));
    }

    public static <T> Optional<T> random(Set<? extends T> set) {
        if (set == null || set.isEmpty())
            return Optional.empty();

        Random random = new Random(System.nanoTime());
        int randomNumber = random.nextInt(set.size());

        Iterator<? extends T> iterator = set.iterator();
        int currentIndex = 0;
        T randomElement = null;

        while (iterator.hasNext()) {
            randomElement = iterator.next();
            if (currentIndex == randomNumber)
                break;
            currentIndex++;
        }

        return Optional.ofNullable(randomElement);
    }

    public long countUnlinkedCells() {
        return cells().stream().filter(Cell::hasNoLinks).count();
    }

    public long countDeadEnds() {
        return cells().stream().filter(c -> c.totalLinks() == 1).count();
    }

    public List<Cell> getDeadEnds() {
        return cells().stream().filter(c -> c.totalLinks() == 1).collect(toList());
    }

    // No longer a perfect maze when using this
    public static void braid(Grid grid, double braidingThreshold) {
        Random random = new Random(System.nanoTime());
        List<Cell> deadEnds = grid.getDeadEnds();
        Collections.shuffle(deadEnds, random);

        for (Cell deadEnd : deadEnds) {
            if (deadEnd.totalLinks() != 1 || random.nextDouble() > braidingThreshold)
                continue;

            List<Cell> neighbors = deadEnd.neighbors();
            neighbors.removeAll(deadEnd.links());
            List<Cell> otherNeighborDeadEnds = neighbors.stream().filter(c -> c.totalLinks() == 1).collect(toList());
            List<Cell> best = otherNeighborDeadEnds.isEmpty() ? neighbors : otherNeighborDeadEnds;

            Cell neighbor = random(best).get();
            deadEnd.link(neighbor);
        }
    }

    public long countThreeWayIntersections() {
        return cells().stream().filter(c -> c.totalLinks() == 3).count();
    }

    public long countFourWayIntersections() {
        return cells().stream().filter(c -> c.totalLinks() == 4).count();
    }

    public long countCellsWithHorizontalPassages() {
        return cells().stream().filter(c -> c.neighborDirections(Direction.HORIZONTAL_DIRECTIONS).size() == 2).count();
    }

    public long countCellsWithVerticalPassages() {
        return cells().stream().filter(c -> c.neighborDirections(Direction.VERTICAL_DIRECTIONS).size() == 2).count();
    }

    public long countElbowPassages() {
        return cells().stream().filter(this::hasElbowPassage).count();
    }

    public long countElbowPassagesWithoutIntersections() {
        return cells().stream().filter(this::isElbowPassageWithoutIntersection).count();
    }

    private boolean isElbowPassageWithoutIntersection(Cell c) {
        if (c.totalLinks() != 2)
            return false;
        return hasElbowPassage(c);
    }

    private boolean hasElbowPassage(Cell c) {
        return c.hasLinksWithAllDirection(Direction.RIGHT_UP_ELBOW) ||
                c.hasLinksWithAllDirection(Direction.RIGHT_DOWN_ELBOW) ||
                c.hasLinksWithAllDirection(Direction.LEFT_UP_ELBOW) ||
                c.hasLinksWithAllDirection(Direction.LEFT_DOWN_ELBOW);
    }

    protected String repeat(String str, int amount) {
        return String.join("", Collections.nCopies(amount, str));
    }

    public static void main(String[] args) {
        Grid grid = new Grid(8, 8);
        // Grid.recursiveBacktrackerMaze(grid);
        // Grid.braid(grid, 1);
        // System.out.println(grid.gridString());

        Grid.recursiveBacktrackerMaze(grid);
        System.out.println(grid.gridString());
        grid.clear();
        Grid.huntAndKillMaze(grid);
        System.out.println(grid.gridString());
        grid.clear();
        Grid.binaryTreeMaze(grid);
        System.out.println(grid.gridString());
        grid.clear();
        Grid.wilsonsMaze(grid);
        System.out.println(grid.gridString());
        grid.clear();
        Grid.aldousBroderMaze(grid);
        System.out.println(grid.gridString());

    }
}
