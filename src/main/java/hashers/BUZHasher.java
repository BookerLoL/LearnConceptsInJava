package hashers;

import test.Validator;

/**
 * An implementation of the BUZ hash function.
 * 
 * @author Ethan
 *
 */
public class BUZHasher extends Hasher implements Hasher32 {
	private static final String NOT_SAME_LENGTH_INPUTS_MESSAGE_FORMAT = "The input '%s' length %d must be the same as the random numbers length: %d";

	/**
	 * Uses the BUZ hash to create 32 bit hash value.
	 * 
	 * @param input         A non-null input to hash
	 * @param randomNumbers Precomputed random numbers for each char value. The
	 *                      length of random numbers must be the same length as the
	 *                      input.
	 * @return A 32 bit hash
	 * @throws IllegalArgumentException input is null
	 * @throws IllegalArgumentException randomNumbers is null
	 * @throws IllegalArgumentException input's length is not the smae as
	 *                                  randomNumber's length.
	 */
	public static int hash32(String input, int[] randomNumbers) {
		checkValidInput(input);
		Validator.checkValid(randomNumbers != null, "Random Numbers must be non-null");
		checkSameLength(input, randomNumbers);

		int hash = 0;

		for (int i = 0; i < input.length(); i++) {
			int high = hash & 0x80000000;
			hash <<= 1;
			hash ^= (high >> 31);
			hash ^= randomNumbers[input.charAt(i)];
		}

		return hash;
	}

	private static void checkSameLength(String input, int[] randomNumbers) {
		Validator.checkValid(input.length() == randomNumbers.length, NOT_SAME_LENGTH_INPUTS_MESSAGE_FORMAT, input,
				input.length(), randomNumbers.length);
	}
}
