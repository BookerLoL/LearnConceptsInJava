package functional.pys_fp_book;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static functional.pys_fp_book.TailCall.ret;
import static functional.pys_fp_book.TailCall.sus;

public abstract class List<T> {
    public abstract T head();

    public abstract List<T> tail();

    public abstract boolean isEmpty();


    public abstract List<T> setHead(T t);

    public abstract List<T> drop(int n);

    public abstract List<T> dropWhile(Function<T, Boolean> pred);

    public abstract List<T> concat(List<T> other);

    public abstract List<T> reverse();

    public abstract List<T> removeLast();

    public abstract long length();

    public abstract <U> U foldLeft(U identity, Function<U, Function<T, U>> f);

    public abstract <U> U foldLeft(U identity, U zero, List<T> list, Function<U, Function<T, U>> f);

    public abstract <U> Tuple<U, List<T>> foldLeft(U identity, U zero, Function<U, Function<T, U>> f);

    public abstract <U> U foldRight(U identity, Function<T, Function<U, U>> f);

    public abstract long lengthMemoized();

    public abstract Result<T> headOption();

    public abstract Result<T> tailOption();

    private static List<?> NIL = new Nil<>();

    //f(1, f(2, f(3, identity))), recursive approach that is not safe
    public static <A, B> B foldRight(List<A> list, B identity, Function<A, Function<B, B>> f) {
        return list.isEmpty() ? identity : f.apply(list.head()).apply(foldRight(list.tail(), identity, f));
    }

    private static class Nil<T> extends List<T> {
        private Nil() {
        }

        @Override
        public T head() {
            throw new IllegalStateException("List is empty");
        }

        @Override
        public List<T> tail() {
            return this;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public List<T> setHead(T t) {
            throw new IllegalStateException("List is empty");
        }

        @Override
        public List<T> drop(int n) {
            return this;
        }

        @Override
        public List<T> dropWhile(Function<T, Boolean> pred) {
            return this;
        }

        @Override
        public List<T> concat(List<T> other) {
            return other;
        }

        @Override
        public List<T> reverse() {
            return this;
        }

        @Override
        public List<T> removeLast() {
            return this;
        }

        @Override
        public long length() {
            return 0;
        }

        @Override
        public <U> U foldLeft(U identity, Function<U, Function<T, U>> f) {
            return identity;
        }

        @Override
        public <U> U foldLeft(U identity, U zero, List<T> list, Function<U, Function<T, U>> f) {
            return identity;
        }

        @Override
        public <U> Tuple<U, List<T>> foldLeft(U identity, U zero, Function<U, Function<T, U>> f) {
            return new Tuple<>(identity, list());
        }

        @Override
        public <U> U foldRight(U identity, Function<T, Function<U, U>> f) {
            return identity;
        }

        @Override
        public long lengthMemoized() {
            return 0;
        }

        @Override
        public Result<T> headOption() {
            return Result.empty();
        }

        @Override
        public Result<T> tailOption() {
            return Result.empty();
        }
    }

    private static class Cons<T> extends List<T> {
        private final T head;
        private final List<T> tail;
        private final long length;

        private Cons(T head, List<T> tail) {
            this.head = head;
            this.tail = tail;
            this.length = tail.length() + 1; //This is the memoized approach, is great for very large lists!
        }

        @Override
        public T head() {
            return head;
        }

        @Override
        public List<T> tail() {
            return tail;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public List<T> setHead(T t) {
            return new Cons(t, tail());
        }

        @Override
        public List<T> drop(int n) {
            return n <= 0 ? this : drop_(this, n).eval();
        }

        private TailCall<List<T>> drop_(List<T> list, int n) {
            return n <= 0 || list.isEmpty() ? ret(list) : sus(() -> drop_(list.tail(), n - 1));
        }

        @Override
        public List<T> dropWhile(Function<T, Boolean> pred) {
            return dropWhile_(this, pred).eval();
        }

        @Override
        public List<T> concat(List<T> other) {
            return foldRight(other, x -> y -> cons(x, y));
        }

        @Override
        public List<T> reverse() {
            return reverse_(this, list()).eval();
        }

        private TailCall<List<T>> reverse_(List<T> list, List<T> acc) {
            return list.isEmpty() ? ret(acc) : sus(() -> reverse_(list.tail(), cons(list.head(), acc)));
        }

        @Override
        public List<T> removeLast() {
            return reverse().tail().removeLast();
        }

        @Override
        public long length() {
            return length;
        }

        //Faster than using fold
        private long scalaLength() {
            List<T> curr = this;
            long length = 0;
            while (!curr.isEmpty()) {
                length++;
                curr = curr.tail();
            }
            return length;
        }

        @Override
        public <U> U foldLeft(U identity, Function<U, Function<T, U>> f) {
            return foldLeft_(identity, this, f).eval();
        }

        @Override
        public <U> U foldLeft(U identity, U zero, List<T> list, Function<U, Function<T, U>> f) {
            return foldLeft_(identity, zero, this, f).eval();
        }

        @Override
        public <U> Tuple<U, List<T>> foldLeft(U identity, U zero, Function<U, Function<T, U>> f) {
            return foldLeftHelper_(identity, zero, this, f).eval();
        }

        private <U> TailCall<Tuple<U, List<T>>> foldLeftHelper_(U acc, U zero, List<T> list, Function<U, Function<T, U>> f) {
            return list.isEmpty() || acc.equals(zero) ? ret(new Tuple<>(acc, list)) : sus(() -> foldLeftHelper_(f.apply(acc).apply(list.head()), zero, list.tail(), f));
        }

        private <U> TailCall<U> foldLeft_(U acc, U zero, List<T> list,
                                          Function<U, Function<T, U>> f) {
            return list.isEmpty() || acc.equals(zero)
                    ? ret(acc) : sus(() -> foldLeft_(f.apply(acc).apply(list.head()),
                    zero, list.tail(), f));
        }

        @Override
        public <U> U foldRight(U identity, Function<T, Function<U, U>> f) {
            return reverse().foldLeft(identity, x -> y -> f.apply(y).apply(x));
        }

        @Override
        public long lengthMemoized() {
            return length;
        }

        @Override
        public Result<T> headOption() {
            return Result.success(head);
        }

        @Override
        public Result<T> tailOption() {
            return foldLeft(Result.empty(), x -> Result::success);
        }

        private <U> TailCall<U> foldLeft_(U acc, List<T> t, Function<U, Function<T, U>> f) {
            return t.isEmpty() ? ret(acc) : sus(() -> foldLeft_(f.apply(acc).apply(t.head()), t.tail(), f));
        }

        private TailCall<List<T>> dropWhile_(List<T> list, Function<T, Boolean> pred) {
            return list.isEmpty() || pred.apply(list.head()) ? ret(list) : sus(() -> dropWhile_(list.tail(), pred));
        }
    }

    public static <T> List<T> concat(List<T> list1, List<T> list2) {
        return list1.reverse().foldLeft(list2, x -> x::cons);
    }

    public static <T> List<T> setHead(List<T> list, T head) {
        return list.setHead(head);
    }

    public Cons<T> cons(T t) {
        return new Cons<>(t, this);
    }

    public static <T> Cons<T> cons(T item, List<T> tail) {
        return tail.cons(item);
    }

    public static <T> List<T> list() {
        return (List<T>) NIL;
    }

    public static <T> List<T> list(T... t) {
        List<T> n = list();
        for (int i = t.length - 1; i >= 0; i--) {
            n = cons(t[i], n);
        }
        return n;
    }

    public static <T> List<T> list(Collection<T> c) {
        List<T> n = list();
        for (T t : c) {
            n = cons(t, n);
        }
        return n.reverse();
    }

    public static <T> List<T> reverse(List<T> t) {
        return t.foldLeft(list(), list -> list::cons);
    }

    public String toString() {
        return toString(new StringBuilder(), this).eval().append("[NIL]").toString();
    }

    private TailCall<StringBuilder> toString(StringBuilder acc, List<T> list) {
        return list.isEmpty() ?
                ret(acc) : sus(() -> toString(acc.append(list.head()).append(", "), list.tail()));
    }

    public static <T> List<T> flatten(List<List<T>> list) {
        return foldRight(list, list(), x -> x::concat);
    }

    public static <T> List<T> flattenViaFlatMap(List<List<T>> list) {
        return list.flatMap(x -> x);
    }

    public <U> List<U> map(Function<T, U> f) {
        return foldRight(list(), x -> y -> cons(f.apply(x), y));
    }

    public List<T> filter(Function<T, Boolean> f) {
        return foldRight(list(), x -> y -> f.apply(x) ? cons(x, y) : y);
    }

    public static <T> List<T> filterViaFlatMap(List<T> list, Function<T, Boolean> f) {
        return list.flatMap(a -> f.apply(a) ? list(a) : list());
    }

    public <U> List<U> flatMap(Function<T, List<U>> f) {
        return foldRight(list(), x -> y -> concat(f.apply(x), y));
    }

    public static <T> List<T> fill(int n, Supplier<T> s) {
        return range(0, n).map(ignore -> s.get());
    }

    //This solution can be improved
    public static <T> List<T> flatternResults(List<Result<T>> list) {
        return flatten(list.foldRight(list(), x -> y -> y.cons(x.map(List::list).getOrElse(list()))));
    }

    public static <T> Result<List<T>> sequence(List<Result<T>> list) {
        return traverse(list.filter(r -> r.isSuccess() || r.isFailure()), x -> x);
    }

    public static <T, U> Result<List<U>> traverse(List<T> list, Function<T, Result<U>> f) {
        return list.foldRight(Result.success(list()), item -> acc -> Result.map2(f.apply(item), acc, a -> b -> b.cons(a)));
    }

    public static <T, U, V> List<V> zip(List<T> list1, List<U> list2, Function<T, Function<U, V>> f) {
        return zip_(list(), list1, list2, f).eval().reverse();
    }

    public static <T, U> Tuple<List<T>, List<U>> unzip(List<Tuple<T, U>> list) {
        return list.foldRight(new Tuple<>(list(), list()), tuple -> acc -> new Tuple<>(acc._1.cons(tuple._1), acc._2.cons(tuple._2)));
    }

    public static <T, U, V> List<V> product(List<T> list1, List<U> list2, Function<T, Function<U, V>> f) {
        return list1.flatMap(a -> list2.map(b -> f.apply(a).apply(b)));
    }

    private static <T, U, V> TailCall<List<V>> zip_(List<V> acc, List<T> list1, List<U> list2, Function<T, Function<U, V>> f) {
        return list1.isEmpty() || list2.isEmpty() ?
                ret(acc)
                : sus(() -> zip_(cons(f.apply(list1.head()).apply(list2.head()), acc), list1.tail(), list2.tail(), f));
    }

    public Result<T> getAt(int index) {
        return index < 0 || index >= length()
                ? Result.failure("Index out of bound")
                : getAt_(this, index).eval();
    }

    private static <T> TailCall<Result<T>> getAt_(List<T> list, int index) {
        return index == 0
                ? TailCall.ret(Result.success(list.head()))
                : TailCall.sus(() -> getAt_(list.tail(), index - 1));
    }

    public Tuple<List<T>, List<T>> splitAt(long index) {
        return index < 0
                ? splitAt(0)
                : index > length()
                ? splitAt(length())
                : splitAt(list(), this.reverse(), this.length() - index).eval();
    }

    private TailCall<Tuple<List<T>, List<T>>> splitAt(List<T> acc,
                                                      List<T> list, long i) {
        return i == 0 || list.isEmpty()
                ? ret(new Tuple<>(list.reverse(), acc))
                : sus(() -> splitAt(acc.cons(list.head()), list.tail(), i - 1));
    }

    public static <T> Boolean startsWith(List<T> list, List<T> sub) {
        return startsWith_(list, sub).eval();
    }

    private static <T> TailCall<Boolean> startsWith_(List<T> list, List<T> sub) {
        return sub.isEmpty() ? ret(true) : list.isEmpty() ? ret(false) :
                list.head().equals(sub.head()) ? sus(() -> startsWith_(list.tail(), sub.tail())) : ret(false);
    }

    public static <T> Boolean hasSubList(List<T> list, List<T> sub) {
        return hasSubList_(list, sub).eval();
    }

    private static <T> TailCall<Boolean> hasSubList_(List<T> list, List<T> sub) {
        return list.isEmpty() ? ret(sub.isEmpty()) : startsWith(list, sub) ? ret(true) : sus(() -> hasSubList_(list.tail(), sub));
    }

    public <U> Map<U, List<T>> groupBy(Function<T, U> f) {
        return foldRight(new HashMap<>(), t -> mt -> {
            final U u = f.apply(t);
            mt.put(u, mt.getOrDefault(u, list()).cons(t));
            return mt;
        });
    }

    //Imperative style might be better for more efficient approach
    public static <T, U> List<T> unfold(U start, Function<U, Result<Tuple<T, U>>> f) {
        return unfold_(list(), start, f).eval().reverse();
    }

    private static <T, U> TailCall<List<T>> unfold_(List<T> acc, U identity, Function<U, Result<Tuple<T, U>>> f) {
        Result<Tuple<T, U>> r= f.apply(identity);
        Result<TailCall<List<T>>> result = r.map(rt -> sus(() -> unfold_(acc.cons(rt._1), rt._2, f)));
        return result.getOrElse(ret(acc));
    }

    public static List<Integer> range(int start, int end) {
        return List.unfold(start, i -> i < end ?
                Result.success(new Tuple<>(i, i+1))
                : Result.empty());
    }

    public boolean exists(Function<T, Boolean> p) {
        return foldLeft(false, true, x -> y -> x || p.apply(y))._1;
    }

    public boolean forAll(Function<T, Boolean> p) {
        return !exists(x -> !p.apply(x));
    }

    public List<List<T>> splitListAt(long i) {
        return splitListAt_(list(), this.reverse(), i).eval();
    }

    private TailCall<List<List<T>>> splitListAt_(List<T> acc, List<T> list, long i) {
        return i == 0 || list.isEmpty() ?
        ret(list(list.reverse(), acc)) :
        sus(() -> splitListAt_(acc.cons(list.head()), list.tail(), i - 1));
    }

    public List<List<T>> divide(int depth) {
        return this.isEmpty() ? list(this) : divide(list(this), depth);
    }

    private List<List<T>> divide(List<List<T>> list, int depth) {
        return list.head().length() < depth || depth < 2 ?
                list
                : divide(list.flatMap(x -> x.splitListAt(x.length() / 2)), depth / 2);
    }
}
