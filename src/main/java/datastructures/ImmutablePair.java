package datastructures;

/**
 * An immutable pair class that does not allow values to be changed after being
 * set.
 * 
 * @author Ethan
 * @version 1.0
 * @since 2021-03-28
 *
 * @param <K> key type
 * @param <V> value type
 */
public class ImmutablePair<K, V> extends Pair<K, V> {
	public ImmutablePair(K key, V value) {
		super(key, value);
	}

	@Override
	public K setKey(K newKey) {
		throw new UnsupportedOperationException("Key is not mutable");
	}

	@Override
	public V setValue(V value) {
		throw new UnsupportedOperationException("Value is not mutable");
	}
}
