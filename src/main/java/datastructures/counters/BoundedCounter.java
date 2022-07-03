package datastructures.counters;

/**
 * An implementation of a bounded counter where it's incrementing positively or
 * negatively will wrap around to the end or start.
 * 
 * Important: start and end are inclusive so be careful of off by 1 situation.
 * This also results in the range function being +1 than the standard range
 * result.
 * 
 * This is primarily useful if you want to start at a certain index and wrap
 * around until you have iterated through all the values you need.
 * 
 * Resources:
 * https://stackoverflow.com/questions/14415753/wrap-value-into-range-min-max-without-division
 * 
 * @author Ethan
 * @version 1.0
 * @since 2021-04-09
 */
public class BoundedCounter {
	protected int startBound;
	protected int endBound;

	protected int currentPos;
	protected int startPos;
	protected long range;

	public BoundedCounter(int inclusiveStart, int inclusiveEnd) {
		this(inclusiveStart, inclusiveEnd, inclusiveStart, inclusiveStart);
	}

	public BoundedCounter(int inclusiveStart, int inclusiveEnd, int startPos) {
		this(inclusiveStart, inclusiveEnd, startPos, startPos);
	}

	public BoundedCounter(int inclusiveStart, int inclusiveEnd, int startPos, int currentPos) {
		if (inclusiveStart > inclusiveEnd) {
			throw new IllegalArgumentException("Need to have start <= end");
		}

		this.startBound = inclusiveStart;
		this.endBound = inclusiveEnd;

		checkInvalidPosition(startPos);
		checkInvalidPosition(currentPos);

		this.startPos = startPos;
		this.currentPos = currentPos;

		// We are inclusive for end bound, so cases like (-3, -1) range = 3 not 2, (0,
		// 3) range = 4 not 3.
		range = ((long) getEndBound()) - getStartBound() + 1;
	}

	private void checkInvalidPosition(int pos) {
		if (pos < getStartBound() || pos > getEndBound()) {
			throw new IllegalArgumentException(String
					.format("Position must be between: %d and %d, requested position: %d", startBound, endBound, pos));
		}
	}

	/**
	 * Increment the counter by 1
	 */
	public void increment() {
		increment(1);
	}

	/**
	 * Can increment the counter by as much as you want. Can be any integer value to
	 * increment including negatives.
	 * 
	 * @param positions the number of positions you want the counter to increment
	 *                  by.
	 */
	public void increment(int positions) {
		if (positions == 0 || getRange() == 0) {
			return;
		}

		long nextPos = getCurrentPosition() + positions;
		if (nextPos < getStartBound()) {
			currentPos = getEndBound() - getWithinRangeDiff(nextPos, getStartBound());
		} else if (nextPos > getEndBound()) {
			currentPos = getStartBound() + getWithinRangeDiff(nextPos, getEndBound());
		} else {
			currentPos = (int) nextPos;
		}
	}

	/**
	 * Decrement the counter by 1
	 */
	public void decrement() {
		increment(-1);
	}

	/**
	 * Can decrement the counter by as much as you want. Can be any integer value to
	 * decrement including negatives.
	 * 
	 * @param positions the number of positions you want the counter to decrement
	 *                  by.
	 */
	public void decrement(int positions) {
		increment(-positions);
	}

	private int getWithinRangeDiff(long nextPos, int boundary) {
		// subtract 1 to allow inclusive boundary value since this equation will always
		// yield a positive number given that |boundary| < |nextPos|
		int diff = (int) Math.abs(Math.abs(nextPos) - Math.abs(boundary)) - 1;
		if (diff >= getRange()) {
			diff %= getRange();
		}
		return diff;
	}

	/**
	 * 
	 * @return The lowest value the counter can be.
	 */
	public int getStartBound() {
		return startBound;
	}

	/**
	 * 
	 * @return The highest value the counter can be.
	 */
	public int getEndBound() {
		return endBound;
	}

	/**
	 * 
	 * @return The inclusive difference between the end and start bound. Will be +1
	 *         from the standard range formula.
	 */
	public long getRange() {
		return range;
	}

	/**
	 * 
	 * @return The start position of the counter.
	 */
	public int getStartPosition() {
		return startPos;
	}

	/**
	 * Update the start position of the counter.
	 * 
	 * @param startPosition Must be a value between the start and end boundaries.
	 */
	public void setStartPosition(int startPosition) {
		checkInvalidPosition(startPos);
		this.startPos = startPosition;
	}

	/**
	 * 
	 * @return The current position of the counter.
	 */
	public int getCurrentPosition() {
		return currentPos;
	}

	/**
	 * Update the current position of the counter.
	 * 
	 * @param currentPosition Must be a value between the start and end boundaries.
	 */
	public void setCurrentPosition(int currentPosition) {
		checkInvalidPosition(currentPosition);
		this.currentPos = currentPosition;
	}

	public String toString() {
		return String.format("Start bound: %d, End bound: %d, Start position: %d, Current Position: %d",
				getStartBound(), getEndBound(), getStartPosition(), getCurrentPosition());
	}
}
