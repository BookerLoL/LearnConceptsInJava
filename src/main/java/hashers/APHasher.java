package hashers;

/**
 * An implementation of the AP hash function
 * 
 * @author Ethan
 *
 */
public class APHasher extends Hasher implements Hasher64 {
	/**
	 * Uses the AP hash to create a 64 bit hash value
	 * 
	 * @param input Non-null input to hash
	 * @return A 64 bit hash
	 * @throws IllegalArgumentException input is null
	 */
	public static long hash64(String input) {
		checkValidInput(input);

		long hash = 0xAAAAAAAAL;
		for (int i = 0; i < input.length(); i++) {
			hash ^= (((i & 1) == 0) ? ((hash << 7) ^ input.charAt(i) * (hash >> 3))
					: (~((hash << 11) + (input.charAt(i) ^ (hash >> 5)))));
		}
		return hash;
	}
}
