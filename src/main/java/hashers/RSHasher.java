package hashers;

/**
 * Robert Sedgewicks Hash Function
 * 
 * @author Ethan
 *
 */
public class RSHasher extends Hasher implements Hasher64 {

	/**
	 * Uses the Robert Sedgewicks hash to create 64 bit hash value.
	 * 
	 * @param input A non-null input to hash
	 * @return A 64 bit hash
	 * @throws IllegalArgumentException input is null
	 */
	public static long hash64(String input) {
		long b = 378551;
		long a = 63689;
		long hash = 0;

		for (int i = 0; i < input.length(); i++) {
			hash *= a + input.charAt(i);
			a *= b;
		}

		return hash;
	}
}
