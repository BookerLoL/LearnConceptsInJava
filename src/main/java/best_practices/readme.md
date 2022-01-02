# Best Practices in Java

## Best Overall techniques

- Should read guides / read books / watch vidoes / etc from experts to learn whats best practice
- Abstract classes are harmful, inheritance tree propagated changes
  - Avoid abstract classes as much as possible
- Composition over inheritance

```java
public interface Logger {
  void log(String message);

  default Logger filter(Predicate<String> filter) {
    return message -> {
      if (filter.test(message)) {
        log(message);
      }
    };
  }
}

Logger logger = msg -> System.out::println;
Logger filteredLogger = logger.filter(msg -> !msg.isEmpty());
```

## TODO organize the notes

- algorithm-heavy, micro optimised library code, code that really performs well usually more important than code that doesn't need comments

- anonymous, local or inner class, check if you can make it static or even a regular top-level class.
- Avoid returning anonymous, local or inner class instances from methods to the outside scope.
- Double braces are bad!

  - Common mistake when initializing a `Map`
  - anti-pattern, memory leak, not necessarily good for readability

- Better to expect interface objects rather than more parameters

  - Interfaces can provide methods in which you can retrieve the parameters you need

- Prefer returning Optionals rather than null

  - Arrays or Collections should never be null
  - **Never return null**, return **Optional** instead to make it clear
  - Don't use optional as a parameter

- Avoid trying to overload `var-args` methods

- Creating union types, just create an interface and have classes implement it
- Streams

  - **filter/limit, map, collect order**
    - if can't filter, map the least heavy processing first, filter, then process heavy near the end
  - Flatmap may not be lazy dependign on jdk8 version

- Some design patterns can be applied using lambdas

  - Interfaces highly consider static methods

- Consider creating your own tag if certain tags aren't good

  - Annotation processor can be useful

  ```java
  public @interface Warning {
      String name() default "warning";
      String description() default "";
  }
  ```

- If you want unsigned primitives, need to create class and extend Number

  - can consider Comparator too

- Wrapper classes
  - always use `equals` if comparing for value and not memory reference
    - some values work because they are cached
  - Because of auto-unboxing for ternary operator as they can promote based on highest operand
- Local Caching

  - Consider using HashMap

- If adding enum

  - Always look at what references that enum, in case there are logic that should be updated

- Lambdas

  - Best lambdas are thin one-liners
  - extract heavy lambdas into named ::methods
  - If long streams, extract them into explanatory variables

- [Memory calculation](https://stackoverflow.com/questions/37916136/how-to-calculate-memory-usage-of-a-java-program)

- Many small functions with long descriptive names
- Less tabs is easier to read
- Early returns
- Write simplest code that works
- Implement equals and hash code for classes
  - Use `equals` for equality checking
  - `==` if same object reference

## Best Testing Practices

- Arrange/Act/Assert
  - Given/When/Then
- Test Design Goals
  - Sensitive (fail for any bugs)
    - High coverage %
    - correct tests
  - Specific (precise failures)
    - expressive
    - isolated and robust
    - low overlap
  - Fast
  - Few
    - small, DRY tests
- More test codes than production code

## Functional Java Mistakes

- Avoid big blocks of lambda expressions
  - Just make it a function
- Only use streams once!!
- Don't pass them around Streams without control of it
  - can pass if private intermediate function
  - pass when result is very large / infinite
  - default to collection
- Consume / terminate the stream
- Just use peek for printing / seeing progress
  - can introduce side effects
- Java8 flat map is not lazily evaluated

## NullPointerException handling

- Self-validate before
  - if check -> throw exception
  - `Objects.requireNonNull(var)`
  - `@NonNull` Lombok
- If must return null

  - return Optional instead
  - consider changing `get` methods to return Optional

- Don't use optionals for DTO/API model
  - Use optional for domain model
- Always initialize collection fields with empty one

## SneakThrows Lombok

- Great if don't intend to selectiviely catch
- otherwise, wrap checked exceptions in runtime exceptions that you throw

## Exceptions

- Use the exception message to report any technical details about the error that might be useful for developers investigating the exception.
- **catch-rethrow-with-debug-message pattern**
  `throw new RuntimeException("For id: " + keyDebugId, e);`
- Keep your exception messages concise and meaningful. Just like comments.
- Only create a new exception type E1 if you need to catch(E1) and selectively handle that particular exception type, to work-around or recover from it.
- Developers should never be afraid to let go of a runtime exception.
- translate the error codes on the backend.
- Avoid displaying the exact error cause to your users unless they can do something to fix it.

## Code Review

- Have person sitting down to review code
  - give suggestions and see what they think
    - "what do you think fo extracting this part into a separate function?"
- Before commiting changes, **revert all improvements and them make those changes themselves**
  - **So they learn to do it themselves**
- It's fine to allow flawed code to pass reviews
  - encourage people to do their best and make conscious decisions
  - Look for **their best designs**
  - **if obvious error or bug**, fix it and explain problem
  - **If design changes elaborate**, use code style above
  - If subtle / nuanced coding practices, **leave it your way**
    - don't argue over small semantics
- **Not all design flaws grow into real issues**
- Learn, don't blame
  - **Be vareful with words, tones, and attitude you have in reviews**
  - Put effort into understanding the chosen solution, big picture
    - **Don't aim for apparent flaws and bugs**
- Senior needs to learn **kindness, empathy, and teaching**
- Consider team meeting if some issues arise in multiple code reviews
- If inexperienced / unsure / poor day
  - Use more 'guided' teaching method and gently walk them through the significant issues, correcting and deeply explaining each one
- Help the reviewer, If more experienced, must mind their time
  - break them into separate steps
  - create many little commits
  - commit massive refactoring separately
  - sketch diagram for more complex design
  - offer to provide walkthrough to reviewer to explain idea
- Conclusion
  - be kind and humble
  - discuss code constructively
  - author of code should have final word
  - concentrate on facts, not opinions or style
  - accept imperfect solutions to preserve team spirit
  - submit reviewer friendly pull requests with explanatory, fine-grained commits
- Pair programming is good alternative to code reviews
