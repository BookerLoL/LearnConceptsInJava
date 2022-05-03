package ciphers;

import utils.CharacterUtil;

import java.util.Collection;
import java.util.List;

/**
 * Ciphers that take a {@code String} input can choose to support different {@code String} inputs or return different casing despite input.
 * <p>
 * - For example, a cipher might encode the input to be all uppercase despite it being lowercase.
 */
public interface Caseable {
    public enum Case {
        UPPERCASE {
            @Override
            public String transform(String input) {
                return input == null ? input : input.toUpperCase();
            }
        },
        LOWERCASE {
            @Override
            public String transform(String input) {
                return input == null ? input : input.toLowerCase();
            }
        },
        NO_CHANGE {
            @Override
            public String transform(String input) {
                return input;
            }
        };


        public static final Collection<Case> CASES = List.of(Case.values());

        /**
         * Transform the given character based on the Case setting.
         *
         * @param input The input to transform
         * @return The transformed string based on the given case. If null, will return null.
         * @apiNote Null safe
         */
        public abstract String transform(String input);
    }

    /**
     * Update the current setting for handling String based inputs.
     *
     * @param newCase The new case to use.
     */
    public void setCase(Case newCase);

    /**
     * @return The current case setting that is being used.
     */
    public Case getCase();

    /**
     * Ciphers may not necessarily support all cases.
     *
     * @return The {@code String} cases that the cipher can support.
     * @apiNote Returning all cases in {@link Case#values()}
     */
    default Collection<Case> getSupportingCases() {
        return Case.CASES;
    }
}
