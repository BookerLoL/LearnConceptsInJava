# Java Code Optimizations

- ### Accessing a variable is faster than calling a method to access it

  - Calling a method does have overhead

- ### Generally better to buffer byte streams

  - Depends on usage

- ### for loops > for each loop > iterator > stream for performance

  - use for loops for primitives for best performance
  - Parallel streams can perform well depending on context

- ### Optimizing for loops

  - Comparing against `0` is almost always more efficient in any language
    - `<0`, `!= 0`, etc
    - `System.arraycopy` is the fastest for copying due to being a `native` method
    - If don't need to copy
      - Consider getting rid of condition statement and catch ArrayIndexOutOfBoundsException

- ### Reuse common subexpressions

```java

double slowResults=(scalar*(limit/max)*value1)+(scalar*(limit/max)*value2);
        double scaled=scalar*(limit/max);
        double fastResults=scaled*(value1+value2);
```

- ### Equivalent Iterative code is faster than recursion

  - Recursion relies on calling functions which is major overhead

- ### Nulling objects can help prevent leaked memory
  - Java 9, GC is smart enough to detect when memory is freed but not smart enough when leaked memory
- ### Wildcard package does not matter for performance

  - Using specific packages will help avoid package conflicts and for readability

- ### Primitives are faster than Wrapper classes

  - Wrapper classes will auto-unbox and auto-box which is overhead

- ### String Concatenation
  - When looping or concatenating across multiple lines or functions
    - Use StringBuilder
  - If using in a single line, it's fine to use `+` to concatenate
  - Micro-optimization is to pre-allocate enough memory for the StringBuilder

```java
for (String string : strings) { //this is why it's slow
    //result = new StringBuilder(result).append(string).toString();
    result += string;
}

//equivalentCompilerCode = new StringBuilder("x").append("y").append("z").toString();
"x" + "y" + "z";
```

- ### Switch cases is often faster than if-else if-...

  - the general performance optimization is gained around 3+ branches
    - Low branches can cause poor JIT optimization
  - If statement will also try to do hottest branch first predication if it's possible which can perform really well depending on case

- ### Unrolling for loops is better
  - Rather than having if statements inside a loop, if it outside then run a loop to avoid conditional checking
    - LinkedList implementation does this
- ### Use `static final` for Constants
  - Bare in mind not all `static final` are at compile time, if rely on function or object then its runtime
  - Constant Expressions / Constant Folding
    - Compiler optimization technique
    - Conditions
      - Operation between compile-time constants
        - `static final String str = "abc".toUppercase(); //not compile time`
      - Constant-time constants like literals or String

```java
//Java will try to see if its possible to do constant folding, do your best to code correctly
String a = "a";
String b = "b";
String not_at_compile_time = a + b;

final String aa = "a";
final String bb = "b";
String at_compile_time = "a" + "b";
at_compile_time = aa + bb;
```

- ### Avoid Finalizers
- ### Parsing and doing Date / Time operations is really slow
  - Passing around unix timestamp and some simple math can signicantly improve performance

```java
//fast
long newTime = state.time + 24 * state.oneHour;

//slow
new Date(newTime);

//Very slow
new Date(date.getTime() + 24 * state.oneHour);
```

- ### Avoid String.format over concatenation
- ### Specifiy Collection Size during Initialization if possible
- ### Check if collection is random access to have better performance for get
- ### Consider using Set with a List to determine if something exists for constant contains
- ### Use collection.isEmpty over Collection.size to check if empty
  - Can't be sure that Collection.size is constant
- ### Try to limit instantiating objects
  - Prefer to clear content or reuse objects

```java
StringBuilder sb = new StringBuilder();
for (String s : strings) {
    sb.setLength(0); //better than sb = new StringBuilder
}
```

- ### Casting can become expensive
  - Cast and save into variable
  - Casting gets slower when casting to an interface
- ### Incrementing ints

  - Use `int++` over `+=` due to special bytecode to optimize speed
    - only applicable to ints
  - `+=` and `= +` no difference

- ### Dividing ints

  - Shifting `x <<= 1` is slightly faster than `x /= 2`

- ### Improving method invocation speed
  - Consider inlining small methods
    - Eliminate need to call functions
  - Have statically-compiled functions
    - Either private, final or static methods are compile-time functions
- ### Accessing local variables are the fastest
  - Special byte code for first 4 local/paramaeters
    - If instance method, then `this` takes up 1 of the special spots
    - double and long occupy 2 spots
- ### Avoid creating new Arrays in methods

  - They are always executed at runtime
    - better to use a static or instance variable

- ### Streams and Optionals add significant overhead over foreach loops

  - Hot code paths should avoid using these

- ### Regular Expressions are comptuationally intensive
  - At least cache Pattern reference
- ### Use entrySet if need both key and value during map iteration
- ### Use EnumMap / EnumSet for enums
  - they use `.ordinal` rather than `hash` for quick access
- ### Optimize hashcode and Equals
  - for equals, check `this == argument`, `this instanceof argument` first
  - hashcode, consider cases where certain properties can be the hash code for quick hash code generation
    - better hashcode will result in less `equals` being called
- ### Never call new for wrappers unless you want a new instance
  - Heavily prefer calling `valueOf`
- ### Use primivitives and stack and not the heap
- ### Consider replacing index based iterators with for loop with get method
- ### Apache Commons StringUtils.replace is better for Java 8
  - Java 9, almost equal performance
  - Java 13, faster
- ### Try upgrading Java versions
  - Newer Java versions tend to have better optimized code changes
