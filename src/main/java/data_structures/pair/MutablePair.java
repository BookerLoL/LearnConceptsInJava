package datastructures.pair;

public class MutablePair<K, V> extends Pair<K, V> {
	public MutablePair() {
		super(null, null);
	}

	public MutablePair(K key, V value) {
		super(key, value);
	}

	public static <K, V> MutablePair<K, V> make(K key, V value) {
		return new MutablePair<>(key, value);
	}
}
