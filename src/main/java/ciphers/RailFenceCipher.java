package ciphers;

import java.util.Objects;

/**
 * Implementation of the rail fence cipher.
 * <p>
 * Non-letter characters are not removed or transformed.
 * <p>
 * References:
 * http://practicalcryptography.com/ciphers/classical-era/rail-fence/
 * https://crypto.interactive-maths.com/rail-fence-cipher.html
 *
 * @author Ethan
 * @version 1.0
 * @since 2021-04-07
 */
public class RailFenceCipher implements Encodable<String, String>, Decodable<String, String>, Caseable {
    public static final int DEFAULT_ROWS = 2;
    private static final char DEFAULT_EMPTY = '-';

    private Case casing;
    private int key;

    public RailFenceCipher() {
        this(DEFAULT_ROWS, Case.NO_CHANGE);
    }

    public RailFenceCipher(int rows) {
        this(rows, Case.NO_CHANGE);
    }

    public RailFenceCipher(int rows, Case casing) {
        Objects.requireNonNull(casing);
        key = rows < DEFAULT_ROWS ? DEFAULT_ROWS : rows;
        this.casing = casing;
    }

    public String encode(String message) {
        Objects.requireNonNull(message);
        message = casing.transform(message);
        char[][] railField = getRailField(message);
        return readEncoding(railField);
    }

    private char[][] getRailField(String message) {
        char[][] railField = new char[key][message.length()];

        boolean goingUp = false;
        for (int row = 0, col = 0; col < message.length(); col++) {
            railField[row][col] = message.charAt(col);

            if (goingUp) {
                row--;

                if (row == -1) {
                    row = 1;
                    goingUp = false;
                }
            } else {
                row++;

                if (row == railField.length) {
                    row -= 2;
                    goingUp = true;
                }
            }
        }

        return railField;
    }

    private String readEncoding(char[][] railField) {
        StringBuilder sb = new StringBuilder(railField[0].length);
        for (char[] row : railField) {
            for (char ch : row) {
                if (ch != '\0' && ch != DEFAULT_EMPTY) {
                    sb.append(ch);
                }
            }
        }
        return sb.toString();
    }

    public String decode(String encoding) {
        Objects.requireNonNull(encoding);
        encoding = casing.transform(encoding);
        char[][] encodedRailField = getEncodedRailField(encoding);
        return readMessage(encodedRailField);
    }

    private char[][] getEncodedRailField(String encoding) {
        String emptyMessage = repeat("-", encoding.length());
        char[][] railField = getRailField(emptyMessage);

        for (int index = 0, rowIndex = 0, colIndex = rowIndex; index < encoding.length(); ) {
            char[] row = railField[rowIndex];

            colIndex = getNextEmptyCol(row, colIndex);

            if (colIndex == -1) {
                rowIndex++;
                colIndex = 0;
            } else {
                row[colIndex] = encoding.charAt(index);
                colIndex++;
                index++;
            }
        }

        return railField;
    }

    private int getNextEmptyCol(char[] row, int startCol) {
        for (int col = startCol; col < row.length; col++) {
            if (row[col] == DEFAULT_EMPTY) {
                return col;
            }
        }
        return -1;
    }

    private String repeat(String message, int count) {
        StringBuilder sb = new StringBuilder(message.length() * count);
        while (count > 0) {
            sb.append(message);
            count--;
        }
        return sb.toString();
    }

    private String readMessage(char[][] railField) {
        StringBuilder sb = new StringBuilder(railField[0].length);

        boolean goingUp = false;
        for (int row = 0, col = 0; col < railField[row].length; col++) {
            sb.append(railField[row][col]);

            if (goingUp) {
                row--;

                if (row == -1) {
                    row += 2;
                    goingUp = false;
                }
            } else {
                row++;

                if (row == railField.length) {
                    row -= 2;
                    goingUp = true;
                }
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        RailFenceCipher c = new RailFenceCipher(4);
        String encoding = c.encode("defend the east wall of the castle");
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
        return this.casing;
    }
}
