package gametheory;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import datastructures.Pair;
import datastructures.counters.LoopCounter;

/**
 * An implementation of iterated elimination of weakly dominated strategies
 * (IEWDS). The order of elimination does matter, as of right now, the
 * implementation is first come first serve.
 * 
 * This implementation can handle one to multiple number of players.
 * 
 * 
 * Resources:
 * 
 * Strategic dominance - Wikipedia
 * 
 * @author Ethan
 * @version 1.0
 * @since 2021-04-10
 */
public class IEWDS {
	/**
	 * Eliminates all strategies that are weakly dominated in a given a normal form
	 * game.
	 * 
	 * The inputs will not be modified.
	 * 
	 * @param normalForm The normal form to perform the elimination.
	 * @return Only one strategy will be returned if there exists one which is
	 *         usually first come first serve.
	 */
	public static List<Pair<int[], double[]>> eliminate(NormalFormGame normalForm) {
		Objects.requireNonNull(normalForm);

		List<Pair<int[], double[]>> leftoverStrategies = normalForm.getActionPathAndPayouts();
		final int[] playersTotalActions = normalForm.getAllActions();
		// Alternatively you could just loop through all the players from 0 - (total
		// players - 1), I wanted to give an impression on how people may actually go
		// about it on paper
		LoopCounter playerCounter = new LoopCounter(0, playersTotalActions.length - 1);
		LinkedList<Pair<int[], double[]>> dominatedStrategies = new LinkedList<>();
		boolean removed = true;

		while (removed) {
			playerCounter.setLoopCount(0);
			dominatedStrategies.clear();

			while (playerCounter.getLoopCount() == 0) { // while haven't looped through all the players
				final int player = playerCounter.getCurrentPosition();

				outer: for (int action = 0; action < playersTotalActions[player]; action++) {
					List<Pair<int[], double[]>> strategies = getAllActionsFrom(player, action, leftoverStrategies);
					for (int otherAction = 0; otherAction < playersTotalActions[player]; otherAction++) {
						if (otherAction == action) {
							continue;
						}

						List<Pair<int[], double[]>> otherStrategies = getAllActionsFrom(player, otherAction,
								leftoverStrategies);

						if (isWeaklyDominating(player, strategies, otherStrategies)) {
							dominatedStrategies.addAll(otherStrategies);
							break outer;
						}
					}
				}

				if (!dominatedStrategies.isEmpty()) {
					playerCounter.setStartPosition(playerCounter.getCurrentPosition());
					break;
				}

				playerCounter.increment();
			}

			if (!dominatedStrategies.isEmpty()) {
				leftoverStrategies.removeAll(dominatedStrategies);
			} else {
				removed = false;
			}
		}

		return leftoverStrategies;
	}

	protected static List<Pair<int[], double[]>> getAllActionsFrom(int player, int strategy,
			List<Pair<int[], double[]>> actionsAndPayouts) {
		return actionsAndPayouts.stream().filter(pair -> pair.getKey()[player] == strategy)
				.collect(Collectors.toList());
	}

	protected static boolean isWeaklyDominating(int player, List<Pair<int[], double[]>> dominator,
			List<Pair<int[], double[]>> subject) {
		if (dominator == null || subject == null || dominator.isEmpty() || dominator.size() != subject.size()) {
			return false;
		}

		for (int i = 0; i < dominator.size(); i++) {
			if (dominator.get(i).getValue()[player] < subject.get(i).getValue()[player]) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		test1();
		test2();
	}

	private static void test1() {
		NormalFormGame nf = new NormalFormGame(2, 2);
		nf.setPayout(new double[] { 10, 4 }, 0, 0);
		nf.setPayout(new double[] { 5, 4 }, 0, 1);

		nf.setPayout(new double[] { 0, 1 }, 1, 0);
		nf.setPayout(new double[] { 4, 6 }, 1, 1);

		// None should be removed
		System.out.println("Remaining Paths and Values that were not elminated");
		List<Pair<int[], double[]>> lastStanding = IEWDS.eliminate(nf);
		for (Pair<int[], double[]> p : lastStanding) {
			System.out.println(Arrays.toString(p.getKey()) + "\t" + Arrays.toString(p.getValue()));
		}
	}

	private static void test2() {
		NormalFormGame nf = new NormalFormGame(3, 2);
		nf.setPayout(new double[] { 0, 1 }, 0, 0);
		nf.setPayout(new double[] { -4, 2 }, 0, 1);

		nf.setPayout(new double[] { 0, 3 }, 1, 0);
		nf.setPayout(new double[] { 3, 3 }, 1, 1);

		nf.setPayout(new double[] { -2, 2 }, 2, 0);
		nf.setPayout(new double[] { 3, -1 }, 2, 1);

		// None should be removed
		System.out.println("Remaining Paths and Values that were not elminated");
		List<Pair<int[], double[]>> lastStanding = IEWDS.eliminate(nf);
		for (Pair<int[], double[]> p : lastStanding) {
			System.out.println(Arrays.toString(p.getKey()) + "\t" + Arrays.toString(p.getValue()));
		}
	}
}
