package gametheory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

import datastructures.Pair;

/**
 * An implementation of a normal form game table that is commonly seen in game
 * theory to represent payouts depending on certain actions.
 * 
 * This is a dynamic normal form table that allows N number of players and can
 * add additional players.
 * 
 * 
 * References: https://en.wikipedia.org/wiki/Normal-form_game
 * 
 * @author Ethan
 * @version 1.0
 * @since 2021-04-08
 */
public class NormalFormGame {
	/*
	 * A class to help represent a dynamic matrix as Java does not provide dynamic
	 * features like other languages do.
	 * 
	 * Each level represents a player but the last level represents the payouts of
	 * taking such actions.
	 *
	 */
	private class PlayerNode {
		List<PlayerNode> actions;
		double[] payouts;

		private PlayerNode initActions(int totalActions) {
			actions = new ArrayList<>(totalActions);
			for (int i = 0; i < totalActions; i++) {
				actions.add(new PlayerNode());
			}
			return this;
		}

		private PlayerNode initPayout(int totalPlayers) {
			payouts = new double[totalPlayers];
			return this;
		}

		private boolean isLeaf() {
			return actions == null;
		}
	}

	public static final int MIN_PLAYERS = 1;
	public static final int MIN_ACTIONS = 1;
	private static final int MIN_PLAYER_INDEX = 0;
	private PlayerNode rootPlayer; // always the 'first' player

	/**
	 *
	 * @param actions A non-empty integer list containing positive numbers
	 *                represents the possible actions each player can take.
	 * 
	 */
	public NormalFormGame(int... actions) {
		Objects.requireNonNull(actions);
		checkMinPlayerLength(actions);
		checkMinPlayerValues(actions);

		rootPlayer = constructNormalForm(actions);
	}

	private void checkMinPlayerLength(int... actions) {
		if (actions.length < MIN_PLAYERS) {
			throw new IllegalArgumentException("Should have at least one player's strategy numbers");
		}
	}

	private void checkMinPlayerValue(int action) {
		if (action < MIN_ACTIONS) {
			throw new IllegalArgumentException("Only allow positive number of actions");
		}
	}

	private void checkMinPlayerValues(int... actions) {
		for (int numActions : actions) {
			checkMinPlayerValue(numActions);
		}
	}

	private PlayerNode constructNormalForm(int... actions) {
		final int totalPlayers = actions.length;

		PlayerNode firstPlayer = new PlayerNode().initActions(actions[0]);
		PlayerNode tempPlayer;
		Queue<PlayerNode> playerQueue = new LinkedList<>();
		playerQueue.add(firstPlayer);
		for (int playerIndex = 0; playerIndex < totalPlayers; playerIndex++) {
			final int playerNumActions = actions[playerIndex];
			Queue<PlayerNode> nextPlayerQueue = new LinkedList<>();
			while (!playerQueue.isEmpty()) {
				tempPlayer = playerQueue.remove();
				tempPlayer.initActions(playerNumActions);
				nextPlayerQueue.addAll(tempPlayer.actions);
			}
			playerQueue.addAll(nextPlayerQueue);
		}

		// Init payouts after last player
		while (!playerQueue.isEmpty()) {
			tempPlayer = playerQueue.remove();
			tempPlayer.initPayout(totalPlayers);
		}

		return firstPlayer;
	}

	public NormalFormGame removeAllPlayers() {
		rootPlayer = null;
		return this;
	}

	public boolean isEmptyLobby() {
		return rootPlayer == null;
	}

	/**
	 * Adds additional players with their action values. They are appended to the
	 * already existing players.
	 * 
	 * Important note is that all the payouts will be reset to all zero values
	 * again. Any previous payout arrays will not be associated to an array.
	 * 
	 * @param A non-empty integer list containing positive numbers represents the
	 *          possible actions each player can take.
	 * 
	 * @return The updated instance of NormalForm.
	 */
	public NormalFormGame addPlayers(int... actions) {
		Objects.requireNonNull(actions);
		checkMinPlayerLength(actions);
		checkMinPlayerValues(actions);

		int[] previousActions = getAllActions();
		List<Integer> totalActions = new ArrayList<>(actions.length + previousActions.length);
		for (int action : previousActions) {
			totalActions.add(action);
		}
		for (int action : actions) {
			totalActions.add(action);
		}

		removeAllPlayers();
		int[] newActions = totalActions.stream().mapToInt(i -> i).toArray();
		rootPlayer = constructNormalForm(newActions);
		return this;
	}

	/**
	 * The total amount of players in the normal form.
	 * 
	 * @return If empty, return 0 otherwise the total number of players.
	 */
	public int getTotalPlayers() {
		if (isEmptyLobby()) {
			return 0;
		}

		int totalPlayers = 0;
		for (PlayerNode tempPlayer = rootPlayer; !tempPlayer.isLeaf(); totalPlayers++) {
			tempPlayer = tempPlayer.actions.get(0);
		}
		return totalPlayers;
	}

	/**
	 * Gets the total actions the player can take.
	 * 
	 * @param player The player, which can be 0 <= player < totalPlayers
	 * @return If is a valid player index, returns total actions for that player
	 *         otherwise 0 for invalid player index.
	 */
	public int getTotalActions(int player) {
		checkPlayerIndex(player);

		PlayerNode temp = rootPlayer;
		while (player != MIN_PLAYER_INDEX) {
			temp = temp.actions.get(0);
			player--;
		}
		return temp.actions.size();
	}

	private void checkPlayerIndex(int player) {
		if (player < MIN_PLAYER_INDEX && player >= getTotalPlayers()) {
			throw new IllegalArgumentException("Player must be 0 <= player < " + getTotalPlayers() + " total players");
		}
	}

	/**
	 * Retrieve a list of number of actions from all players starting from the first
	 * player to the last player.
	 * 
	 * @return a list of number of actions from each player.
	 */
	public int[] getAllActions() {
		if (isEmptyLobby()) {
			return new int[0];
		}

		int[] playersActions = new int[getTotalPlayers()];
		PlayerNode temp = rootPlayer;
		for (int player = 0; player < playersActions.length; player++, temp = temp.actions.get(0)) {
			playersActions[player] = temp.actions.size();
		}
		return playersActions;
	}

	/**
	 * Retrieve a specific payout based on the action path to get there. Please be
	 * warned that this is the in-memory payout. So any changes to the results will
	 * affect the payout internally.
	 * 
	 * 
	 * @param actionPath The path to take to get to the payout.
	 * @return If the game has players then returns the in-memory payout, otherwise
	 *         returns an empty array.
	 */
	public double[] getPayout(int... actionPath) {
		return getPayout(true, actionPath);
	}

	/**
	 * Retrieve a specific payout based on the action path to get there. You are
	 * able to specify whether you want the in-memory payout where you can make
	 * modifications directly or get a copy of the payout.
	 * 
	 * 
	 * @param inMemory   Whether or not you want the actual in memory payout or a
	 *                   copy of it.
	 * @param actionPath The path to take to get to the payout.
	 * @return If the game has players then returns the payout, otherwise returns an
	 *         empty array.
	 */
	public double[] getPayout(boolean inMemory, int... actionPath) {
		checkValidActionPath(actionPath);

		if (isEmptyLobby()) {
			return new double[0];
		}

		double[] payouts = getNode(actionPath).payouts;
		return inMemory ? payouts : payouts.clone();
	}

	private void checkValidActionPath(int... actionPath) {
		if (actionPath == null || actionPath.length == 0 || actionPath.length != getTotalPlayers()) {
			throw new IllegalArgumentException(
					"Action path must be non-null positive length array with the same length of total players");
		}
	}

	/**
	 * Set an entire payout to the desired new payout values. Please note that the
	 * argument payout will be cloned. Need a value action path to replace the
	 * payout.
	 * 
	 * @param payout     The new payout that should be the same size as the old
	 *                   payout.
	 * @param actionPath The path to reach the payout.
	 */
	public void setPayout(double[] payout, int... actionPath) {
		Objects.requireNonNull(payout);
		Objects.requireNonNull(actionPath);
		checkInvalidPayoutSize(payout);
		checkValidActionPath(actionPath);
		if (isEmptyLobby()) {
			return;
		}

		getNode(actionPath).payouts = payout.clone();
	}

	private PlayerNode getNode(int... actionPath) {
		PlayerNode tempPlayer = rootPlayer;
		for (int actionIndex = 0; actionIndex < actionPath.length; actionIndex++) {
			int action = actionPath[actionIndex];
			checkInvalidAction(action, tempPlayer.actions.size());
			tempPlayer = tempPlayer.actions.get(action);
		}
		return tempPlayer;
	}

	private void checkInvalidAction(int action, int totalActions) {
		if (action < 0 || action >= totalActions) {
			throw new IllegalArgumentException("Action must be 0 <= action < " + totalActions);
		}
	}

	private void checkInvalidPayoutSize(double[] payout) {
		if (payout.length != getTotalPlayers()) {
			throw new IllegalArgumentException("Invalid payout length. Should be the same length as getTotalPlayers()");
		}
	}

	/**
	 * Retrieve the in-memory payouts. From left to right of the branches, similar
	 * to taking the first most paths first.
	 * 
	 * @return list of payouts if there exists any
	 */
	public List<double[]> getPayouts() {
		if (isEmptyLobby()) {
			return new ArrayList<>();
		}

		List<double[]> payouts = new ArrayList<>();
		getAllPayoutsHelper(rootPlayer, payouts);
		return payouts;
	}

	private void getAllPayoutsHelper(PlayerNode player, List<double[]> payouts) {
		if (player.isLeaf()) {
			payouts.add(player.payouts);
			return;
		}

		for (PlayerNode nextPlayer : player.actions) {
			getAllPayoutsHelper(nextPlayer, payouts);
		}
	}

	/**
	 * Retrieve both a string format of the possible action paths and their
	 * associated payout vector from left to right. Will retrieve the in-memory copy
	 * of the payouts, so careful on modifications.
	 * 
	 * This is useful to use for applying IESDS or other related algorithms. Each
	 * action path is similar to accessing a matrix position.
	 * 
	 * @return If non-empty, retrieves the list of all possible actions and their
	 *         associated payout otherwise an empty list.
	 */
	public List<Pair<int[], double[]>> getActionPathAndPayouts() {
		return getActionPathAndPayouts(true);
	}

	/**
	 * Retrieve both action paths and their associated payout from left to right.
	 * Able to retrieve the in-memory payout otherwise receive a copy of it.
	 * 
	 * @param inMemory Whether or not you want the actual in-memory payout or a copy
	 *                 of it.
	 * @return If non-empty, retrieves the list of all possible actions and their
	 *         associated payout otherwise an empty list.
	 */
	public List<Pair<int[], double[]>> getActionPathAndPayouts(boolean inMemory) {
		if (isEmptyLobby()) {
			return new ArrayList<>();
		}

		List<Pair<int[], double[]>> pathAndPayouts = new ArrayList<>();
		int[] path = new int[getTotalPlayers()];
		if (inMemory) {
			getAllActionPathsAndPayoutsHelper(rootPlayer, path, 0, pathAndPayouts);
		} else {
			getAllActionPathsAndPayoutsCopyHelper(rootPlayer, path, 0, pathAndPayouts);
		}
		return pathAndPayouts;
	}

	private void getAllActionPathsAndPayoutsHelper(PlayerNode player, int[] actionPath, int playerIndex,
			List<Pair<int[], double[]>> info) {
		if (player.isLeaf()) {
			info.add(new Pair<>(actionPath.clone(), player.payouts));
			return;
		}

		for (int i = 0; i < player.actions.size(); i++) {
			actionPath[playerIndex] = i;
			getAllActionPathsAndPayoutsHelper(player.actions.get(i), actionPath, playerIndex + 1, info);
		}
	}

	private void getAllActionPathsAndPayoutsCopyHelper(PlayerNode player, int[] actionPath, int playerIndex,
			List<Pair<int[], double[]>> info) {
		if (player.isLeaf()) {
			info.add(new Pair<>(actionPath.clone(), player.payouts.clone()));
			return;
		}

		for (int i = 0; i < player.actions.size(); i++) {
			actionPath[playerIndex] = i;
			getAllActionPathsAndPayoutsHelper(player.actions.get(i), actionPath, playerIndex + 1, info);
		}
	}
}
