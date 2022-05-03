package math;

import utils.MathUtil;

import java.util.Objects;

/**
 * An implementation of a Fraction found in mathematics.
 * 
 * There is a potential case for overflow/underflow if the fraction operations
 * go over the Integer.MAX_VALUE or Integer.MIN_VALUE.
 * 
 * Resources:
 * 
 * 
 * https://www.mathsisfun.com/fractions-menu.html
 * https://jonisalonen.com/2012/converting-decimal-numbers-to-ratios/
 * 
 * @author Ethan
 * @version 1.0
 * @since 2021-04-10
 */
public class Fraction implements Cloneable, Comparable<Fraction> {
	private int numerator;
	private int denominator;

	/**
	 * Creates a Fraction(1, 1)
	 */
	public Fraction() {
		this(1, 1);
	}

	/**
	 * Constructs a Fraction.
	 * 
	 * @param numerator   the numerator of the fraction.
	 * @param denominator the denominator of the fraction.
	 */
	public Fraction(int numerator, int denominator) {
		this.numerator = numerator;
		this.denominator = denominator;
	}

	/**
	 * Adds the two fractions.
	 * 
	 * @implNote Will return a new instance.
	 * @param other
	 * @return A non-simplified added fraction.
	 */
	public Fraction add(Fraction other) {
		int denominatorLCM = MathUtil.lcm(getDenominator(), other.getDenominator());

		int numerator = (denominatorLCM / getDenominator() * getNumerator())
				+ (denominatorLCM / other.getDenominator() * other.getNumerator());

		return new Fraction(numerator, denominatorLCM);
	}

	/**
	 * Subtracts the two fractions.
	 * 
	 * @implNote Will return a new instance.
	 * @param other
	 * @return A non-simplified subtracted fraction.
	 */
	public Fraction subtract(Fraction other) {
		return add(other.negate());
	}

	/**
	 * Multiplies the two fractions.
	 * 
	 * @implNote Will return a new instance.
	 * @param other
	 * @return A non-simplified mutiplied fraction.
	 */
	public Fraction multiply(Fraction other) {
		return new Fraction(getNumerator() * other.getNumerator(), getDenominator() * other.getDenominator());
	}

	/**
	 * Divides the two fractions.
	 * 
	 * @implNote Will return a new instance.
	 * @param other
	 * @return A non-simplified divided fraction.
	 */
	public Fraction divide(Fraction other) {
		return multiply(other.reciprocal());
	}

	/**
	 * The negation of the fraction.
	 * 
	 * @implNote Will return a new instance.
	 * @return Convert a positive fraction into a negative and vice versa.
	 */
	public Fraction negate() {
		return new Fraction(-getNumerator(), getDenominator());
	}

	/**
	 * The recriprocal of the fraction.
	 * 
	 * 
	 * @implNote Will return a new instance.
	 * @return The recriprocol of the fraction.
	 */
	public Fraction reciprocal() {
		return new Fraction(getDenominator(), getNumerator());
	}

	/**
	 * The simplified version of the fraction.
	 * 
	 * @implNote Will return a new instance.
	 * @return The fraction in simplified form.
	 */
	public Fraction simplify() {
		int gcd = MathUtil.gcd(getNumerator(), getDenominator());
		return new Fraction(getNumerator() / gcd, getDenominator() / gcd);
	}

	/**
	 * A proper fraction is when the |numerator| <= |denominator|
	 * 
	 * @return Whether the fraction is considered a proper fraction.
	 */
	public boolean isProperFraction() {
		return Math.abs(getNumerator()) <= Math.abs(getDenominator());
	}

	/**
	 * An improper fraction is not a proper fraction.
	 * 
	 * @return Whether the fraction is considered an improper fraction.
	 */
	public boolean isImproperFraction() {
		return !isProperFraction();
	}

	/**
	 * An undefined fraction is where the denomintor is equal to 0.
	 * 
	 * @return Whether the fraction is considered an undefined fraction.
	 */
	public boolean isUndefinedFraction() {
		return getDenominator() == 0;
	}

	/**
	 * Two fractions are equivalent if their decimal values are the same.
	 * 
	 * @param other
	 * @return if decimal values are the same then true otherwise false.
	 */
	public boolean isEquivalent(Fraction other) {
		return toDecimal() == other.toDecimal();
	}

	/**
	 * Converts the fraction into it's respective decimal value.
	 * 
	 * @return the decimal value of the represented fraction.
	 * @throws IllegalArgumentException if the fraction is undefined.
	 */
	public double toDecimal() {
		checkIsValidFraction(this);
		return ((double) getNumerator()) / getDenominator();
	}

	/**
	 * Converts the fraction into it's respective percentage.
	 * 
	 * @return the percentage of the fraction
	 * @throws IllegalArgumentException if the fraction is undefined.
	 */
	public double toPercent() {
		return toDecimal() * 100;
	}

	private static void checkIsValidFraction(Fraction fraction) {
		if (fraction.isUndefinedFraction()) {
			throw new IllegalArgumentException("The fraction is an undefined fraction (denominator = 0)");
		}
	}

	/**
	 * @return The numerator of the fraction.
	 */
	public int getNumerator() {
		return numerator;
	}

	/**
	 * @return The denominator of the fraction.
	 */
	public int getDenominator() {
		return denominator;
	}

	/**
	 * Update the numerator.
	 * 
	 * @param numerator the new numerator value.
	 * @return the previous numerator value.
	 */
	public int setNumerator(int numerator) {
		int previousNumerator = this.numerator;
		this.numerator = numerator;
		return previousNumerator;
	}

	/**
	 * Update the denominator.
	 * 
	 * @param denominator the new denominator value.
	 * @return the previous denominator value.
	 */
	public int setDenominator(int denominator) {
		int previousDenominator = this.denominator;
		this.denominator = denominator;
		return previousDenominator;
	}

	/**
	 * Compares the decimal value to see which one is greater.
	 * 
	 * <br>
	 * If both are undefined fractions, returns 0. An undefined fraction will always
	 * be greater than a non-undefined fraction. result in being greater.
	 */
	@Override
	public int compareTo(Fraction o) {
		if (isUndefinedFraction() && o.isUndefinedFraction()) {
			return 0;
		} else if (isUndefinedFraction()) {
			return 1;
		} else if (o.isUndefinedFraction()) {
			return -1;
		}

		double diff = toDecimal() - o.toDecimal();

		if (diff < 0) {
			return -1;
		} else if (diff > 0) {
			return 1;
		}
		return 0;
	}

	/**
	 * Two fractions are equal if both their numerator and denominator are the same.
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (o == this) {
			return true;
		}

		if (!(o instanceof Fraction)) {
			return false;
		}

		Fraction other = (Fraction) o;
		return getNumerator() == other.getNumerator() && getDenominator() == other.getDenominator();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getNumerator(), getDenominator());
	}

	@Override
	public Object clone() {
		Object clone = null;
		try {
			clone = super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return clone;
	}

	/**
	 * A formatted string of: "numerator/denominator"
	 * 
	 * <pre>
	 * Example: <code>
	 * Fraction f = new Fraction(3, 2);
	 * "3/2".equals(f.toString());
	 * </code> </pr>
	 */
	@Override
	public String toString() {
		return String.format("%d/%d", getNumerator(), getDenominator());
	}

	/**
	 * 
	 * A static factory method of generatoring a Fraction. <br>
	 * It is synonymous to calling the constructor
	 * {@code new Fraction(int numerator, int denominator)}
	 * 
	 * @param numerator
	 * @param denominator
	 * @return A Fraction with the specified numerator and denominator.
	 */
	public static Fraction valueOf(int numerator, int denominator) {
		return new Fraction(numerator, denominator);
	}

	/**
	 * Converts an integer into it's fraction form. In otherwords, it's a Fraction
	 * with an abritrary numerator but a denomintor as 1.
	 * 
	 * <pre>
	 * Example: 
	 * <code>
	 * Fraction oneHundredOverOne = Fraction.valueOf(100);
	 * Fraction threeOverOne = Fraction.valueOf(3);
	 * </code>
	 * </pre>
	 * 
	 * @param numerator The numerator value.
	 * @return A Fraction with numerator over one.
	 */
	public static Fraction valueOf(int numerator) {
		return new Fraction(numerator, 1);
	}

	/**
	 * Converts a decimal value into a closely estiamted integer fraction
	 * representation.
	 * 
	 * <pre>
	 * Examples: 
	 * <code>
	 * Fraction oneHalf = Fraction.valueOf(0.5);
	 * Fraction threeOverTwo = Fraction.valueOf(1.5);
	 * </code>
	 * </pre>
	 * 
	 * @param decimal The value to convert into a fraction.
	 * @return A very closely estimated Fraction
	 */
	public static Fraction valueOf(double decimal) {
		if (decimal < 0) {
			return valueOf(-decimal).negate();
		}

		// Continued fraction
		// The lower the tolerance, the more accurate, change the E-Power
		double tolerance = 1.0E-12;
		double h1 = 1;
		double h2 = 0;
		double k1 = 0;
		double k2 = 1;
		double b = decimal;
		do {
			double a = Math.floor(b);
			double aux = h1;
			h1 = a * h1 + h2;
			h2 = aux;
			aux = k1;
			k1 = a * k1 + k2;
			k2 = aux;
			b = 1 / (b - a);
		} while (Math.abs(decimal - h1 / k1) > decimal * tolerance);

		return new Fraction((int) h1, (int) k1);
	}

	/**
	 * Convert a string formatted fraction: "int/int" into a Fraction.
	 * 
	 * <pre>
	 * Example: 
	 * <code>
	 * Fraction f = Fraction.valueOf("1/2");
	 * </code>
	 * </pre>
	 * 
	 * @param fraction A string formatted fraction.
	 * @return A Fraction representing the string formatted fraction
	 */
	public static Fraction valueOf(String fraction) {
		String[] components = fraction.split("/");
		return new Fraction(Integer.valueOf(components[0]), Integer.valueOf(components[1]));
	}
}
