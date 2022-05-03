package datastructures;

import java.util.Objects;

/**
 * An pair class that does not allow values null values to be set.
 * 
 * @author Ethan
 * @version 1.0
 * @since 2021-03-28
 *
 * @param <K> key type
 * @param <V> value type
 */
public class NonNullPair<K, V> extends Pair<K, V> {

	public NonNullPair(K key, V value) {
		super(key, value);
	}

	@Override
	public K setKey(K newKey) {
		Objects.requireNonNull(newKey);
		K prev = key;
		key = newKey;
		return prev;
	}

	@Override
	public V setValue(V value) {
		Objects.requireNonNull(value);
		V prev = value;
		this.value = value;
		return prev;
	}
}
