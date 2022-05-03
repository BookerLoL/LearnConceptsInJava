package functional.pys_fp_book;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static functional.pys_fp_book.CollectionsUtility.foldLeft;
import static functional.pys_fp_book.CollectionsUtility.foldRight;
import static functional.pys_fp_book.CollectionsUtility.reverse;

@FunctionalInterface
public interface Function<T, U> {
    U apply(T arg);

    default <V> Function<V, U> compose(Function<V, T> f) {
        return v -> apply(f.apply(v));
    }

    default <V> Function<T, V> andThen(Function<U, V> f) {
        return t -> f.apply(apply(t));
    }

    //More like an and-then based on arguments, but same results as compose
    static <A, B, C> Function<Function<A, B>, Function<Function<B, C>, Function<A, C>>> compose() {
        return a -> b -> b.compose(a);
    }

    static <A, B, C> Function<Function<B, C>, Function<Function<A, B>, Function<A, C>>> andThen() {
        return a -> b -> b.andThen(a);
    }

    //Unable to infer types, must provide them when calling Function.<Type1, Type2, Type3>higherCompose()...
    static <A, B, C> Function<Function<B, C>, Function<Function<A, B>, Function<A, C>>> higherCompose() {
        return f -> g -> arg -> f.apply(g.apply(arg));
    }

    static <A, B, C> Function<Function<A, B>, Function<Function<B, C>, Function<A, C>>> higherAndThen() {
        return f -> g -> arg -> g.apply(f.apply(arg));
    }

    //It knows the argument types, so type inference isn't as bad, but if arguments are ambiguous then maybe
    //This is the better compose, the other composes are just showing how to change argument placements
    static <A, B, C> Function<B, C> compose(Function<A, C> f, Function<B, A> g) {
        return arg -> f.apply(g.apply(arg));
    }

    static <A, B, C> Function<B, C> partialA(A a, Function<A, Function<B, C>> f) {
        return f.apply(a);
    }

    static <A, B, C> Function<A, C> partialB(B b, Function<A, Function<B, C>> f) {
        return a -> f.apply(a).apply(b);
    }

    static <A, B, C> Function<B, Function<A, C>> reverseArgs(Function<A, Function<B, C>> f) {
        return b -> a -> f.apply(a).apply(b);
    }

    static <T> Function<T, T> identity() {
        return t -> t;
    }

    //This is bad for memory
    static <T> Function<T, T> composeAllBadMemory(List<Function<T, T>> list) {
        return foldRight(list, identity(), x -> x::compose);
    }

    //Better to just iterate through list and apply functions to results
    static <T> Function<T, T> composeAllFoldLeft(List<Function<T, T>> list) {
        return x -> foldLeft(reverse(list), x, acc -> func -> func.apply(acc));
    }

    static <T> Function<T, T> composeAllFoldRight(List<Function<T, T>> list) {
        return x -> foldRight(list, x, func -> func::apply);
    }

    static <T> Function<T, T> andThenFoldLeft(List<Function<T, T>> list) {
        return x -> foldLeft(list, x, acc -> func -> func.apply(acc));
    }

    static <T> Function<T, T> andThenFoldRight(List<Function<T, T>> list) {
        return x -> foldRight(reverse(list), x, func -> func::apply);
    }

    static <T, U> Function<T, U> memoize(Function<T, U> f) {
        Map<T, U> cache = new ConcurrentHashMap<>();
        return input -> cache.computeIfAbsent(input, f::apply);
    }
}
