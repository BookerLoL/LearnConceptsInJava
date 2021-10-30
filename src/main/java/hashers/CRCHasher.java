package hashers;

/**
 * An implementation of a CRC variant hash function.
 * 
 * @author Ethan
 *
 */
public class CRCHasher extends Hasher implements Hasher32 {
	/**
	 * Uses the CRC hash to create 32 bit hash value.
	 * 
	 * @param input A non-null input to hash
	 * @return A 32 bit hash
	 * @throws IllegalArgumentException input is null
	 */
	public int hash32(String input) {
		checkValidInput(input);

		int hash = 0;
		for (int i = 0; i < input.length(); i++) {
			int high = hash & 0xF8000000;

			hash <<= 5;
			hash ^= (high >> 27);
			hash ^= input.charAt(i);
		}

		return hash;
	}
}
