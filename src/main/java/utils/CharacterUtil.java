package utils;

import java.util.List;
import java.util.stream.IntStream;

public class CharacterUtil {
    private CharacterUtil() {
    }

    /**
     * A helper class that handles American Standard Code for Information Interchange characters.
     *
     * @see <a href="http://facweb.cs.depaul.edu/sjost/it212/documents/ascii-pr.htm">Ascii Source</a>
     */
    public static class AsciiHelper {
        //Number of letters in English alphabet: a-z
        public static final int NUM_LETTERS = 26;

        public static final char MIN_CHAR = 0;
        public static final char MAX_CHAR = 127;
        public static final char MIN_PRINTABLE_CHAR = 32;
        public static final char MAX_PRINTABLE_CHAR = 126;

        public static final char MIN_LOWERCASE_ALPHABET = 'a';
        public static final char MAX_LOWERCASE_ALPHABET = 'z';
        public static final char MIN_UPPERCASE_ALPHABET = 'A';
        public static final char MAX_UPPERCASE_ALPHABET = 'Z';

        //Making list to allow for random accessing
        //+1 due to exclusive end
        public static final List<Character> LOWERCASE_ALPHABET = IntStream.range(MIN_LOWERCASE_ALPHABET, MAX_LOWERCASE_ALPHABET + 1).mapToObj(i -> Character.valueOf((char) i)).toList();
        public static final List<Character> UPPERCASE_ALPHABET = IntStream.range(MIN_UPPERCASE_ALPHABET, MAX_UPPERCASE_ALPHABET + 1).mapToObj(i -> Character.valueOf((char) i)).toList();

        public static boolean isPrintableAscii(char ch) {
            return MIN_PRINTABLE_CHAR <= ch && ch <= MAX_PRINTABLE_CHAR;
        }

        public static boolean isLetter(char ch) {
            return isLowercaseLetter(ch) || isUppercaseLetter(ch);
        }

        public static boolean isUppercaseLetter(char ch) {
            return MIN_UPPERCASE_ALPHABET <= ch && ch <= MAX_UPPERCASE_ALPHABET;
        }

        public static boolean isLowercaseLetter(char ch) {
            return MIN_LOWERCASE_ALPHABET <= ch && ch <= MAX_LOWERCASE_ALPHABET;
        }

        public static void main(String[] arsgs) {
            System.out.println(LOWERCASE_ALPHABET);
        }
    }
}
