package utils;

import java.util.Objects;
import java.util.Optional;

/**
 * A utility class for {@link Math} to provide useful functionality that currently does not exist
 *
 * @author Ethan
 * @version 1.0
 * @since 2022-07-10
 */
public class MathUtil {
    /**
     * Private constructor to prevent initialization
     */
    private MathUtil() {
    }

    /**
     * Calculates the least common multiple (lcm) of two integers.
     *
     * <pre>
     * Code examples:
     * lcm(6, 8); // 24
     * lcm(0, 5); // 0
     * lcm(5, 0); // 0
     * </pre>
     *
     * @param number1 The first number for calculating lcm
     * @param number2 The second number for calculating lcm
     * @return 0 if either argument is 0 otherwise returns a positive number (lcm)
     * @see <a href="https://en.wikipedia.org/wiki/Least_common_multiple">Least Common Multiple (lcm) </a>
     */
    public static int lcm(int number1, int number2) {
        if (number1 == 0 || number2 == 0) {
            return 0;
        }

        return Math.abs((number1 / gcd(number1, number2)) * number2);
    }

    /**
     * Calculates the least common multiple of two or more integers.
     *
     * <pre>
     * Code examples:
     * lcm(6, 8);     // 24
     * lcm(6, 8, 14); // 168
     * lcm(0, 8, 14); // 0
     * lcm(6, 8, 0);  // 0
     * </pre>
     *
     * @param number1      The first number for calculating lcm
     * @param number2      The second number for calculating lcm
     * @param otherNumbers numbers to calculate the lcm of all, can be empty
     * @return The lcm of all the numbers given, 0 if any number is 0 otherwise positive number (lcm)
     * @throws NullPointerException If otherNumbers is null.
     * @see MathUtil#lcm(int, int)
     */
    public static int lcm(int number1, int number2, int... otherNumbers) {
        Objects.requireNonNull(otherNumbers);

        int currentLcm = lcm(number1, number2);

        for (int number : otherNumbers) {
            // Terminate early if lcm is already 0 since lcm of 0 is always 0
            if (currentLcm == 0) {
                return currentLcm;
            }

            currentLcm = lcm(currentLcm, number);
        }

        return currentLcm;
    }

    /**
     * Calculates the greatest common denominator (gcd) of two integers.
     *
     * @param number1 The first number for calculating gcd
     * @param number2 The second number for calculating gcd
     * @return The greatest common denominator (gcd) of num1 and number2 which is either 0 or a positive number.
     * @apiNote Although the gcd(0, 0) should be undefined, we return 0 in this case
     * @implNote Implements the Euclidean algorithm to calculate the gcd
     * @see <a href="https://en.wikipedia.org/wiki/Euclidean_algorithm">Euclidean Algorithm</a>
     */
    public static int gcd(int number1, int number2) {
        number1 = Math.abs(number1);
        number2 = Math.abs(number2);

        // We must ensure that number1 >= number2 to ensure gcd is correct
        if (number1 < number2) {
            int tmp = number1;
            number1 = number2;
            number2 = tmp;
        }

        while (number2 != 0) {
            int remainder = number1 % number2;
            number1 = number2;
            number2 = remainder;
        }

        return number1;
    }

    /**
     * Calculates the greatest common denominator of two or more integers.
     *
     * @param number1      The first number for calculating gcd
     * @param number2      The second number for calculating gcd
     * @param otherNumbers The gcd of all the numbers given, 1 if any number is 0 otherwise num1 positive least common multiple
     * @return The greatest common denominator (gcd) of num1, num2, and otherNumbers which can be 0 or a positive number.
     * @apiNote Although the gcd(0, 0) should be undefined, we return 0 in this case
     * @see MathUtil#gcd(int, int)
     */
    public static int gcd(int number1, int number2, int... otherNumbers) {
        Objects.requireNonNull(otherNumbers);

        int currentGcd = gcd(number1, number2);

        for (int number : otherNumbers) {
            // Terminate early since gcd of 1 is always 1
            if (currentGcd == 1) {
                break;
            }

            currentGcd = gcd(currentGcd, number);
        }

        return currentGcd;
    }
}
