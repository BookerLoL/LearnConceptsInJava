package math;

import java.util.Objects;

/**
 * An implementation of a Complex Number found in mathematics.
 * 
 * Resources: https://en.wikipedia.org/wiki/Complex_number
 * http://www.chemistrylearning.com/reciprocal-of-a-complex-number/
 * https://algs4.cs.princeton.edu/99scientific/Complex.java.html
 * 
 * 
 * @author Ethan
 * @version 1.0
 * @since 2021-04-10
 */
public class ComplexNumber {
	private double real;
	private double imaginary;

	public ComplexNumber() {
		this(0.0, 0.0);
	}

	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	public ComplexNumber add(ComplexNumber other) {
		return new ComplexNumber(getReal() + other.getReal(), getImaginary() + other.getImaginary());
	}

	public ComplexNumber subtract(ComplexNumber other) {
		return new ComplexNumber(getReal() - other.getReal(), getImaginary() - other.getImaginary());
	}

	public ComplexNumber multiply(ComplexNumber other) {
		double firsts = getReal() * other.getReal(); // FOIL
		double outers = getReal() * other.getImaginary();
		double inners = getImaginary() * other.getReal();
		double lasts = getImaginary() * other.getImaginary(); // i^2 = -1, so subtract
		return new ComplexNumber(firsts - lasts, outers + inners);
	}

	public ComplexNumber divides(ComplexNumber other) {
		return multiply(other.reciprocal());
	}

	public ComplexNumber negate() {
		return new ComplexNumber(-getReal(), -getImaginary());
	}

	public ComplexNumber conjugate() {
		return new ComplexNumber(getReal(), -getImaginary());
	}

	public ComplexNumber scale(double alpha) {
		return new ComplexNumber(getReal() * alpha, getImaginary() * alpha);
	}

	public ComplexNumber reciprocal() {
		double modulus = this.getReal() * this.getReal() + this.getImaginary() + this.getImaginary();
		return new ComplexNumber(getReal() / modulus, -getImaginary() / modulus); // conjugate / modulus
	}

	public double abs() {
		return Math.hypot(getReal(), getImaginary());
	}

	public double getReal() {
		return real;
	}

	public double getImaginary() {
		return imaginary;
	}

	public double setReal(double real) {
		double previous = this.real;
		this.real = real;
		return previous;
	}

	public double setImaginary(double imaginary) {
		double previous = this.imaginary;
		this.imaginary = imaginary;
		return previous;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}
		if (o == this) {
			return true;
		}
		if (!(o instanceof ComplexNumber)) {
			return false;
		}

		ComplexNumber other = (ComplexNumber) o;
		return getReal() == other.getReal() && getImaginary() == other.getImaginary();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getReal(), getImaginary());
	}

	@Override
	public String toString() {
		String format = getImaginary() < 0 ? "(%f + %fi)" : "(%f - %fi)";
		return String.format(format, getReal(), getImaginary());
	}
}
