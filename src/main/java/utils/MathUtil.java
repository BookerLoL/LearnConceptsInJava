package utils;

import java.util.Objects;

/**
 * An extension of Java Math class where certain helpful functions that aren't
 * in the library can be found here.
 * 
 * Resources:
 * 
 * https://en.wikipedia.org/wiki/Euclidean_algorithm
 * 
 * @author Ethan
 * @version 1.0
 * @since 2021-04-10
 */
public class MathUtil {
	private MathUtil() {}
	/**
	 * Calculates the least common multiple of two integers. The output is always a
	 * positive number by convention.
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static int lcm(int a, int b) {
		if (a == 0 || b == 0) {
			return 0;
		}

		return Math.abs((a / gcd(a, b)) * b);
	}

	/**
	 * Calculates the least common multiple of two or more integers. The output is
	 * always a positive number by convention.
	 * 
	 * @param a
	 * @param b
	 * @param otherNumbers
	 * @return
	 */
	public static int lcm(int a, int b, int... otherNumbers) {
		Objects.requireNonNull(otherNumbers);
		int lcmResult = lcm(a, b);

		for (int num : otherNumbers) {
			lcmResult = lcm(lcmResult, num);
		}

		return lcmResult;
	}

	/**
	 * Calculates the greatest common denominator of two integers. The output is
	 * always a postive number by convention.
	 * 
	 * @param a
	 * @param b
	 * @return the greatest commond denominator of a and b
	 */
	public static int gcd(int a, int b) {
		// Euclidean algorithm
		if (a < 0) {
			a = Math.abs(a);
		}
		if (b < 0) {
			b = Math.abs(b);
		}

		int remainder;
		while (b != 0) {
			remainder = a % b;
			a = b;
			b = remainder;
		}
		return a;
	}

	/**
	 * Calculates the greatest common denominator of two or more integers. The
	 * output is always a postive number by convention.
	 * 
	 * 
	 * @param a
	 * @param b
	 * @param otherNumbers
	 * @return
	 */
	public static int gcd(int a, int b, int... otherNumbers) {
		Objects.requireNonNull(otherNumbers);
		int gcdResult = gcd(a, b);

		for (int num : otherNumbers) {
			gcdResult = gcd(gcdResult, num);

			if (gcdResult == 1) {
				break;
			}
		}

		return gcdResult;
	}

	public static void main(String[] args) {
		System.out.println(MathUtil.gcd(10, 2, null));
		System.out.println(MathUtil.lcm(6, 5));
	}
}
