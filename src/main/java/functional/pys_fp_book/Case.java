package functional.pys_fp_book;

/**
 * An abstraction of "if, if-else, else"
 *
 * @param <T>
 */
public class Case<T> extends Tuple<Supplier<Boolean>, Supplier<Result<T>>> {
    private Case(Supplier<Boolean> condition, Supplier<Result<T>> value) {
        super(condition, value);
    }

    static <T> Case<T> mcase(Supplier<Boolean> condition, Supplier<Result<T>> value) {
        return new Case<>(condition, value);
    }

    static <T> DefaultCase<T> mcase(Supplier<Result<T>> value) {
        return new DefaultCase<>(value);
    }

    static <T> Result<T> match(DefaultCase<T> defaultCase, Case<T>... matchers) {
        for (Case<T> aCase : matchers) {
            if (aCase._1.get()) aCase._2.get();
        }
        return defaultCase._2.get();
    }

    private static class DefaultCase<T> extends Case<T> {
        private DefaultCase(Supplier<Result<T>> value) {
            super(() -> true, value);
        }
    }
}
