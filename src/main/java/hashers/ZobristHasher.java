package hashers;

import java.util.Map;
import java.util.Objects;

/**
 * An implementation of zobrist hashing.
 * 
 * Used for games maintaining evaluated previous states of a game.
 * 
 * @author Ethan
 *
 */
public class ZobristHasher extends Hasher implements Hasher64 {
	/**
	 * Uses a generic implementation of the zorbrist hashing to encode a board's
	 * state. This can be applied to other games, not just specifically for Chess.
	 * 
	 * @param board           The board to evaluate.
	 * @param pieceBitstrings A non-null mapping of each piece and their
	 *                        corresponding given encoding values that you decided
	 *                        on.
	 * @param emptySymbol     The symbol on the board to indicate there is no piece
	 *                        in that corresponding position.
	 * @return A 64 bit hash of the board.
	 */
	public static long hash64(String[][] board, Map<String, Long> pieceBitstrings, String emptySymbol) {
		checkValidInput(board);
		checkValidInput(pieceBitstrings);

		long hash = 0;
		for (String[] row : board) {
			for (String symbol : row) {
				if (isPiece(symbol, emptySymbol)) {
					// If there is no corresponding value, simply don't change the hash.
					hash ^= pieceBitstrings.getOrDefault(symbol, hash);
				}
			}
		}
		return hash;
	}

	private static boolean isPiece(String symbol, String emptySymbol) {
		return !Objects.equals(symbol, emptySymbol);
	}
}
