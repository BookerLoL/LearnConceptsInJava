package ciphers;

import datastructures.counters.BoundedCounter;

import java.util.Objects;

import static utils.CharacterUtil.AsciiHelper.LOWERCASE_ALPHABET;
import static utils.CharacterUtil.AsciiHelper.MAX_LOWERCASE_ALPHABET;
import static utils.CharacterUtil.AsciiHelper.MAX_UPPERCASE_ALPHABET;
import static utils.CharacterUtil.AsciiHelper.MIN_LOWERCASE_ALPHABET;
import static utils.CharacterUtil.AsciiHelper.MIN_UPPERCASE_ALPHABET;
import static utils.CharacterUtil.AsciiHelper.UPPERCASE_ALPHABET;
import static utils.CharacterUtil.AsciiHelper.isLetter;
import static utils.CharacterUtil.AsciiHelper.isUppercaseLetter;

/**
 * Caesar Cipher implementation that can only handle ASCII.
 * Non-ASCII values will be ignored but still kept in the results.
 *
 * @author Ethan
 * @version 1.1
 */
public class CaesarCipher implements Encodable<String, String>, Decodable<String, String>, Caseable {
    private final BoundedCounter lowercaseLetterBound = new BoundedCounter(MIN_LOWERCASE_ALPHABET, MAX_LOWERCASE_ALPHABET);
    private final BoundedCounter uppercaseLetterBound = new BoundedCounter(MIN_UPPERCASE_ALPHABET, MAX_UPPERCASE_ALPHABET);


    public static final int DEFAULT_SHIFTS = 1;
    public static final int MIN_SHIFTS = 0;
    public static final int MAX_SHIFTS = 26;

    private int key;
    private Case casing;

    public CaesarCipher() {
        this(DEFAULT_SHIFTS, Case.NO_CHANGE);
    }

    public CaesarCipher(int shifts) {
        this(shifts, Case.NO_CHANGE);
    }

    public CaesarCipher(int shifts, Case casing) {
        Objects.requireNonNull(casing);

        this.key = getValidShifts(shifts);
        this.casing = casing;
    }

    private static int getValidShifts(int shifts) {
        if (shifts < MIN_SHIFTS) {
            shifts = DEFAULT_SHIFTS;
        } else if (shifts > MAX_SHIFTS) {
            shifts = shifts % MAX_SHIFTS;
        }
        return shifts;
    }


    public String encode(String message) {
        Objects.requireNonNull(message);
        return translate(message, true);
    }

    public String decode(String encoding) {
        Objects.requireNonNull(encoding);
        return translate(encoding, false);
    }

    private String translate(String input, boolean encodeFlag) {
        final char[] translatedChars = new char[input.length()];
        input = casing.transform(input);

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);

            if (!isLetter(ch)) {
                translatedChars[i] = ch;
                continue;
            }

            translatedChars[i] = isUppercaseLetter(ch) ? shiftUppercaseLetter(ch, encodeFlag) : shiftLowercaseLetter(ch, encodeFlag);
        }

        return new String(translatedChars);
    }

    private char shiftUppercaseLetter(char letter, boolean shiftForwards) {
        uppercaseLetterBound.setCurrentPosition(letter);
        if (shiftForwards) {
            uppercaseLetterBound.increment(key);
        } else {
            uppercaseLetterBound.decrement(key);
        }
        int shiftedIndex = uppercaseLetterBound.getCurrentPosition() - uppercaseLetterBound.getStartBound();
        return UPPERCASE_ALPHABET.get(shiftedIndex);
    }

    private char shiftLowercaseLetter(char letter, boolean shiftForwards) {
        lowercaseLetterBound.setStartPosition(letter);
        if (shiftForwards) {
            lowercaseLetterBound.increment(key);
        } else {
            lowercaseLetterBound.decrement(key);
        }
        int shiftedIndex = lowercaseLetterBound.getCurrentPosition() - lowercaseLetterBound.getStartBound();
        return LOWERCASE_ALPHABET.get(shiftedIndex);
    }

    public static void main(String[] args) {
        CaesarCipher c = new CaesarCipher(2, Case.UPPERCASE);
        String encoding = c.encode("abcdefghijklmnopqrstuvwxyz");
        System.out.println(encoding);
        String message = c.decode(encoding);
        System.out.println(message);
    }

    @Override
    public void setCase(Case newCase) {
        Objects.requireNonNull(newCase);
        this.casing = newCase;
    }

    @Override
    public Case getCase() {
        return casing;
    }
}
