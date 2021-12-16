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
-
