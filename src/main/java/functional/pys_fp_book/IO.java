package functional.pys_fp_book;

@FunctionalInterface
public interface IO<T> {
    T run();

    IO<Nothing> empty = () -> Nothing.instance;

    static <T> IO<T> unit(T t) {
        return () -> t;
    }

    default <U> IO<U> map(Function<T, U> f) {
        return () -> f.apply(run());
    }

    default <U> IO<U> flatMap(Function<T, IO<U>> f) {
        return () -> f.apply(run()).run();
    }
}
