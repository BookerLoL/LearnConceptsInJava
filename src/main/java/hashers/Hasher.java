package hashers;

import test.Validator;

public abstract class Hasher {
	protected static final String INPUT_NULL_MESSAGE = "The input should not be null";

	protected static final void checkValidInput(Object object) {
		Validator.checkValid(object != null, INPUT_NULL_MESSAGE);
	}
}
