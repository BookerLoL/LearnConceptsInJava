package gametheory;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import datastructures.Pair;

/**
 * An implementation of finding the potential pure stategy nash equilibriums
 * from a given normal form game table.
 * 
 * Resources:
 * 
 * YouTube - More on Nash equilibrium | Game theory and Nash equilibrium |
 * Microeconomics | Khan Academy - By Khan Academy
 * 
 * Youtube - Game theory: Math marvels: Pure strategy Nash equilibria for 3
 * player games - By Mathsmerizing
 * 
 * 
 * @author Ethan
 * @version 1.0
 * @since 2021-04-09
 */
public class NashEquilibrium {
	/**
	 * Find all the nash equilibrium from a given normal form game
	 * 
	 * The inputs will not be modified.
	 * 
	 * @param normalForm the normal form game table
	 * @return The found nash equilibriums if there are any
	 */
	public static List<Pair<int[], double[]>> find(NormalFormGame normalForm) {
		Objects.requireNonNull(normalForm);

		// potential nash equilibriums
		List<Pair<int[], double[]>> actionsAndPayouts = normalForm.getActionPathAndPayouts();
		List<Pair<int[], double[]>> potentialNE = new LinkedList<>();
		int[] playersTotalActions = normalForm.getAllActions();

		for (Pair<int[], double[]> actionAndPayout : actionsAndPayouts) {
			if (isNashEquilibrium(actionAndPayout, normalForm, playersTotalActions)) {
				potentialNE.add(actionAndPayout);
			}
		}
		return potentialNE;
	}

	private static boolean isNashEquilibrium(Pair<int[], double[]> candidate, NormalFormGame normalForm,
			int[] playersTotalActions) {
		final int[] candidatePath = candidate.getKey();
		int[] path = candidatePath.clone();

		for (int player = 0; player < playersTotalActions.length; player++) {
			final double playerPayout = candidate.getValue()[player];
			for (int action = 0; action < playersTotalActions[player]; action++) {
				if (action == candidatePath[player]) {
					continue;
				}

				path[player] = action;
				double[] otherPayout = normalForm.getPayout(path);

				if (playerPayout < otherPayout[player]) {
					return false;
				}
			}
			path[player] = candidatePath[player];
		}

		return true;
	}

	public static void main(String[] args) {
		test1();
		test2();
	}

	private static void test1() {
		// Example: YouTube - More on Nash equilibrium | Game theory and Nash
		// equilibrium | Microeconomics | Khan Academy
		// Converted to negative numbers since years in prison is not a good thing and
		// thus a negative payout.
		NormalFormGame nf = new NormalFormGame(2, 2);
		nf.getPayout(0, 0)[0] = -3;
		nf.getPayout(0, 0)[1] = -3;
		nf.getPayout(0, 1)[0] = -1;
		nf.getPayout(0, 1)[1] = -10;

		nf.getPayout(1, 0)[0] = -10;
		nf.getPayout(1, 0)[1] = -1;
		nf.getPayout(1, 1)[0] = -2;
		nf.getPayout(1, 1)[1] = -2;

		nf.getActionPathAndPayouts().forEach(p -> {
			System.out.print(Arrays.toString(p.getKey()) + "\t");
			System.out.println(Arrays.toString(p.getValue()));
		});

		System.out.println("The nash equilibriums");
		List<Pair<int[], double[]>> lastStanding = NashEquilibrium.find(nf);
		for (Pair<int[], double[]> p : lastStanding) {
			System.out.println(Arrays.toString(p.getKey()) + "\t" + Arrays.toString(p.getValue()));
		}
	}

	private static void test2() {
		// Youtube - Game theory: Math marvels: Pure strategy Nash equilibria for 3
		// player games

		NormalFormGame nf = new NormalFormGame(3, 3, 2);

		nf.setPayout(new double[] { 2, 0, 4 }, 0, 0, 0);
		nf.setPayout(new double[] { 1, 1, 1 }, 0, 1, 0);
		nf.setPayout(new double[] { 1, 2, 3 }, 0, 2, 0);

		nf.setPayout(new double[] { 3, 2, 3 }, 1, 0, 0);
		nf.setPayout(new double[] { 0, 1, 0 }, 1, 1, 0);
		nf.setPayout(new double[] { 2, 1, 0 }, 1, 2, 0);

		nf.setPayout(new double[] { 1, 0, 2 }, 2, 0, 0);
		nf.setPayout(new double[] { 0, 0, 3 }, 2, 1, 0);
		nf.setPayout(new double[] { 3, 1, 1 }, 2, 2, 0);

		nf.setPayout(new double[] { 2, 0, 3 }, 0, 0, 1);
		nf.setPayout(new double[] { 4, 1, 2 }, 0, 1, 1);
		nf.setPayout(new double[] { 1, 1, 2 }, 0, 2, 1);

		nf.setPayout(new double[] { 1, 3, 2 }, 1, 0, 1);
		nf.setPayout(new double[] { 2, 2, 2 }, 1, 1, 1);
		nf.setPayout(new double[] { 0, 4, 3 }, 1, 2, 1);

		nf.setPayout(new double[] { 0, 0, 0 }, 2, 0, 1);
		nf.setPayout(new double[] { 3, 0, 3 }, 2, 1, 1);
		nf.setPayout(new double[] { 2, 1, 0 }, 2, 2, 1);

		nf.getActionPathAndPayouts().forEach(p -> {
			System.out.print(Arrays.toString(p.getKey()) + "\t");
			System.out.println(Arrays.toString(p.getValue()));
		});

		System.out.println("The nash equilibriums");
		List<Pair<int[], double[]>> lastStanding = NashEquilibrium.find(nf);
		for (Pair<int[], double[]> p : lastStanding) {
			System.out.println(Arrays.toString(p.getKey()) + "\t" + Arrays.toString(p.getValue()));
		}
	}
}
