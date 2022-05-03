package functional.pys_fp_book;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static functional.pys_fp_book.TailCall.ret;
import static functional.pys_fp_book.TailCall.sus;

public class CollectionsUtility {
    public static <T, U> List<U> map(List<T> list, Function<T, U> f) {
        List<U> newList = new ArrayList<>();
        for (T value : list) {
            newList.add(f.apply(value));
        }
        return newList;
    }

    public static <T, U> List<U> mapLeft(List<T> list, Function<T, U> f) {
        return foldLeft(list, list(), acc -> item -> append(acc, f.apply(item)));
    }

    public static <T, U> List<U> mapRight(List<T> list, Function<T, U> f) {
        return foldRight(list, list(), item -> acc -> prepend(acc, f.apply(item)));
    }

    public static <T> List<T> list() {
        return Collections.emptyList();
    }

    public static <T> List<T> list(T t) {
        return Collections.singletonList(t);
    }

    public static <T> List<T> list(List<? extends T> t) {
        return Collections.unmodifiableList(t);
    }

    public static <T> List<T> list(T... t) {
        return Collections.unmodifiableList(Arrays.asList(t));
    }

    public static <T> T head(List<T> t) {
        if (t.isEmpty()) {
            throw new IllegalStateException("List is empty");
        }
        return t.get(0);
    }

    private static <T> List<T> copy(List<T> t) {
        return new ArrayList<>(t);
    }

    public static <T> List<T> tail(List<T> t) {
        if (t.isEmpty()) {
            throw new IllegalStateException("List is empty");
        }

        List<T> modifiableList = copy(t);
        modifiableList.remove(0);
        return Collections.unmodifiableList(modifiableList);
    }

    public static <T> List<T> prepend(List<T> list, T t) {
        List<T> modifiableList = copy(list);
        modifiableList.add(0, t);
        return Collections.unmodifiableList(modifiableList);
    }

    public static <T> List<T> prependUsingFoldLeft(T t, List<T> list) {
        return foldLeft(list, list(t), a -> b -> append(a, b));
    }

    public static <T> List<T> append(List<T> list, T t) {
        List<T> modifiableList = copy(list);
        modifiableList.add(t);
        return Collections.unmodifiableList(modifiableList);
    }

    //((0 + 1) + 2), 0 is identity, left side of operation is accumulator
    public static <T, U> U foldLeft(List<T> list, U identity, Function<U, Function<T, U>> f) {
        U acc = identity;
        for (T item : list) {
            acc = f.apply(acc).apply(item);
        }
        return acc;
    }

    public static <T, U> U foldLeftRecursiveTC(List<T> list, U identity, Function<U, Function<T, U>> f) {
        return foldLeftRecursiveTC_(list, identity, f).eval();
    }

    private static <T, U> TailCall<U> foldLeftRecursiveTC_(List<T> list, U identity, Function<U, Function<T, U>> f) {
        return list.isEmpty() ? ret(identity) : sus(() -> foldLeftRecursiveTC_(tail(list), f.apply(identity).apply(head(list)), f));
    }

    //(2 + (1 + 0))s, 0 is identity, right side of operation is accumulator
    public static <T, U> U foldRight(List<T> list, U identity, Function<T, Function<U, U>> f) {
        U acc = identity;
        for (int i = list.size() - 1; i >= 0; i--) {
            acc = f.apply(list.get(i)).apply(acc);
        }
        return acc;
    }

    public static <T, U> U foldRightRecursiveUnsafe(List<T> list, U identity, Function<T, Function<U, U>> f) {
        return list.isEmpty() ?
                identity :
                f.apply(head(list)).apply(foldRightRecursiveUnsafe(tail(list), identity, f));
    }

    public static <T, U> U foldRightRecursiveUnsafeTailRecursion(List<T> list, U acc, Function<T, Function<U, U>> f) {
        return list.isEmpty() ?
                acc : foldRightRecursiveUnsafeTailRecursion(tail(list), f.apply(head(list)).apply(acc), f);
    }

    public static <T, U> U foldRightRecursiveTC(List<T> list, U acc, Function<T, Function<U, U>> f) {
        return foldRightRecursiveTC_(reverse(list), acc, f).eval();
    }

    private static <T, U> TailCall<U> foldRightRecursiveTC_(List<T> list, U acc, Function<T, Function<U, U>> f) {
        return list.isEmpty() ?
                ret(acc) : sus(() -> foldRightRecursiveTC_(tail(list), f.apply(head(list)).apply(acc), f));
    }

    public static <T> List<T> reverse(List<T> t) {
        List<T> reversedList = new ArrayList<>();
        for (int i = t.size() - 1; i >= 0; i--) {
            reversedList.add(t.get(i));
        }
        return Collections.unmodifiableList(reversedList);
    }

    public static <T> List<T> reverseWithFoldLeftPrepend(List<T> t) {
        return foldLeft(t, list(), acc -> i -> prepend(acc, i));
    }

    public static <T> void forEach(List<T> list, Effect<T> e) {
        for (T t : list) e.apply(t);
    }

    public static <T> List<T> unfold(T seed, Function<T, T> nextF, Function<T, Boolean> p) {
        List<T> result = new ArrayList<>();
        T temp = seed;
        while (p.apply(temp)) {
            result = append(result, temp);
            temp = nextF.apply(temp);
        }
        return result;
    }

    public static List<Integer> range(int start, int end) {
        return unfold(start, x -> x + 1, x -> x < end);
    }

    public static List<Integer> rangeRecursive(int start, int end) {
        return start >= end ? list() : prepend(rangeRecursive(start + 1, end), start);
    }

    public static List<Integer> rangeRecursiveTC(int start, int end) {
        return rangeRecursiveTC_(start, end, list()).eval();
    }

    private static TailCall<List<Integer>> rangeRecursiveTC_(int start, int end, List<Integer> acc) {
        return start >= end ? ret(acc) : sus(() -> rangeRecursiveTC_(start + 1, end, append(acc, start)));
    }
}
