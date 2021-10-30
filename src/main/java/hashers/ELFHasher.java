package hashers;

/**
 * An implementation of the ELF hash function.
 * 
 * @author Ethan
 *
 */
public class ELFHasher extends Hasher implements Hasher32 {

	/**
	 * Uses the ELF hash to create 32 bit hash value.
	 * 
	 * @param input A non-null input to hash
	 * @return A 32 bit hash
	 * @throws IllegalArgumentException input is null
	 */
	public static int hash32(String input) {
		checkValidInput(input);

		int hash = 0;
		int x = 0;
		for (int i = 0; i < input.length(); i++) {
			hash = (hash << 4) + input.charAt(0);

			x = hash & 0xF0000000;
			if (x != 0) {
				hash ^= (x >> 24);
			}
			hash &= ~x;
		}

		return hash;
	}
}
