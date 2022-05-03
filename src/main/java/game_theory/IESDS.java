package gametheory;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import datastructures.Pair;
import datastructures.counters.LoopCounter;

/**
 * An implementation of iterated elimination of strictly dominated strategies
 * (IESDS).
 * 
 * This implementation can handle one to multiple number of players.
 * 
 * 
 * Resources:
 * 
 * YouTube - Game Theory 101: Iterated Elimination of Strictly Dominated
 * Strategies - By William Spaniel
 * 
 * Strategic dominance - Wikipedia
 * 
 * @author Ethan
 * @version 1.0
 * @since 2021-04-09
 */
public class IESDS {
	/**
	 * Eliminates all strategies that are strictly dominated in a given a normal
	 * form game.
	 * 
	 * The inputs will not be modified.
	 * 
	 * @param normalForm The normal form to perform the elimination.
	 * @return All leftover strategies that were not elminated.
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

						if (isStrictlyDominating(player, strategies, otherStrategies)) {
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

	protected static boolean isStrictlyDominating(int player, List<Pair<int[], double[]>> dominator,
			List<Pair<int[], double[]>> subject) {
		if (dominator == null || subject == null || dominator.isEmpty() || dominator.size() != subject.size()) {
			return false;
		}

		for (int i = 0; i < dominator.size(); i++) {
			if (dominator.get(i).getValue()[player] <= subject.get(i).getValue()[player]) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		test1();
		test2();
		test3();
		test4();
		test5();
	}

	private static void test1() {
		System.out.println("Running Test 1: Game Theory 101: Iterated Elimination of Strictly Dominated Strategies");
		NormalFormGame nf = new NormalFormGame(3, 3);
		// row 1
		nf.getPayout(0, 0)[0] = 13;
		nf.getPayout(0, 0)[1] = 3;
		nf.getPayout(0, 1)[0] = 1;
		nf.getPayout(0, 1)[1] = 4;
		nf.getPayout(0, 2)[0] = 7;
		nf.getPayout(0, 2)[1] = 3;

		// row 2
		nf.getPayout(1, 0)[0] = 4;
		nf.getPayout(1, 0)[1] = 1;
		nf.getPayout(1, 1)[0] = 3;
		nf.getPayout(1, 1)[1] = 3;
		nf.getPayout(1, 2)[0] = 6;
		nf.getPayout(1, 2)[1] = 2;

		// row 3
		nf.getPayout(2, 0)[0] = -1;
		nf.getPayout(2, 0)[1] = 9;
		nf.getPayout(2, 1)[0] = 2;
		nf.getPayout(2, 1)[1] = 8;
		nf.getPayout(2, 2)[0] = 8;
		nf.getPayout(2, 2)[1] = -1;

		nf.getActionPathAndPayouts().forEach(p -> {
			System.out.print(Arrays.toString(p.getKey()) + "\t");
			System.out.println(Arrays.toString(p.getValue()));
		});

		System.out.println("Remaining Paths and Values that were not elminated");
		List<Pair<int[], double[]>> lastStanding = IESDS.eliminate(nf);
		for (Pair<int[], double[]> p : lastStanding) {
			System.out.println(Arrays.toString(p.getKey()) + "\t" + Arrays.toString(p.getValue()));
		}
	}

	private static void test2() {
		System.out.println("Running Test 2: Wikipedias's example");
		NormalFormGame nf = new NormalFormGame(3, 3);
		// row 1
		nf.getPayout(0, 0)[0] = 10;
		nf.getPayout(0, 0)[1] = 4;
		nf.getPayout(0, 1)[0] = 5;
		nf.getPayout(0, 1)[1] = 3;
		nf.getPayout(0, 2)[0] = 3;
		nf.getPayout(0, 2)[1] = 2;

		// row 2
		nf.getPayout(1, 0)[0] = 0;
		nf.getPayout(1, 0)[1] = 1;
		nf.getPayout(1, 1)[0] = 4;
		nf.getPayout(1, 1)[1] = 6;
		nf.getPayout(1, 2)[0] = 6;
		nf.getPayout(1, 2)[1] = 0;

		// row 3
		nf.getPayout(2, 0)[0] = 2;
		nf.getPayout(2, 0)[1] = 1;
		nf.getPayout(2, 1)[0] = 3;
		nf.getPayout(2, 1)[1] = 5;
		nf.getPayout(2, 2)[0] = 2;
		nf.getPayout(2, 2)[1] = 8;

		nf.getActionPathAndPayouts().forEach(p -> {
			System.out.print(Arrays.toString(p.getKey()) + "\t");
			System.out.println(Arrays.toString(p.getValue()));
		});

		System.out.println("Remaining Paths and Values that were not elminated");
		List<Pair<int[], double[]>> lastStanding = IESDS.eliminate(nf);
		for (Pair<int[], double[]> p : lastStanding) {
			System.out.println(Arrays.toString(p.getKey()) + "\t" + Arrays.toString(p.getValue()));
		}
	}

	private static void test3() {
		System.out.println("Running Test 3");
		// Case where 2 remains, using only 1 player
		NormalFormGame nf = new NormalFormGame(3);
		nf.getPayout(0)[0] = 1;
		nf.getPayout(1)[0] = 2;
		nf.getPayout(2)[0] = 2;

		nf.getActionPathAndPayouts().forEach(p -> {
			System.out.print(Arrays.toString(p.getKey()) + "\t");
			System.out.println(Arrays.toString(p.getValue()));
		});

		System.out.println("Remaining Paths and Values that were not elminated");
		List<Pair<int[], double[]>> lastStanding = IESDS.eliminate(nf);
		for (Pair<int[], double[]> p : lastStanding) {
			System.out.println(Arrays.toString(p.getKey()) + "\t" + Arrays.toString(p.getValue()));
		}
	}

	private static void test4() {
		NormalFormGame nf = new NormalFormGame(2, 2);
		nf.setPayout(new double[] { 9, -2 }, 0, 0);
		nf.setPayout(new double[] { 3, 0 }, 0, 1);
		nf.setPayout(new double[] { 8, 5 }, 1, 0);
		nf.setPayout(new double[] { -1, 6 }, 1, 1);

		System.out.println("Remaining Paths and Values that were not elminated");
		List<Pair<int[], double[]>> lastStanding = IESDS.eliminate(nf);
		for (Pair<int[], double[]> p : lastStanding) {
			System.out.println(Arrays.toString(p.getKey()) + "\t" + Arrays.toString(p.getValue()));
		}
	}

	private static void test5() {
		NormalFormGame nf = new NormalFormGame(3, 2);
		nf.setPayout(new double[] { 0, 1 }, 0, 0);
		nf.setPayout(new double[] { -4, 2 }, 0, 1);

		nf.setPayout(new double[] { 0, 3 }, 1, 0);
		nf.setPayout(new double[] { 3, 3 }, 1, 1);

		nf.setPayout(new double[] { -2, 2 }, 2, 0);
		nf.setPayout(new double[] { 3, -1 }, 2, 1);

		// None should be removed
		System.out.println("Remaining Paths and Values that were not elminated");
		List<Pair<int[], double[]>> lastStanding = IESDS.eliminate(nf);
		for (Pair<int[], double[]> p : lastStanding) {
			System.out.println(Arrays.toString(p.getKey()) + "\t" + Arrays.toString(p.getValue()));
		}
	}
}
