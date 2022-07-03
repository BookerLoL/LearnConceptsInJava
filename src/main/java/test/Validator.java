package test;

public final class Validator {
	/**
	 * A check to ensure expression is true. If the expression is false then an
	 * error will be thrown.
	 * <p>
	 * Examples:
	 * 
	 * <pre>
	 * {@code checkValid(x == EXPECTED_NUMBER);}
	 * {@code checkValid(false);}
	 * </pre>
	 * 
	 * @param expression Expression to check if true.
	 * @throws IllegalArgumentException if the {@code expression} is {@code false}.
	 */
	public static void checkValid(boolean expression) {
		if (!expression) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * A check to ensure expression is true. If the expression is false then an
	 * error will be thrown with the supplied error message.
	 * 
	 * <p>
	 * Examples:
	 * 
	 * <pre>
	 * {@code checkValid(false, "This error message will appear");}
	 * {@code checkValid(true, "This will not fail");}
	 * </pre>
	 * 
	 * @param expression   Expression to check if true.
	 * @param errorMessage The error message to supply if error occurs.
	 * @throws IllegalArgumentException if the {@code expression} is {@code false}.
	 */
	public static void checkValid(boolean expression, String errorMessage) {
		if (!expression) {
			throw new IllegalArgumentException(errorMessage);
		}
	}

	/**
	 * A check to ensure expression is true. If the expression is false then an
	 * error will be thrown with the given error message format and it's
	 * corresponding arguments to fill in the formatted error message.
	 * 
	 * <p>
	 * Examples:
	 * 
	 * <pre>
W
	 * {@code checkValid(true, "This will not fail");}
	 * </pre>
	 * 
	 * @param expression         Expression to check if true.
	 * @param errorMessageFormat The error message format string to supply if error
	 *                           occurs.
	 * @param errorMessageArgs   The error message arguments for the format string.
	 * @throws IllegalArgumentException if the {@code expression} is {@code false}.
	 */
	public static void checkValid(boolean expression, String errorMessageFormat, Object... errorMessageArgs) {
		if (!expression) {
			throw new IllegalArgumentException(String.format(errorMessageFormat, errorMessageArgs));
		}
	}

	/**
	 * A check to ensure the object is not null. If the object is null then an error
	 * will be thrown.
	 * 
	 * @param object The object to check if null
	 * @throws NullPointerException if the object is null.
	 */
	public static void checkNotNull(Object object) {
		if (object == null) {
			throw new NullPointerException();
		}
	}

	/**
	 * A check to ensure the object is not null. If the object is null then an error
	 * will be thrown with the given error message.
	 * 
	 * @param object       The object to check if null
	 * @param errorMessage The error message to supply if error occurs.
	 * @throws NullPointerException if the object is null.
	 */
	public static void checkNotNull(Object object, String errorMessage) {
		if (object == null) {
			throw new NullPointerException(errorMessage);
		}
	}

	/**
	 * A check to ensure the object is not null. If the object is null then an error
	 * will be thrown with the given error message format and it's corresponding
	 * arguments to fill in the formatted error message.
	 * 
	 * @param object             The object to check if null
	 * @param errorMessageFormat The error message format string to supply if error
	 *                           occurs.
	 * @param errorMessageArgs   The error message arguments for the format string.
	 * @throws NullPointerException if the object is null.
	 */
	public static void checkNotNull(Object object, String errorMessageFormat, Object... errorMessageArgs) {
		if (object == null) {
			throw new NullPointerException(String.format(errorMessageFormat, errorMessageArgs));
		}
	}

	/**
	 * A check to ensure each object is not null. If one of the objects is null then
	 * an error will be thrown.
	 * 
	 * @param objects The objects to check if null.
	 * @throws NullPointerException if one of the objects is null.
	 */
	public static void checkNotNull(Iterable<Object> objects) {
		for (Object object : objects) {
			if (object == null) {
				throw new NullPointerException();
			}
		}
	}

	/**
	 * A check to ensure each object is not null. If one of the objects is null then
	 * an error will be thrown with the given error message.
	 * 
	 * @param objects      The objects to check if null.
	 * @param errorMessage The error message to supply if error occurs.
	 * @throws NullPointerException if one of the objects is null.
	 */
	public static void checkNotNull(Iterable<Object> objects, String errorMessage) {
		for (Object object : objects) {
			if (object == null) {
				throw new NullPointerException(errorMessage);
			}
		}
	}

	/**
	 * A check to ensure each object is not null. If one of the objects is null then
	 * an error will be thrown with the given error message format and it's
	 * corresponding arguments to fill in the formatted error message.
	 * 
	 * @param objects            The objects to check if null.
	 * @param errorMessageFormat The error message format string to supply if error
	 *                           occurs.
	 * @param errorMessageArgs   The error message arguments for the format string.
	 * @throws NullPointerException if one of the objects is null.
	 */
	public static void checkNotNull(Iterable<Object> objects, String errorMessageFormat, Object... errorMessageArgs) {
		for (Object object : objects) {
			if (object == null) {
				throw new NullPointerException(String.format(errorMessageFormat, errorMessageArgs));
			}
		}
	}
}
