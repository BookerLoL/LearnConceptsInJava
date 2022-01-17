package mazes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * The Grid class is synonymous to a Graph for graph theory.
 * <p>
 * The underlying body of what a 2D maze is composed of.
 * <p>
 * Despite being a 2D maze, the maze can be manipulated for visual usage and be
 * made into 3D maze art.
 *
 * @author Ethan Booker
 * @see <a href="https://weblog.jamisbuck.org/">Source of inspiration - Jamis
 *      Buck</a>
 */
public class Grid implements Iterable<Cell> {
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
        iterator().forEachRemaining(this::setCellNeighbors);
    }

    protected void setCellNeighbors(Cell cell) {
        Arrays.asList(Direction.values()).forEach(d -> setCellNeighbor(cell, d));
    }

    protected void setCellNeighbor(Cell cell, Direction direction) {
        int row = cell.row + direction.yDir;
        int col = cell.col + direction.xDir;

        Cell neighbor = get(row, col);
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

    public static void binaryTreeMaze(Grid grid) {
        for (Cell cell : grid) {
            List<Cell> neighbors = cell.neighbors(Direction.RIGHT_UP_ELBOW);
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
        aldousBroderMaze(grid, grid.randomCell(), 1.0);
    }

    public static void aldousBroderMaze(Grid grid, Cell startingCell) {
        aldousBroderMaze(grid, startingCell, 1.0);
    }

    /**
     * Performs the aldous broder algorithm and processes the percentage of cells
     * based on how many cells are left that don't have links.
     *
     * @param startingCell               The cell to start the processing at
     * @param percentageOfCellsToProcess 0.5 = 50%, 0.25 = 25%, 1.0 >= 100%
     */
    public static void aldousBroderMaze(Grid grid, Cell startingCell, double percentageOfCellsToProcess) {
        Cell cell = startingCell;
        long unvisited = grid.cells().stream().filter(Cell::hasNoLinks).count() - 1;
        unvisited = percentOf(unvisited, getValidPercent(percentageOfCellsToProcess));

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

    private static long percentOf(long total, double percentage) {
        double percent = percentage * 100.0;
        return (long) ((total * percent) / 100.0);
    }

    private static double getValidPercent(double percentage) {
        if (percentage >= 1.0)
            return 1.0;
        else if (percentage <= 0.0)
            return 0.0;
        else
            return percentage;
    }

    public static void wilsonsMaze(Grid grid) {
        wilsonsMaze(grid, grid.randomCell(), 1.0);
    }

    public static void wilsonsMaze(Grid grid, Cell startingCell) {
        Set<Cell> unvisited = grid.cells().stream().filter(Cell::hasNoLinks).collect(Collectors.toSet());
        Cell cell = startingCell;
        unvisited.remove(cell);

        while (!unvisited.isEmpty()) {
            cell = random(unvisited).get();
            List<Cell> path = new ArrayList<>();
            path.add(cell);

            while (unvisited.contains(cell)) {
                cell = random(cell.neighbors()).get();
                if (path.contains(cell))
                    path.clear();
                else
                    path.add(cell);
            }

            link(path);
            unvisited.removeAll(path);
        }
    }

    public static void wilsonsMaze(Grid grid, Cell startingCell, double percentageOfCellsToProcess) {
        Set<Cell> unvisited = grid.cells().stream().filter(Cell::hasNoLinks).collect(Collectors.toSet());
        long unvisitedThreshold = percentOf(unvisited.size(), 1.0 - percentageOfCellsToProcess);
        Cell cell = startingCell;
        unvisited.remove(cell);

        while (!unvisited.isEmpty() && unvisited.size() >= unvisitedThreshold) {
            cell = random(unvisited).get();
            List<Cell> path = new ArrayList<>();
            path.add(cell);

            while (unvisited.contains(cell)) {
                cell = random(cell.neighbors()).get();
                if (path.contains(cell))
                    path.clear();
                else
                    path.add(cell);
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

    public static void houstonsMaze(Grid grid) {
        houstonsMaze(grid, 1.0 / 3);
    }

    public static void houstonsMaze(Grid grid, double aldousBroaderMazeCompletionPercentage) {
        aldousBroderMaze(grid, grid.randomCell(), aldousBroaderMazeCompletionPercentage);
        wilsonsMaze(grid);
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

    public static final Function<List<Cell>, Cell> SELECT_RANDOM = list -> random(list).get();
    public static final Function<List<Cell>, Cell> SELECT_MOST_OLDEST = list -> list.get(list.size() / 2);
    public static final Function<List<Cell>, Cell> SELECT_MIDDLE = list -> list.get(list.size() / 2);
    public static final Function<List<Cell>, Cell> SELECT_MOST_RECENT = list -> list.get(list.size() - 1);

    public static final Function<List<Cell>, Cell> SAME_CELL_UNTIL_NO_MORE_NEIGHBOR_UNLINKED_SELECTOR(
            Function<List<Cell>, Cell> selectingFunction) {
        // Need to use object to main state in a Functional object, will only have size
        // 1 at most
        List<Cell> previousCells = new ArrayList<>();
        return list -> {
            if (!previousCells.isEmpty()) {
                Cell previousCell = previousCells.get(0);
                if (!previousCell.neighborsWithoutLinks().isEmpty())
                    return previousCell;
                previousCells.remove(previousCell);
            }
            Cell cell = selectingFunction.apply(list);
            previousCells.add(cell);
            return cell;
        };
    }

    public static class SelectorItem {
        public final Function<List<Cell>, Cell> selectorFunction;
        public final double oddsOfPicking;

        public SelectorItem(Function<List<Cell>, Cell> selectorFunction, double oddsOfPicking) {
            this.selectorFunction = selectorFunction;
            this.oddsOfPicking = oddsOfPicking;
        }
    }

    public static final Function<List<Cell>, Cell> MIX_OF_SELECTORS(SelectorItem... selectors) {
        return list -> {
            Random random = new Random(System.nanoTime());
            double chance = random.nextDouble();

            for (SelectorItem selectionItem : selectors) {
                if (chance <= selectionItem.oddsOfPicking) {
                    return selectionItem.selectorFunction.apply(list);
                }
                chance -= selectionItem.oddsOfPicking;
            }

            System.out.println("Selecting last: " + chance);
            // Select last given list if none odds are not satisfied
            return selectors[selectors.length - 1].selectorFunction.apply(list);
        };
    }

    // Using last which mimics the stack appraoch
    public static void recursiveBacktrackerMaze(Grid grid) {
        growingTreeMaze(grid, SELECT_MOST_RECENT);
    }

    // The recursive version of recursive backtracker appraoch
    public static void depthFirstSearchMaze(Grid grid) {
        depthFirstSearchMazeHelper(grid.randomCell(), new HashSet<>());
    }

    private static void depthFirstSearchMazeHelper(Cell cell, Set<Cell> visitedCells) {
        visitedCells.add(cell);

        List<Cell> unlinkedNeighbors = cell.neighborsWithoutLinks();
        while (!unlinkedNeighbors.isEmpty()) {
            Cell neighbor = random(unlinkedNeighbors).get();
            if (!visitedCells.contains(neighbor)) {
                cell.link(neighbor);
                depthFirstSearchMazeHelper(neighbor, visitedCells);
            }
            unlinkedNeighbors.remove(neighbor);
        }
    }

    private static class CellPair implements Comparable<CellPair> {
        final long weight;
        final Cell left, right;

        public CellPair(long weight, Cell left, Cell right) {
            this.weight = weight;
            this.left = left;
            this.right = right;
        }

        @Override
        public int compareTo(CellPair o) {
            return Long.compare(weight, o.weight);
        }

        public String toString() {
            return "Weight: " + weight + "\tLeft: " + left + "\t" + right;
        }
    }

    public static void randomizedKruskalsMaze(Grid grid) {
        List<Cell> cells = grid.cells();

        Map<Cell, Set<Cell>> cellGroups = cells.stream()
                .collect(Collectors.toMap(Function.identity(), c -> new HashSet<>(Arrays.asList(c))));
        PriorityQueue<CellPair> neighborPairs = generateKruskalPairs(cells);

        while (!neighborPairs.isEmpty()) {
            CellPair pair = neighborPairs.remove();
            if (canMergeKruskalPair(cellGroups, pair))
                mergeKruskalPair(cellGroups, pair);
        }
    }

    private static boolean canMergeKruskalPair(Map<Cell, Set<Cell>> cellGroups, CellPair pair) {
        Set<Cell> leftGroup = cellGroups.get(pair.left), rightGroup = cellGroups.get(pair.right);
        return canMergeCellGroups(leftGroup, rightGroup);
    }

    // This can be used for other cell grouping algorithms that follow similar logic
    private static boolean canMergeCellGroups(Set<Cell> leftGroup, Set<Cell> rightGroup) {
        if (leftGroup == rightGroup)
            return false;
        return leftGroup.stream().noneMatch(rightGroup::contains);
    }

    private static void mergeKruskalPair(Map<Cell, Set<Cell>> cellGroups, CellPair pair) {
        pair.left.link(pair.right);
        mergeCellGroups(cellGroups, cellGroups.get(pair.left), cellGroups.get(pair.right));
    }

    private static void mergeCellGroups(Map<Cell, Set<Cell>> cellGroups, Set<Cell> winners, Set<Cell> losers) {
        winners.addAll(losers);
        losers.forEach(loser -> cellGroups.put(loser, winners));
    }

    private static PriorityQueue<CellPair> generateKruskalPairs(List<Cell> gridCells) {
        Random random = new Random(System.nanoTime());
        PriorityQueue<CellPair> pairsToGroup = new PriorityQueue<>();
        for (Cell cell : gridCells) {
            cell.getNeighbor(Direction.SOUTH)
                    .ifPresent(neighbor -> pairsToGroup.add(new CellPair(random.nextInt(), cell, neighbor)));
            cell.getNeighbor(Direction.EAST)
                    .ifPresent(neighbor -> pairsToGroup.add(new CellPair(random.nextInt(), cell, neighbor)));
        }
        return pairsToGroup;
    }

    public static void randomizedPrimsMaze(Grid grid) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public static void simpliedPrimsMaze(Grid grid) {
        growingTreeMaze(grid, SELECT_RANDOM);
    }

    // Cheaper but still effective as True Prim's algorithm
    public static void weightedPrimsMaze(Grid grid) {
        Random random = new Random(System.nanoTime());
        Map<Cell, Integer> weights = new HashMap<>();
        grid.cells().forEach(cell -> weights.put(cell, random.nextInt()));
        PriorityQueue<Cell> cellsWithNeighborsUnlinked = new PriorityQueue<>(Comparator.comparingInt(weights::get));
        cellsWithNeighborsUnlinked.add(grid.randomCell());

        while (!cellsWithNeighborsUnlinked.isEmpty()) {
            Cell cell = cellsWithNeighborsUnlinked.peek();
            Optional<Cell> unlinkedNeighborCell = random(cell.neighborsWithoutLinks());
            if (unlinkedNeighborCell.isPresent()) {
                cell.link(unlinkedNeighborCell.get());
                cellsWithNeighborsUnlinked.add(unlinkedNeighborCell.get());
            } else {
                cellsWithNeighborsUnlinked.remove(cell);
            }
        }
    }

    public static void threeSetPrimsMaze(Grid grid) {
        Set<Cell> in = new HashSet<>(), frontier = new HashSet<>(), out = new HashSet<>();
        Cell cell = grid.randomCell();
        in.add(cell);
        frontier.addAll(cell.neighbors());
        out.addAll(grid.cells());
        out.removeAll(frontier);
        out.removeAll(in);

        while (!frontier.isEmpty()) {
            // Remove from frontier at random
            cell = random(frontier).get();
            frontier.remove(cell);

            // Add to in set, link this cell to a random in neighbor
            in.add(cell);
            List<Cell> inNeighbors = cell.neighbors();
            inNeighbors.retainAll(in);
            Cell inCellNeighbor = random(inNeighbors).get();
            cell.link(inCellNeighbor);

            // Move out neighbors to frontier set
            List<Cell> remainingNeighbors = cell.neighbors();
            remainingNeighbors.retainAll(out);
            out.removeAll(remainingNeighbors);
            frontier.addAll(remainingNeighbors);
        }
    }

    // Always selects first cell in list, creates very long straight paths much like
    // a corn field
    public static void selectingOldestCellGrowingMaze(Grid grid) {
        growingTreeMaze(grid, SELECT_MOST_OLDEST);
    }

    public static void selectingMedianCellGrowingMaze(Grid grid) {
        growingTreeMaze(grid, SELECT_MIDDLE);
    }

    public static void selectingMostDistanceCellGrowingMaze(Grid grid) {
        growingTreeMaze(grid, SAME_CELL_UNTIL_NO_MORE_NEIGHBOR_UNLINKED_SELECTOR(SELECT_RANDOM));
    }

    public static void mixOfSelectorsMaze(Grid grid, SelectorItem... selectors) {
        if (selectors == null || selectors.length == 0)
            growingTreeMaze(grid, SELECT_RANDOM);
        else
            growingTreeMaze(grid, MIX_OF_SELECTORS(selectors));
    }

    private static void growingTreeMaze(Grid grid, Function<List<Cell>, Cell> selectingFunction) {
        List<Cell> cellsWithNeighborsUnlinked = new ArrayList<>();
        cellsWithNeighborsUnlinked.add(grid.randomCell());

        while (!cellsWithNeighborsUnlinked.isEmpty()) {
            Cell cell = selectingFunction.apply(cellsWithNeighborsUnlinked);
            Optional<Cell> unlinkedNeighborCell = random(cell.neighborsWithoutLinks());
            if (unlinkedNeighborCell.isPresent()) {
                cell.link(unlinkedNeighborCell.get());
                cellsWithNeighborsUnlinked.add(unlinkedNeighborCell.get());
            } else {
                cellsWithNeighborsUnlinked.remove(cell);
            }
        }
    }

    public static void ellersMaze(Grid grid) {
        Random random = new Random(System.nanoTime());
        Map<Cell, Set<Cell>> cellGroups = new HashMap<>();
        for (Cell[] row : grid.grid) {
            for (Cell cell : row) {
                // Create single cell group if not in group already
                cellGroups.computeIfAbsent(cell, c -> new HashSet<>(Arrays.asList(c)));

                // Attempting to merge groups horizontally
                Optional<Cell> westNeighbor = cell.getNeighbor(Direction.WEST);
                if (westNeighbor.isPresent()) {
                    Set<Cell> westNeighborGroup = cellGroups.get(westNeighbor.get());
                    Set<Cell> cellGroup = cellGroups.get(cell);

                    // Ensure cell groups are different before linking and merging, and the last row
                    // to ensure the those cell groups can be reached
                    if (cellGroup != westNeighborGroup
                            && (!cell.hasNeighbor(Direction.SOUTH) || random.nextInt(2) == 0)) {
                        cell.link(westNeighbor.get());
                        mergeCellGroups(cellGroups, cellGroup, westNeighborGroup);
                    }
                }
            }

            // Each group on this row must have a south cell linked unless last row
            int rowIndex = row[0].row;
            Set<Set<Cell>> rowCellGroups = Arrays.asList(row).stream().map(cellGroups::get)
                    .map(cellGroup -> getCellsInRow(cellGroup, rowIndex)).collect(Collectors.toSet());
            for (Set<Cell> rowCellGroup : rowCellGroups) {
                Cell groupCell = random(rowCellGroup).get();
                Optional<Cell> southCellOption = groupCell.getNeighbor(Direction.SOUTH);
                southCellOption.ifPresent(southCell -> {
                    groupCell.link(southCell);
                    rowCellGroup.add(southCell);
                    cellGroups.put(southCell, rowCellGroup);
                });
            }
        }
    }

    private static Set<Cell> getCellsInRow(Set<Cell> group, int row) {
        return group.stream().filter(cell -> cell.row == row).collect(Collectors.toSet());
    }

    public static void recursiveDivision(Grid grid) {
        for (Cell cell : grid)
            cell.neighbors().forEach(neighbor -> cell.link(neighbor, false));
        recursiveDivide(new DividingComponents(grid, new DividingCoordinates(0, 0, grid.rows, grid.columns),
                new Random(System.nanoTime())));
    }

    private static void recursiveDivide(DividingComponents gridInfo) {
        // || (gridInfo.height() < 5 && gridInfo.width() < 5 && gridInfo.rand.nextInt(4)
        // == 0) creates potential rooms of those dimensions
        if (gridInfo.height() <= 1 || gridInfo.width() <= 1)
            return;
        if (gridInfo.height() > gridInfo.width())
            recursiveDivideHorizontally(gridInfo);
        else
            recursiveDivideVertically(gridInfo);
    }

    private static void recursiveDivideHorizontally(DividingComponents gridInfo) {
        int divideSouth = gridInfo.rand.nextInt(gridInfo.height() - 1);
        int passageAt = gridInfo.rand.nextInt(gridInfo.width());

        for (int col = 0; col < gridInfo.width(); col++) {
            if (col == passageAt)
                continue;
            Cell cell = gridInfo.grid.get(gridInfo.row() + divideSouth, gridInfo.col() + col);
            cell.unlinkNeighbor(Direction.SOUTH);
        }

        recursiveDivide(new DividingComponents(gridInfo.grid,
                new DividingCoordinates(gridInfo.row(), gridInfo.col(), divideSouth + 1, gridInfo.width()),
                gridInfo.rand));
        recursiveDivide(new DividingComponents(gridInfo.grid, new DividingCoordinates(gridInfo.row() + divideSouth + 1,
                gridInfo.col(), gridInfo.height() - divideSouth - 1, gridInfo.width()), gridInfo.rand));
    }

    private static void recursiveDivideVertically(DividingComponents gridInfo) {
        int divideEast = gridInfo.rand.nextInt(gridInfo.width() - 1);
        int passageAt = gridInfo.rand.nextInt(gridInfo.height());

        for (int row = 0; row < gridInfo.height(); row++) {
            if (passageAt == row)
                continue;
            Cell cell = gridInfo.grid.get(gridInfo.row() + row, gridInfo.col() + divideEast);
            cell.unlinkNeighbor(Direction.EAST);
        }

        recursiveDivide(new DividingComponents(gridInfo.grid,
                new DividingCoordinates(gridInfo.row(), gridInfo.col(), gridInfo.height(), divideEast + 1),
                gridInfo.rand));
        recursiveDivide(new DividingComponents(gridInfo.grid, new DividingCoordinates(gridInfo.row(),
                gridInfo.col() + divideEast + 1, gridInfo.height(), gridInfo.width() - divideEast - 1), gridInfo.rand));
    }

    private static class DividingComponents {
        final Grid grid;
        final DividingCoordinates coordinates;
        final Random rand;

        public DividingComponents(Grid grid, DividingCoordinates coordinates, Random rand) {
            this.grid = grid;
            this.coordinates = coordinates;
            this.rand = rand;
        }

        public int width() {
            return coordinates.width;
        }

        public int height() {
            return coordinates.height;
        }

        public int row() {
            return coordinates.row;
        }

        public int col() {
            return coordinates.col;
        }

        public String toString() {
            return coordinates.toString();
        }
    }

    private static class DividingCoordinates {
        final int row, col, height, width;

        public DividingCoordinates(int row, int col, int height, int width) {
            this.row = row;
            this.col = col;
            this.height = height;
            this.width = width;
        }

        @Override
        public String toString() {
            return String.format("[Row: %d, Col: %d, Height: %d, Width: %d]", row, col, height, width);
        }
    }

    public static <T> Optional<Integer> randomIndex(List<? extends T> list) {
        if (list == null || list.isEmpty())
            return Optional.empty();
        Random random = new Random(System.nanoTime());
        return Optional.of(random.nextInt(list.size()));
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

    public long countUnreachableCells() {
        return countUnreachableCells(randomCell());
    }

    public long countUnreachableCells(Cell startingPoint) {
        Set<Cell> seen = new HashSet<>();
        Queue<Cell> queue = new LinkedList<>();
        queue.add(startingPoint);
        while (!queue.isEmpty()) {
            Cell cell = queue.poll();
            List<Cell> unvisitedLinks = cell.links().stream().filter(c -> !seen.contains(c)).collect(toList());
            seen.addAll(unvisitedLinks);
            queue.addAll(unvisitedLinks);
        }
        return size() - seen.size();
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

    @Override
    public Iterator<Cell> iterator() {
        return new GridIterator(0, 0);
    }

    private class GridIterator implements Iterator<Cell> {
        private int row, col;

        public GridIterator(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public boolean hasNext() {
            return row < rows && col < columns;
        }

        @Override
        public Cell next() {
            if (!hasNext())
                return null;

            Cell cell = grid[row][col];
            col++;
            if (col >= columns) {
                col = 0;
                row++;
            }
            return cell;
        }
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

    public static void main(String[] args) {
        Grid grid = new Grid(100, 100);
        Grid.houstonsMaze(grid);
        System.out.println(grid.gridString());
        System.out.println(grid.countDeadEnds());
        System.out.println(grid.countUnlinkedCells());
        // Grid.recursiveBacktrackerMaze(grid);
        // Grid.braid(grid, 1);
        // System.out.println(grid.gridString());

        /*
         * Grid.recursiveBacktrackerMaze(grid);
         * System.out.println(grid.gridString());
         * grid.clear();
         * Grid.huntAndKillMaze(grid);
         * System.out.println(grid.gridString());
         * grid.clear();
         * Grid.binaryTreeMaze(grid);
         * System.out.println(grid.gridString());
         * grid.clear();
         * Grid.wilsonsMaze(grid);
         * System.out.println(grid.gridString());
         * grid.clear();
         * Grid.aldousBroderMaze(grid);
         * System.out.println(grid.gridString());
         * grid.clear();
         * Grid.simpliedPrimsMaze(grid);
         * System.out.println(grid.gridString());
         * grid.clear();
         * Grid.threeSetPrimsMaze(grid);
         * System.out.println(grid.gridString());
         * grid.clear();
         * Grid.ellersMaze(grid);
         * System.out.println(grid.gridString());
         */
    }
}