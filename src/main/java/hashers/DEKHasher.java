package hashers;

/**
 * An implementation of Donald E. Knuth's hash function.
 * 
 * @author Ethan
 *
 */
public class DEKHasher extends Hasher implements Hasher64 {
	/**
	 * Uses the DEK hash to create 64 bit hash value.
	 * 
	 * @param input A non-null input to hash
	 * @return A 64 bit hash
	 * @throws IllegalArgumentException input is null
	 */
	public static long hash64(String input) {
		checkValidInput(input);

		long hash = input.length();

		for (int i = 0; i < input.length(); i++) {
			hash = ((hash << 5) ^ (hash >> 27)) ^ input.charAt(i);
		}

		return hash;
	}
}
