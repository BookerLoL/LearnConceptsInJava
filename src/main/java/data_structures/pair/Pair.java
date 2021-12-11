package datastructures.pair;

import java.util.Map;

public abstract class Pair<K, V> implements Map.Entry<K, V> {
    protected K key;
    protected V val;

    public Pair(K key, V value) {
        this.key = key;
        val = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return val;
    }

    public K setKey(K newKey) {
        K oldKey = key;
        key = newKey;
        return oldKey;
    }

    public V setValue(V value) {
        V prev = val;
        val = value;
        return prev;
    }
}
