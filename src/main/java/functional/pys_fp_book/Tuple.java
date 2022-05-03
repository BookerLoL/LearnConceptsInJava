package functional.pys_fp_book;

public class Tuple<T, U> {
    public final T _1;
    public final U _2;

    public Tuple(T _1, U _2) {
        this._1 = _1;
        this._2 = _2;
    }

    public static <T, U> Tuple<T, U> of(T _1, U _2) {
        return new Tuple<>(_1, _2);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Tuple)) return false;
        Tuple that = (Tuple) o;
        return _1.equals(that._1) && _2.equals(that._2);
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + _1.hashCode();
        result = prime * result + _2.hashCode();
        return result;
    }
}
