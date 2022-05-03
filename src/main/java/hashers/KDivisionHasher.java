package hashers;

import test.Validator;

/**
 * An implementation of Knuth's variant on the Cormen's division hash method.
 * 
 * @author Ethan
 *
 */
public class KDivisionHasher {
	private static final String ZERO_DIVISOR_MESSAGE = "The divisor cannot be zero.";

	/**
	 * Uses the Knuth's divison hash to create 32 bit hash value.
	 * 
	 * @param input       A non-null input to hash
	 * @param primeNumber A non-zero number that should be a prime number for better
	 *                    results.
	 * @return A 32 bit hash
	 * @throws IllegalArgumentException if the primeNumber is zero.
	 */
	public static int hash32(int value, int primeNumber) {
		checkValidDivisor(primeNumber);
		return (value * (value + 3)) % primeNumber;
	}

	/**
	 * Uses the Knuth's divison hash to create 64 bit hash value.
	 * 
	 * @param input       A non-null input to hash
	 * @param primeNumber A non-zero number that should be a prime number for better
	 *                    results.
	 * @return A 32 bit hash
	 * @throws IllegalArgumentException if the primeNumber is zero.
	 */
	public static long hash64(long value, long primeNumber) {
		checkValidDivisor(primeNumber);
		return (value * (value + 3)) % primeNumber;
	}

	private static void checkValidDivisor(long number) {
		Validator.checkValid(number != 0, ZERO_DIVISOR_MESSAGE);
	}
}
