package utils;

import java.util.Objects;
import java.util.Optional;

public class OptionalUtil {
    /**
     * Similar to {@link Math#min(int, int)} but for Optionals
     *
     * @param left The left optional to compare with
     * @param right The right optional to compare with
     * @return The optional with the min value, if equal value then left is returned, if an optional is empty then the other optional is returned.
     * @param <T>
     * @throws NullPointerException if either optional is null
     */
    public static <T extends Comparable<? super T>> Optional<T> min(Optional<T> left, Optional<T> right) {
        Objects.requireNonNull(left, "Left optional argument was null, cannot be null");
        Objects.requireNonNull(right, "Right optional argument was null, cannot be null");

        if (left.isEmpty()) {
            return right;
        } else if (right.isEmpty()) {
            return left;
        }

        return left.get().compareTo(right.get()) <= 0 ? left : right;
    }

    /**
     * Similar to {@link Math#max(int, int)} but for Optionals
     *
     * @param left The left optional to compare with
     * @param right The right optional to compare with
     * @return The optional with the max value, if equal value then left is returned, if an optional is empty then the other optional is returned.
     * @param <T>
     * @throws NullPointerException if either optional is null
     */
    public static <T extends Comparable<? super T>> Optional<T> max(Optional<T> left, Optional<T> right) {
        Objects.requireNonNull(left, "Left optional argument was null, cannot be null");
        Objects.requireNonNull(right, "Right optional argument was null, cannot be null");

        if (left.isEmpty()) {
            return right;
        } else if (right.isEmpty()) {
            return left;
        }

        return left.get().compareTo(right.get()) >= 0 ? left : right;
    }
}
