package mazes;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;

public class WeightedDistanceGrid extends DistanceGrid {
    public static final int DEFAULT_WEIGHT = 1;
    protected Map<Cell, Integer> weights;

    public WeightedDistanceGrid(int rows, int columns) {
        super(rows, columns);
        weights = new HashMap<>();
    }

    public void calculateDistances(Cell startingPoint) {
        Objects.requireNonNull(startingPoint);

        distances.put(startingPoint, DEFAULT_DISTANCE);
        Queue<Cell> queue = new PriorityQueue<Cell>(Comparator.comparing(this::distanceOfOrElseDefault));
        queue.add(startingPoint);
        while (!queue.isEmpty()) {
            Cell cell = queue.poll();
            int currentDistance = distances.getOrDefault(cell, DEFAULT_DISTANCE);
            for (Cell link : cell.links()) {
                int weightedDistance = weightOfOrElseDefault(link) + currentDistance;

                if (!distances.containsKey(link) || weightedDistance < distances.get(link)) {
                    this.distances.put(link, weightedDistance);
                    queue.add(link);
                }
            }
        }
    }

    public void setWeight(Cell cell, int weight) {
        Objects.requireNonNull(cell);
        weights.put(cell, weight);
    }

    public Optional<Integer> weightOf(Cell cell) {
        return Optional.ofNullable(weights.get(cell));
    }

    public Integer weightOfOrElse(Cell cell, int defaultValue) {
        return weights.getOrDefault(cell, defaultValue);
    }

    public Integer weightOfOrElseDefault(Cell cell) {
        return weights.getOrDefault(cell, DEFAULT_WEIGHT);
    }

    public void clearWeights() {
        weights.clear();
    }

    public static void main(String[] args) {
        WeightedDistanceGrid grid = new WeightedDistanceGrid(5, 5);
        Grid.sideWinderMaze(grid);
        grid.setWeight(grid.randomCell(), 100);
        grid.setWeight(grid.randomCell(), 100);
        grid.setWeight(grid.randomCell(), 100);
        grid.calculateDistances(grid.randomCell());
        System.out.println(grid.gridString());
    }
}
