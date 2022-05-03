package hashers;

/**
 * An implementation of the SDBM hash function
 * 
 * @author Ethan
 *
 */
public class SDBMHasher extends Hasher implements Hasher64 {
	/**
	 * Uses the SDBM hash to create 64 bit hash value.
	 * 
	 * @param input A non-null input to hash
	 * @return A 64 bit hash
	 * @throws IllegalArgumentException input is null
	 */
	public static long hash64(String input) {
		checkValidInput(input);

		long hash = 0;
		for (int i = 0; i < input.length(); i++) {
			hash = input.charAt(i) + (hash << 6) + (hash << 16) - hash;
		}
		return hash;
	}
}
