# Combinator Design Pattern

Functional programming style to combine functions
- Function.andThen and Function.compose do this

```java
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Function;

public class User {
    public int age;
    public String name;
}

interface ValidationResult {
    static ValidationResult valid() {
        return ValidationSupport.valid();
    }

    static ValidationResult invalid(String reason) {
        return new Invalid(reason);
    }

    boolean isValid();

    Optional<String> getReason();
}

private static final class Invalid implements ValidationResult {
    private final String reason;

    Invalid(String reason) {
        this.reason = reason;
    }

    public boolean isValid() {
        return false;
    }

    public Optional<String> getReason() {
        return Optional.of(reason);
    }
}

private static final class ValidationSupport {
    private static final ValidationResult VALID = new ValidationResult() {
        @Override
        public boolean isValid() {
            return true;
        }

        @Override
        public Optional<String> getReason() {
            return Optional.empty();
        }
    }

    static ValidationResult valid() {
        return VALID;
    }
}

interface UserValidation extends Function<User, ValidationResult> {
    static UserValidation does(Predicate<User> p, String message) {
        return user -> p.test(user) ? ValidationResult.valid() : ValidationResult.invalid(message);
    }

    default UserValidation and(UserValidation other) {
        return user -> {
            final ValidationResult result = this.apply(user);
            return result.isValid() ? other.apply(user) : result;
        };
    }

    default UserValidation or(UserValidation other) {
        return user -> {
            final ValidationResult result = this.apply(user);
            return result.isValid() ? result : other.apply(user);
        };
    }

    static UserValidation any(UserValidation... others) {
        return user -> {
            for (UserValidation validation : others) {
                final ValidationResult result = validation.apply(user);
                if (result.isValid()) {
                    return result;
                }
            }
            return ValidationResult.invalid("None of the validation rules were valid");
        }
    }
}

public class Example {
    public static void main(String[] args) {
        UserValidation nonNullName = user -> Objects.nonNull(user.name) ? ValidationSupport.valid() : ValidationResult.invalid("Required non-null name");
        UserValidation aboveEighteen = user -> user.age > 18 ? ValidationSupport.valid() : ValidationResult.invalid("Age must be above 18");
        UserValidation userValidation = nonNullName.and(aboveEighteen);
        ValidationResult result = userValidation.apply(new User());
        result.getReason().ifPresent(System.out::println);
    }
}
```