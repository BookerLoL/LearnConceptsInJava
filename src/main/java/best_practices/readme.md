# Best Practices in Java

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
