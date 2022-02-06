package functional.pys_fp_book;

import java.util.Random;


public interface RNG {
    Tuple<Integer, RNG> nextInt();

    interface RandomI<T> extends Function<RNG, Tuple<T, RNG>> {
        static <T> RandomI unit(T t) {
            return rng -> new Tuple<>(t, rng);
        }

        static <T, U> RandomI<U> map(RandomI<T> s, Function<T, U> f) {
            return rng -> {
                Tuple<T, RNG> tuple = s.apply(rng);
                return Tuple.of(f.apply(tuple._1), tuple._2);
            };
        }

        static <T, U> RandomI<U> mapViaFlatMap(RandomI<T> s, Function<T, U> f) {
            return flatMap(s, a -> unit(f.apply(a)));
        }

        static <T, U, V> RandomI<V> map2(RandomI<T> t, RandomI<U> u, Function<T, Function<U, V>> f) {
            return rng -> {
                Tuple<T, RNG> tTuple = t.apply(rng);
                Tuple<U, RNG> uTuple = u.apply(tTuple._2);
                return new Tuple<>(f.apply(tTuple._1).apply(uTuple._1), uTuple._2);
            };
        }

        static <T, U, V> RandomI<V> map2ViaFlatMap(RandomI<T> t, RandomI<U> u, Function<T, Function<U, V>> f) {
            return flatMap(t, t1 -> map(u, u1 -> f.apply(t1).apply(u1)));
        }

        static <T> RandomI<List<T>> sequence(List<RandomI<T>> rs) {
            RandomI<List<T>> list = unit(List.list());
            return rs.foldLeft(list, acc -> r -> map2(r, acc, x -> y -> y.cons(x)));
        }

        static <T, U> RandomI<U> flatMap(RandomI<T> s, Function<T, RandomI<U>> f) {
            return rng -> {
                Tuple<T, RNG> tuple = s.apply(rng);
                return f.apply(tuple._1).apply(tuple._2);
            };
        }
    }

    public static final RandomI<Integer> integer = RNG::nextInt;
    public static final RandomI<Integer> integer2 = RandomI.unit(1);
    public static final RandomI<Boolean> booleanRnd = RandomI.map(integer, num -> num % 2 == 0);
    public static final RandomI<Double> doubleRnd = RandomI.map(integer, x -> x / (((double) Integer.MAX_VALUE) + 1.0));
    public static final RandomI<Tuple<Integer, Integer>> intPairRnd = RandomI.map2(integer, integer2, x -> y -> new Tuple<>(x, y));
    public static final Function<Integer, RandomI<List<Integer>>> integersRnd = length -> RandomI.sequence(List.fill(length, () -> integer));
    public static final RandomI<Integer> notMultipleOfFive = RandomI.flatMap(integer, x -> {
        int mod = x % 5;
        return mod != 0 ? RandomI.unit(x) : RNG.notMultipleOfFive;
    });

    class JavaRNG implements RNG {
        private final Random random;

        private JavaRNG(long seed) {
            this.random = new Random(seed);
        }

        @Override
        public Tuple<Integer, RNG> nextInt() {
            return new Tuple<>(random.nextInt(), this);
        }

        public static RNG rng(long seed) {
            return new JavaRNG(seed);
        }
    }

    class Generator {
        public static Tuple<Integer, RNG> integer(RNG rng) {
            return rng.nextInt();
        }

        public static Tuple<Integer, RNG> integer(RNG rng, int limit) {
            Tuple<Integer, RNG> t = rng.nextInt();
            return new Tuple<>(Math.abs(t._1 % limit), t._2);
        }

        public static Tuple<List<Integer>, RNG> integers(RNG rng, int lenth) {
            List<Tuple<Integer, RNG>> result = List.list(CollectionsUtility.range(0, lenth)).foldLeft(List.list(), lst -> i -> lst.cons(integer(rng)));
            List<Integer> list = result.map(x -> x._1);
            //TO do fix
            Result<Tuple<List<Integer>, RNG>> result2 = Result.success(new Tuple<>(list, rng));
            return result2.getOrElse(new Tuple<>(List.list(), rng));
        }
    }
}
