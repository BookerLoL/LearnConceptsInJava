package datastructures;

import java.util.Map;
import java.util.Objects;

/**
 * A pair class to present (key, value), (left, right), and etc.
 *
 * @param <K> key type
 * @param <V> value type
 * @author Ethan
 * @version 1.0
 * @since 2021-03-28
 */
public class Pair<K, V> implements Map.Entry<K, V> {
    protected K key;
    protected V value;

    private Pair() { }

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return key;
    }

    public K setKey(K newKey) {
        K prev = key;
        key = newKey;
        return prev;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        V prev = value;
        this.value = value;
        return prev;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (o == this) {
            return true;
        }

        if (!(o instanceof Pair)) {
            return false;
        }

        Pair<K, V> other = (Pair<K, V>) o;

        return Objects.equals(getKey(), other.getKey()) && Objects.equals(getValue(), other.getValue());
    }

	@Override
	public int hashCode() {
		return Objects.hash(key, value);
	}
}
