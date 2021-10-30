package hashers;

/**
 * The FNV hash implementation
 * 
 * @author Ethan
 *
 */
public class FNVHasher extends Hasher implements Hasher64 {
	/**
	 * FNV 64 bit hash
	 * 
	 * @param input Non-null input
	 * @return A 64 bit hash
	 * @throws IllegalArgumentException input is null.
	 */
	public static long hash64(String input) {
		checkValidInput(input);

		long fnvPrime = 0x811C9DC5L;
		long hash = 0;

		for (int i = 0; i < input.length(); i++) {
			hash *= fnvPrime;
			hash ^= input.charAt(i);
		}
		return hash;
	}
}
