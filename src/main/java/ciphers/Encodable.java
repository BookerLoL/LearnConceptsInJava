package ciphers;

/**
 * Classes that implement this class is able to encode a specific type of input and return a specific output.
 *
 * @param <T> Type to encode
 * @param <V> Type to return after encoding
 */
public interface Encodable<T, V> {
    V encode(T object);
}
