package utils;

/**
 * This is utility class to manipulate bits.
 *
 * These are all essentially wrapper functions to provide users who don't know 
 * bit manipulation an API to use.
 *
 * Note: TODO - still working on documentation and may change underlying implementation of some of the methods
 * 
 * @author Ethan
 * @version 1.0
 * @since 2022-07-10
 */
public class BitUtil {
	/**
	 * Private constructor to prevent initialization
	 */
	private BitUtil() {}

	/**
	 * The bit manipulation equivalence of {@code number % 2 == 0}
	 *
	 * @param number Number to check if its even
	 * @return {@code true} if number is even otherwise false
	 */
	public static boolean isEven(int number) {
		return (number & 1) == 0;
	}

	/**
	 * Bitwise AND (&)
	 *
	 * @param left The number left of the & symbol
	 * @param right The number right of the & symbol
	 * @return The bitwise AND result
	 */
	public static int and(int left, int right) {
		return left & right;
	}

	/**
	 * Bitwise OR (|)
	 *
	 * @param left The number left of the | symbol
	 * @param right The number right of the | symbol
	 * @return The bitwise OR result
	 */
	public static int or(int left, int right) {
		return left | right;
	}

	/**
	 * Bitwise XOR (^)
	 *
	 * @param left The number left of the ^ symbol
	 * @param right The number right of the ^ symbol
	 * @return The bitwise XOR result
	 */
	public static int xor(int left, int right) {
		return left ^ right;
	}

	/**
	 * Shifts the bits left by the shift amount
	 *
	 * @param number The number to shift the bits
	 * @param shiftAmount The number of left shifts
	 * @return The number left shifted by the shift amount
	 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.19"> Java Specs of left shift <a/>
	 */
	public static int leftShift(int number, int shiftAmount) {
		return number << shiftAmount;
	}

	/**
	 * Shifts the bits right by the shift amount
	 *
	 * @param number The number to shift the bits
	 * @param shiftAmount The number of right shifts
	 * @return The number right shifted by the shift amount
	 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.19"> Java Specs of right shift <a/>
	 */
	public static int rightShift(int number, int shiftAmount) {
		return number >> shiftAmount;
	}

	/**
	 * Bitwise Complement (~)
	 *
	 * @param number The number to complement
	 * @return The complement of the given number
	 */
	public static int complement(int number) {
		return ~number;
	}

	public static boolean isBitSet(int number, int position) {
		return (number & (1 << position - 1)) > 0;
	}

	public static int setBit(int number, int position) {
		return number | (1 << position - 1);
	}

	public static int clearBit(int number, int position) {
		return number & ~(1 << position - 1);
	}

	public static int toggleBit(int number, int position) {
		return number ^ (1 << position - 1);
	}

	public static int toggleRightMostOneBit(int number) {
		return number & number - 1;
	}

	public static int isolateRightMostOneBit(int number) {
		return number & -number;
	}

	public static int isolateRightMostZeroBit(int number) {
		return ~number & number + 1;
	}

	public static int getMaskBits(int fromMinBitIndex, int toMaxBitIndex) {
		if (toMaxBitIndex > 31 || fromMinBitIndex < 0) {
			throw new IllegalArgumentException("min bit index has to be >= 0 and max bit index <= 31, min bit index: "
					+ fromMinBitIndex + "\tmax bit index: " + toMaxBitIndex);
		}

		if (fromMinBitIndex > toMaxBitIndex) { // swap if bad input
			int temp = fromMinBitIndex;
			fromMinBitIndex = toMaxBitIndex;
			toMaxBitIndex = temp;
		}

		// left side of bit mask + bit mask + right side of bit max
		return Integer.valueOf("0".repeat(31 - toMaxBitIndex) + "1".repeat(toMaxBitIndex - fromMinBitIndex)
				+ "0".repeat(fromMinBitIndex), 2);
	}
}
