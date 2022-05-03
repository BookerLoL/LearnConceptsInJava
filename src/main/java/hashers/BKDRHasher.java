package hashers;

import test.Validator;

/**
 * Brian Kernigham and Dennis Ritchie's hash function
 * 
 * @author Ethan
 *
 */
public class BKDRHasher extends Hasher implements Hasher32 {
	private static final String INVALID_SEED_VALUE_MESSAGE = "Number must be an alternating pattern of '1' and '3'";

	/**
	 * Calls {@link #hash32(String, int)} with a seed of 131.
	 * 
	 * @param input Non-null input to hash
	 * @return A 32 bit hashs
	 */
	public static int hash32(String input) {
		return hash32(input, 131);
	}

	/**
	 * Uses the BKDR hash to create 32 bit hash value.
	 * 
	 * @param input Non-null input to hash
	 * @param seed  Follow a regex pattern of alternating between 1 and 3, examples:
	 *              31, 131, 1313, 13131, etc
	 * @return A 32 bit hash
	 * @throws IllegalArgumentException input is null
	 * @throws IllegalArgumentException seed does not follow an alternating pattern
	 *                                  of 1 and 3.
	 */
	public static int hash32(String input, int seed) {
		checkValidInput(input);
		Validator.checkValid(isValidSeed(seed), INVALID_SEED_VALUE_MESSAGE);

		int hash = 0;
		for (int i = 0; i < input.length(); i++) {
			hash = (hash * seed) + input.charAt(i);
		}

		return (hash & 0x7FFFFFFF);
	}

	private static boolean isValidSeed(int seed) {
		String numSeed = String.valueOf(seed);

		if (!isValidNumber(numSeed.charAt(0))) {
			return false;
		}

		boolean prevIsOne = numSeed.charAt(0) == '1';
		for (int i = 1; i < numSeed.length(); i++) {
			char num = numSeed.charAt(i);

			if (!isValidNumber(num)) {
				return false;
			}

			if (prevIsOne) {
				if (num == '1') {
					return false;
				}
			} else {
				if (num == '3') {
					return false;
				}
			}
			prevIsOne = !prevIsOne;

		}
		return true;
	}

	private static final boolean isValidNumber(char ch) {
		return ch == '1' || ch == '3';
	}
}
