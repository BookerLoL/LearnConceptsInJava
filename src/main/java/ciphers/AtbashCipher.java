package ciphers;


import java.util.Objects;

import static utils.CharacterUtil.AsciiHelper.MAX_LOWERCASE_ALPHABET;
import static utils.CharacterUtil.AsciiHelper.MAX_UPPERCASE_ALPHABET;
import static utils.CharacterUtil.AsciiHelper.MIN_LOWERCASE_ALPHABET;
import static utils.CharacterUtil.AsciiHelper.MIN_UPPERCASE_ALPHABET;
import static utils.CharacterUtil.AsciiHelper.isLetter;
import static utils.CharacterUtil.AsciiHelper.isUppercaseLetter;

/**
 * Atbash Cipher implementation that can only handle ASCII.
 * Non-ASCII values will be ignored but still kept in the results.
 *
 * @author Ethan
 * @version 1.1
 * @see <a href=https://www.youtube.com/watch?v=YJMuUIIfMzg&ab_channel=SecretScreening>Video</a>
 * @see <a href=https://en.wikipedia.org/wiki/Atbash>Wikipedia</a>
 */
public class AtbashCipher implements Encodable<String, String>, Decodable<String, String>, Caseable {
    private Case casing;

    public AtbashCipher() {
        this(Case.NO_CHANGE);
    }

    public AtbashCipher(Case casing) {
        Objects.requireNonNull(casing);
        this.casing = casing;
    }

    @Override
    public String encode(String message) {
        Objects.requireNonNull(message);
        return translate(message);
    }

    @Override
    public String decode(String encoding) {
        Objects.requireNonNull(encoding);
        return translate(encoding);
    }

    private String translate(String input) {
        input = casing.transform(input);
        final char[] translatedChars = new char[input.length()];
        for (int i = 0; i < translatedChars.length; i++) {
            char ch = input.charAt(i);
            if (!isLetter(ch)) {
                translatedChars[i] = ch;
                continue;
            }

            ch = isUppercaseLetter(ch) ? uppercaseLetterShift(ch) : lowercaseLetterShift(ch);
            translatedChars[i] = ch;
        }
        return new String(translatedChars);
    }


    private char lowercaseLetterShift(char letter) {
        return (char) (MAX_LOWERCASE_ALPHABET - (letter - MIN_LOWERCASE_ALPHABET));
    }

    private char uppercaseLetterShift(char letter) {
        return (char) (MAX_UPPERCASE_ALPHABET - (letter - MIN_UPPERCASE_ALPHABET));
    }


    public static void main(String[] args) {
        AtbashCipher a = new AtbashCipher();
        String encoding = a.encode("abcdefghijklmnopqrstuvwxyz");
        System.out.println(encoding);
        String message = a.decode(encoding);
        System.out.println(message);
    }

    @Override
    public void setCase(Case newCase) {
        Objects.requireNonNull(newCase);
        this.casing = newCase;
    }

    @Override
    public Case getCase() {
        return this.casing;
    }
}
