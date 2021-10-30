package ciphers;

/**
 * Classes that implement this class is able to decode a specific type of input and return a specific output.
 * - This is often used to decode something that has been encoded
 *
 * @param <T> Type to decode
 * @param <V> Type to return after encoding
 */
public interface Decodable<T, V> {
    V decode(T encoding);
}
