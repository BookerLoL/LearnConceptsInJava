package hashers;

/**
 * An implementation of the DJB Hash funciton
 * 
 * @author Ethan
 *
 */
public class DJBHasher extends Hasher implements Hasher64 {
	
	/**
	 * Uses the divison hash to create 64 bit hash value.
	 * 
	 * @param input       A non-null input to hash
	 * @param primeNumber A non-zero number that should be a prime number for better
	 *                    results.
	 * @return A 64 bit hash
	 */
	public static long hash64(String input) {
		checkValidInput(input);

		long hash = 5381;
		for (int i = 0; i < input.length(); i++) {
			hash = ((hash << 5) + hash) + input.charAt(i);
		}
		return hash;
	}
}
