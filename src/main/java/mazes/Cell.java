package mazes;

import java.util.*;

import static java.util.stream.Collectors.toList;
import static mazes.Direction.HORIZONTAL_DIRECTIONS;
import static mazes.Direction.VERTICAL_DIRECTIONS;

/**
 * The cell class is synonymous to a node for graph theory.
 * <p>
 * Links indicate which cells the cell may traverse to.
 * <p>
 * Neighbors indicate cells that are neighbor that the cell may consider as
 * potential links.
 *
 * @author Ethan Booker
 * @implNote Null values are rejected/not stored to avoid filtering null values.
 * @see <a href="https://weblog.jamisbuck.org/">Source of inspiration - Jamis
 *      Buck</a>
 */
public class Cell {
    private Set<Cell> links;
    private Map<Direction, Cell> neighbors;
    public final int row, col;

    /**
     * Constructor
     *
     * @param row Row position of 2D grid
     * @param col Column position of 2D grid
     */
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
        this.links = new HashSet<>();
        this.neighbors = new EnumMap<>(Direction.class);
    }

    /**
     * Bidirectional linking that allows this cell to traverse to the other cell and
     * vice-versa.
     *
     * @param cell The cell to link with.
     */
    public void link(Cell cell) {
        link(cell, true);
    }

    /**
     * Links the cell that allows for the cell to be traversed.
     * - Bidirectional allows the cell to traverse this cell.
     *
     * @param cell          The cell to link with, if null then won't link
     * @param bidirectional If true, link this cell to the given cell and
     *                      vice-versa, else only link this cell to the given cell
     */
    public void link(Cell cell, boolean bidirectional) {
        if (cell == null) {
            return;
        }

        links.add(cell);
        if (bidirectional) {
            cell.links.add(this);
        }
    }

    /**
     * Bidirectionally unlinks the cell.
     *
     * @param cell The cell to unlink with
     */
    public void unlink(Cell cell) {
        unlink(cell, true);
    }

    /**
     * Removes the link of the cell making it no longer traversable from this cell.
     *
     * @param cell          The cell to unlink with, if null then won't unlink.
     * @param bidirectional If true, unlinks this cell to given cell and vice-versa,
     *                      else only unlink this cell to the given cell.
     */
    public void unlink(Cell cell, boolean bidirectional) {
        if (cell == null) {
            return;
        }

        links.remove(cell);
        if (bidirectional) {
            cell.links.remove(this);
        }
    }

    /**
     * @return The total links of traversable cells, value is >= 0
     */
    public int totalLinks() {
        return links.size();
    }

    /**
     * @return All the traversable cells this cell has links to, list is always non-empty.
     * @apiNote Underlying {@link List} is an {@link ArrayList} that is modifiable.
     */
    public List<Cell> links() {
        return links.stream().collect(toList());
    }

    /**
     * @return true if the cell has no links, else false.
     */
    public boolean hasNoLinks() {
        return links.isEmpty();
    }

    /**
     * @return true if the cell has at least 1 link, else false.
     */
    public boolean hasLinks() {
        return !links.isEmpty();
    }

    /**
     * @param cell The cell to check if there is a link
     * @return true if there is a link with the given cell, else false.
     */
    public boolean hasLink(Cell cell) {
        return links.contains(cell);
    }

    /**
     * @param direction Direction to check for link.
     * @return true if there is a cell link in the given direction, else false.
     */
    public boolean hasLink(Direction direction) {
        return links.contains(getNeighbor(direction).get());
    }

    /**
     * @param cells The cells to check if there is a link.
     * @return true if there is a link for any of the given cells, else false.
     */
    public boolean hasLinksWithAnyCells(Collection<Cell> cells) {
        return cells.stream().anyMatch(links::contains);
    }

    /**
     * @param cells The cells to check if there is a link.
     * @return true if there is a link for all the given cells, else false.
     */
    public boolean hasLinksWithAllCells(Collection<Cell> cells) {
        return links.containsAll(cells);
    }

    private List<Cell> neighborsFromDirections(Collection<Direction> directions) {
        return directions.stream().map(neighbors::get).collect(toList());
    }

    /**
     * @param directions The directions to check if the cell has a link to.
     * @return true if all given directions has a linked cell, else false.
     */
    public boolean hasLinksWithAllDirection(Collection<Direction> directions) {
        return links.containsAll(neighborsFromDirections(directions));
    }

    /**
     * @param directions The directions to check if the cell has a link to.
     * @return true if any of the given directions has a linked cell, else false.
     */
    public boolean hasLinksWithAnyDirection(Collection<Direction> directions) {
        return neighborsFromDirections(directions).stream().anyMatch(links::contains);
    }

    /**
     * @param allowedDirections The directions to check if cell has a neighbor in.
     * @return The directions that have a neighbor in that direction.
     */
    public List<Direction> neighborDirections(Collection<Direction> allowedDirections) {
        return allowedDirections.stream().filter(neighbors::containsKey).collect(toList());
    }

    /**
     * @return All directions the cell currently has neighbors to.
     */
    public List<Direction> neighborDirections() {
        return new ArrayList<>(neighbors.keySet());
    }

    /**
     * Links the neighbor in that given direction if there is a neighbor.
     *
     * @param direction The direction to grab it's closest neighboring cell.
     */
    public void linkNeighbor(Direction direction) {
        link(neighbors.get(direction));
    }

    /**
     * Unlinks the neighbor in that given direction if there is a neighbor.
     *
     * @param direction The direction to grab it's closest neighboring cell.
     */
    public void unlinkNeighbor(Direction direction) {
        unlink(neighbors.get(direction));
    }

    /**
     * @param allowedDirections The directions where the neighbors are located.
     * @return Non-null list of cells that come from the given {@code allowedDirections} if there exists neighbors.
     */
    public List<Cell> neighbors(Collection<Direction> allowedDirections) {
        return allowedDirections.stream().filter(neighbors::containsKey).map(neighbors::get).collect(toList());
    }

    /**
     * @param direction The direction to get a neighbor from.
     * @return If the direction has a neighbor, it will have that neighboring cell,
     *         else empty.
     */
    public Optional<Cell> getNeighbor(Direction direction) {
        return Optional.ofNullable(neighbors.get(direction));
    }

    /**
     * @param direction The direction to check if the cell has a neighbor from
     * @return True if the direction has a neighbor, else false.
     */
    public boolean hasNeighbor(Direction direction) {
        return neighbors.containsKey(direction);
    }

    /**
     * @param neighbor  The neighbor cell to set, if null then it's ignored
     * @param direction The direction to set for neighbor
     */
    public void setNeighbor(Cell neighbor, Direction direction) {
        if (neighbor == null) {
            return;
        }
        neighbors.put(direction, neighbor);
    }

    public void removeNeighbor(Cell cell) {
        neighbors.remove(cell);
    }

    /**
     * @param direction Remove direction and corresponding neighboring cell in that
     *                  direction.
     */
    public void removeNeighbor(Direction direction) {
        neighbors.remove(direction);
    }

    public List<Cell> neighbors() {
        return neighbors.values().stream().collect(toList());
    }

    /**
     * @return All neighbor cells that have no links.
     */
    public List<Cell> neighborsWithoutLinks() {
        return neighbors.values().stream().filter(Cell::hasNoLinks).collect(toList());
    }

    /**
     * @return True if has North and South links, else false.
     */
    public boolean hasVerticalPassage() {
        return hasLinksWithAllDirection(VERTICAL_DIRECTIONS);
    }

    /**
     * @return True if has West and East links, else false.
     */

    public boolean hasHorizontalPassage() {
        return hasLinksWithAllDirection(HORIZONTAL_DIRECTIONS);
    }

    /**
     * @return True if has only 2 links and they are North and South links, else
     *         false.
     */

    public boolean hasOnlyVerticalPassage() {
        return hasLinksWithAllDirection(VERTICAL_DIRECTIONS)
                && !hasLinksWithAnyDirection(HORIZONTAL_DIRECTIONS);
    }

    /**
     * @return True if has only 2 links and they are West and East links, else
     *         false.
     */
    public boolean hasOnlyHorizontalPassage() {
        return hasLinksWithAllDirection(HORIZONTAL_DIRECTIONS)
                && !hasLinksWithAnyDirection(VERTICAL_DIRECTIONS);
    }

    @Override
    public String toString() {
        return String.format("Cell(%d, %d)", row, col);
    }
}