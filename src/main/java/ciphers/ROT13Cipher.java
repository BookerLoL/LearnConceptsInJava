package ciphers;

import java.util.Objects;

/**
 * Implementation of the ROT13 Cipher.
 * <p>
 * It's just Caesar Cipher with a key of 13.
 * <p>
 *
 * @author Ethan
 * @version 1.1
 */
public class ROT13Cipher implements Encodable<String, String>, Decodable<String, String>, Caseable {
    private CaesarCipher scrambler;

    public ROT13Cipher() {
        scrambler = new CaesarCipher(13);
    }

    public String encode(String message) {
        return scrambler.encode(message);
    }

    public String decode(String encoding) {
        return scrambler.decode(encoding);
    }

    @Override
    public void setCase(Case newCase) {
        Objects.requireNonNull(newCase);
        scrambler.setCase(newCase);
    }

    @Override
    public Case getCase() {
        return scrambler.getCase();
    }
}
