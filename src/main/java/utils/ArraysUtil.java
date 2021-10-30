package utils;

import java.util.StringJoiner;

public class ArraysUtil {
    private ArraysUtil() {
    }

    public static final String DEFAULT_ELEMENT_DELIMITER = ", ";

    public static String toString(Object[] array) {
        return toString(array, DEFAULT_ELEMENT_DELIMITER);
    }

    public static String toString(Object[] array, String delimiter) {
        StringJoiner joiner = new StringJoiner(", ", "[", "]");
        for (Object o : array) {
            joiner.add(String.valueOf(o));
        }
        return joiner.toString();
    }

    public static String toString(boolean[] array) {
        return toString(array, DEFAULT_ELEMENT_DELIMITER);
    }

    public static String toString(boolean[] array, String delimiter) {
        StringJoiner joiner = new StringJoiner(delimiter, "[", "]");
        for (Object o : array) {
            joiner.add(String.valueOf(o));
        }
        return joiner.toString();
    }

    public static String toString(char[] array) {
        return toString(array, DEFAULT_ELEMENT_DELIMITER);
    }

    public static String toString(char[] array, String delimiter) {
        StringJoiner joiner = new StringJoiner(delimiter, "[", "]");
        for (Object o : array) {
            joiner.add(String.valueOf(o));
        }
        return joiner.toString();
    }

    public static String toString(byte[] array) {
        return toString(array, DEFAULT_ELEMENT_DELIMITER);
    }

    public static String toString(byte[] array, String delimiter) {
        StringJoiner joiner = new StringJoiner(delimiter, "[", "]");
        for (Object o : array) {
            joiner.add(String.valueOf(o));
        }
        return joiner.toString();
    }

    public static String toString(int[] array) {
        return toString(array, DEFAULT_ELEMENT_DELIMITER);
    }

    public static String toString(int[] array, String delimiter) {
        StringJoiner joiner = new StringJoiner(delimiter, "[", "]");
        for (Object o : array) {
            joiner.add(String.valueOf(o));
        }
        return joiner.toString();
    }

    public static String toString(long[] array) {
        return toString(array, DEFAULT_ELEMENT_DELIMITER);
    }

    public static String toString(long[] array, String delimiter) {
        StringJoiner joiner = new StringJoiner(delimiter, "[", "]");
        for (Object o : array) {
            joiner.add(String.valueOf(o));
        }
        return joiner.toString();
    }

    public static String toString(double[] array) {
        return toString(array, DEFAULT_ELEMENT_DELIMITER);
    }

    public static String toString(double[] array, String delimiter) {
        StringJoiner joiner = new StringJoiner(delimiter, "[", "]");
        for (Object o : array) {
            joiner.add(String.valueOf(o));
        }
        return joiner.toString();
    }

    public static String toString(float[] array) {
        return toString(array, DEFAULT_ELEMENT_DELIMITER);
    }

    public static String toString(float[] array, String delimiter) {
        StringJoiner joiner = new StringJoiner(delimiter, "[", "]");
        for (Object o : array) {
            joiner.add(String.valueOf(o));
        }
        return joiner.toString();
    }

    public static String toString(short[] array) {
        return toString(array, DEFAULT_ELEMENT_DELIMITER);
    }

    public static String toString(short[] array, String delimiter) {
        StringJoiner joiner = new StringJoiner(delimiter, "[", "]");
        for (Object o : array) {
            joiner.add(String.valueOf(o));
        }
        return joiner.toString();
    }

    public static void swap(Object[] ary, int a, int b) {
        Object temp = ary[a];
        ary[a] = ary[b];
        ary[b] = temp;
    }
}
