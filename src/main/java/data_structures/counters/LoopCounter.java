package datastructures.counters;

import datastructures.Pair;

/**
 * An extension of the bounded counter where there is also a loop counter on how
 * many times you have went from iterated past the start position. The loop
 * counter can go into the negatives indicating you have been counting
 * backwards.
 * 
 * @apiNote STILL NEED TO TEST
 * 
 * @apiNote Decrementing backwards despite not doing a full loop of all the
 *          numbers will result in a decrement in loop count, so be careful of
 *          the count. The same issue can be said for incrementing.
 * 
 * @apiNote Perhaps a total numbers counter should be added to determine whether
 *          or not a full loop has actually been done through
 *          decrementing/incrementing.
 * 
 * @author Ethan
 * @version 1.0
 * @since 2021-04-09
 */
public class LoopCounter extends BoundedCounter {
	protected long loopCounter;

	public LoopCounter(int inclusiveStart, int inclusiveEnd) {
		this(inclusiveStart, inclusiveEnd, inclusiveStart, inclusiveStart);
	}

	public LoopCounter(int inclusiveStart, int inclusiveEnd, int startPos) {
		this(inclusiveStart, inclusiveEnd, startPos, startPos);
	}

	public LoopCounter(int inclusiveStart, int inclusiveEnd, int startPos, int currentPos) {
		super(inclusiveStart, inclusiveEnd, startPos, currentPos);
	}

	public void increment(int positions) {
		if (positions == 0) {
			return;
		} else if (getRange() == 1) {
			loopCounter += positions;
		}

		long nextPos = getCurrentPosition() + positions;
		if (nextPos < getStartBound()) {
			Pair<Integer, Integer> diffAndLoops = getWithinRangeDiffAndLoop(nextPos, getStartBound());
			int oldPos = getCurrentPosition();
			currentPos = getEndBound() - diffAndLoops.getKey();
			updateLoopCounter(positions, oldPos, currentPos, diffAndLoops.getValue());
		} else if (nextPos > getEndBound()) {
			Pair<Integer, Integer> diffAndLoops = getWithinRangeDiffAndLoop(nextPos, getEndBound());
			int oldPos = getCurrentPosition();
			currentPos = getStartBound() + diffAndLoops.getKey();
			updateLoopCounter(positions, oldPos, currentPos, diffAndLoops.getValue());
		} else {
			if (currentPos < getStartPosition() && nextPos >= getStartPosition()) {
				loopCounter++;
			} else if (currentPos > getStartPosition() && nextPos <= getStartPosition()) {
				loopCounter--;
			}
			currentPos = (int) nextPos;
		}
	}

	private Pair<Integer, Integer> getWithinRangeDiffAndLoop(long nextPos, int boundary) {
		// subtract 1 to allow inclusive boundary value since this equation will always
		// yield a positive number given that |boundary| < |nextPos|
		int loops = 0;
		int diff = (int) Math.abs(Math.abs(nextPos) - Math.abs(boundary)) - 1;
		if (diff >= range) {
			loops = (int) (diff / range);
			diff %= (range);
		}
		return new Pair<>(diff, loops);
	}

	private void updateLoopCounter(int positions, int oldPos, int newPos, int additionalLoops) {
		// System.out.println(positions + "\t" + oldPos + "\t" + newPos + "\t" +
		// additionalLoops);
		if (positions < 0) {
			if (newPos <= getStartPosition()) {
				loopCounter -= 1;
			}
			loopCounter -= additionalLoops;
		} else {
			if (newPos >= getStartPosition()) {
				loopCounter += 1;
			}
			loopCounter += additionalLoops;
		}
	}

	public long getLoopCount() {
		return loopCounter;
	}

	public void setLoopCount(long loopCount) {
		loopCounter = loopCount;
	}

	public String toString() {
		return super.toString() + ", Loop Count: " + getLoopCount();
	}

	public static void main(String[] args) {
		LoopCounter lc = new LoopCounter(0, 1);
		lc.increment();
		System.out.println(lc);
		lc.increment();
		System.out.println(lc);
		lc.increment();
		System.out.println(lc);
	}
}
