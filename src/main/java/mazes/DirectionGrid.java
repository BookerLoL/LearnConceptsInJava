package mazes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class DistanceGrid extends Grid {
    public static final int DEFAULT_DISTANCE = 0;
    protected Map<Cell, Integer> distances;

    public DistanceGrid(int rows, int columns) {
        super(rows, columns);
        distances = new HashMap<>();
    }

    public void calculateDistances(Cell startingPoint) {
        Objects.requireNonNull(startingPoint);
        distances.put(startingPoint, DEFAULT_DISTANCE);

        Queue<Cell> queue = new LinkedList<>();
        queue.add(startingPoint);
        while (!queue.isEmpty()) {
            Cell currentCell = queue.poll();
            int distance = distances.get(currentCell);
            List<Cell> cellsToCompute = currentCell.links().stream().filter(c -> !distances.containsKey(c))
                    .collect(Collectors.toList());
            cellsToCompute.forEach(c -> distances.put(c, distance + 1));
            queue.addAll(cellsToCompute);
        }
    }

    public Optional<Cell> farthestAwayCell() {
        if (distances.isEmpty())
            return Optional.empty();
        Cell maxDistantCell = distances.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).get()
                .getKey();
        return Optional.of(maxDistantCell);
    }

    private Optional<List<Cell>> generatePath(Cell start, Cell end,
            BiFunction<Cell, Cell, Optional<List<Cell>>> pathProducer) {
        if (!contains(start) || !contains(end))
            return Optional.ofNullable(null);
        if (start == end)
            return Optional.of(Collections.emptyList());
        if (!distances.containsKey(start))
            calculateDistances(start); // Distance never calculated
        if (!distances.containsKey(end))
            return Optional.ofNullable(null); // Unable to reach end node from start after calculating distances
        return pathProducer.apply(start, end);
    }

    public Optional<List<Cell>> shortestPathOfPerfectNonWeightedMaze(Cell start, Cell end) {
        return generatePath(start, end, this::shortestPathOfPerfectNonWeightedMazesHelper);
    }

    private Optional<List<Cell>> shortestPathOfPerfectNonWeightedMazesHelper(Cell start, Cell end) {
        List<Cell> path = new ArrayList<>();
        path.add(end);
        Cell current = end;
        while (current != start) {
            for (Cell neighbor : current.links()) {
                if (distances.get(neighbor) < distances.get(current)) {
                    path.add(neighbor);
                    current = neighbor;
                    break;
                }
            }
        }
        path.add(start);
        return Optional.of(path);
    }

    public Optional<List<Cell>> shortestPathOfMaze(Cell start, Cell end) {
        return generatePath(start, end, this::shortestPathOfMazeHelper);
    }

    // TODO: may need to fix, haven't tested, probably fails with loops
    public Optional<List<Cell>> shortestPathOfMazeHelper(Cell start, Cell end) {
        List<Cell> path = new ArrayList<>();
        path.add(end);
        Cell current = end;
        Cell previous;
        while (current != start) {
            previous = current;
            for (Cell neighbor : current.links()) {
                if (distances.get(neighbor) < distances.get(current)) {
                    path.add(neighbor);
                    current = neighbor;
                }
            }
            if (previous == current)
                return Optional.ofNullable(null);
        }
        path.add(start);
        return Optional.of(path);
    }

    public void clear() {
        super.clear();
        clearDistances();
    }

    public void clearDistances() {
        distances.clear();
    }

    public Optional<Integer> distanceOf(Cell cell) {
        return Optional.ofNullable(distances.get(cell));
    }

    public Integer distanceOfOrElse(Cell cell, int defaultValue) {
        return distances.getOrDefault(cell, defaultValue);
    }

    public Integer distanceOfOrElseDefault(Cell cell) {
        return distances.getOrDefault(cell, DEFAULT_DISTANCE);
    }

    public String gridString() {
        StringBuilder output = new StringBuilder("+").append(repeat("---+", columns)).append("\n");
        final Cell emptyCell = new Cell(-1, -1);
        for (Cell[] row : grid) {
            StringBuilder top = new StringBuilder("|");
            StringBuilder bottom = new StringBuilder("+");

            for (Cell cell : row) {
                cell = cell != null ? cell : emptyCell;
                String body = String.format("%-3s", distances.getOrDefault(cell, -1));

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

    public String gridString(Set<Cell> showCellDistances) {
        StringBuilder output = new StringBuilder("+").append(repeat("---+", columns)).append("\n");
        final Cell emptyCell = new Cell(-1, -1);
        for (Cell[] row : grid) {
            StringBuilder top = new StringBuilder("|");
            StringBuilder bottom = new StringBuilder("+");

            for (Cell cell : row) {
                cell = cell != null ? cell : emptyCell;

                String body;
                if (showCellDistances.contains(cell)) {
                    body = String.format("%-3s", distances.getOrDefault(cell, -1));
                } else {
                    body = "   ";
                }

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
        DistanceGrid grid = new DistanceGrid(10, 10);
        Grid.sideWinderMaze(grid);
        Grid.braid(grid, 1);
        Cell start = grid.randomCell();
        grid.calculateDistances(start);
        // List<Cell> cellPath = grid.shortestPath(start,
        // grid.farthestAwayCell().get());
        // System.out.println(grid.gridString(cellPath.stream().collect(Collectors.toSet())));
        System.out.println(grid.gridString());
    }
}
