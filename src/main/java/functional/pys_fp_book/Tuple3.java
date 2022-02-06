package functional.pys_fp_book;

public class Tuple3<T, U, V> {
    public final T _1;
    public final U _2;
    public final V _3;

    public Tuple3(T _1, U _2, V _3) {
        this._1 = _1;
        this._2 = _2;
        this._3 = _3;
    }

    public static <T, U, V> Tuple3<T, U, V> of(T _1, U _2, V _3) {
        return new Tuple3<>(_1, _2, _3);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Tuple3)) return false;
        Tuple3 that = (Tuple3) o;
        return _1.equals(that._1) && _2.equals(that._2);
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + _1.hashCode();
        result = prime * result + _2.hashCode();
        result = prime * result + _3.hashCode();
        return result;
    }
}