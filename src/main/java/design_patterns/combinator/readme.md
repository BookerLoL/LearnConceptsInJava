# Combinator Design Pattern

Functional programming style to combine functions

- Function.andThen and Function.compose do this
- [Source](https://dzone.com/articles/introducing-combinators-part-1)

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

Other examples

```java
@FunctionalInterface
    public interface Before<T, R> extends Function<Consumer<T>, Function<Function<T, R>, Function<T, R>>> {
        static <T, R> Before<T, R> create() {
            return before -> function -> argument -> {
                before.accept(argument);
                return function.apply(argument);
            };
        }

        static <T, R> Function<T, R> decorate(Consumer<T> before, Function<T, R> function) {
            return Before.<T, R>create().apply(before).apply(function);
        }
    }

    @FunctionalInterface
    public interface After<T, R> extends Function<Function<T, R>, Function<BiConsumer<T, R>, Function<T, R>>> {
        static <T, R> After<T, R> create() {
            return function -> after -> argument -> {
                R result = function.apply(argument);
                after.accept(argument, result);
                return result;
            };
        }

        static <T, R> Function<T, R> decorate(Function<T, R> function, BiConsumer<T, R> after) {
            return After.<T, R>create().apply(function).apply(after);
        }
    }

    @FunctionalInterface
    public interface Provided<T, R> extends Function<Predicate<T>, Function<Function<T, R>, Function<Function<T, R>, Function<T, R>>>> {
        static <T, R> Provided<T, R> create() {
            return condition -> function -> fallback -> arg ->
                    (condition.test(arg) ? function : fallback).apply(arg);
        }

        static <T, R> Function<T, R> decorate(Predicate<T> condition, Function<T, R> function, Function<T, R> fallback) {
            return Provided.<T, R>create().apply(condition).apply(function).apply(fallback);
        }
    }

    @FunctionalInterface
    public interface Precondition<T, R, X extends RuntimeException> extends Function<Predicate<T>, Function<Function<T, R>, Function<Function<T, X>, Function<T, R>>>> {
        static <T, R, X extends RuntimeException> Precondition<T, R, X> create() {
            return condition -> function -> error -> Provided.decorate(
                    condition, function,
                    arg -> {
                        throw error.apply(arg);
                    });
        }

        static <T, R, X extends RuntimeException> Function<T, R> decorate(Predicate<T> condition, Function<T, R> function, Function<T, X> error) {
            return Precondition.<T, R, X>create().apply(condition).apply(function).apply(error);
        }
    }

    @FunctionalInterface
    public interface Postcondition<T, R, X extends RuntimeException> extends Function<Function<T, R>, Function<BiPredicate<T, R>, Function<BiFunction<T, R, X>, Function<T, R>>>> {
        static <T, R, X extends RuntimeException> Postcondition<T, R, X> create() {
            return function -> condition -> error -> After.decorate(
                    function,
                    (argument, result) -> {
                        if (!condition.test(argument, result)) {
                            throw error.apply(argument, result);
                        }
                    });
        }

        static <T, R, X extends RuntimeException> Function<T, R> decorate(Function<T, R> function, BiPredicate<T, R> condition, BiFunction<T, R, X> error) {
            return Postcondition.<T, R, X>create().apply(function).apply(condition).apply(error);
        }
    }

```
