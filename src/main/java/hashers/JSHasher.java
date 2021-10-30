package hashers;

/**
 * Justin Sobel hash function
 * 
 * @author Ethan
 *
 */
public class JSHasher extends Hasher implements Hasher64 {

	/**
	 * Uses the JS hash to create 64 bit hash value.
	 * 
	 * @param input A non-null input to hash
	 * @return A 64 bit hash
	 * @throws IllegalArgumentException input is null
	 */
	public static long hash64(String input) {
		checkValidInput(input);

		long hash = 1315423911;
		for (int i = 0; i < input.length(); i++) {
			hash ^= ((hash << 5) + input.charAt(i) + (hash >> 2));
		}
		return hash;
	}
}
