# Java

```java
//Removing duplication with lexical scoping
public static final Predicate<String> startsWith(String prefix) {
    return name -> name.startsWith(prefix);
}


public static final Function<String, Predicate<String>> startsWith = prefix -> name -> name.startsWith(prefix);
```

- reduce
  - can return optional or initial default value
- **Learn Collectors class**
  - Collectors.joining

```java
string.chars() //returns code point values of chars


public interface Identifiable {
    public String getName();
    public int getId();
}

final Function<Identifiable, String> byName = Identifiable::getName;
final Function<Identifiable, Integer> byId = Idetnfifiable::getId;

List<Identifiable> sortedIdentities = identities.stream().sorted(comparing(byName).thenComparing(byId)).collect(toList());

Map<String, List<Identifiable>> byName = identites.stream().collect(Collectors.groupingBy(Identifiable::getName));
Map<String, List<Integer>> idsByName = identities.stream().collect(groupingBy(Identifiable::getName, mapping(Identifiable::getId, toList())));

Comparator<Identifiable> byName = Comparator.comparing(Identifiable::getName);
//reducing, BinaryOperator.maxBy

Files.newDirectoryStream(Paths.get("dir"), path -> path.toString().endsWith(".java")); //stream of java files
File[] hiddenFiles = new File(".").listFiles(File::isHidden);

//Consider using flap map to return a stream for sub results
//WatchService
```

- Can consider refactoring

```java
//Basic strategy pattern
public static int totalValues(List<Money>, final Predicate<Money> moneySelector) {
    return assets.stream().filter(assetSelector).mapToInt(Money::getValue).sum();
}

//Creating delegate to lambda expressions for better strategy pattern in classes
public class Calculator {
  private Function<String, BigDecimal> priceFinder;

  public Calculator(Function<String, BigDecimal> priceFinder) {
    this.priceFinder = priceFinder;
  }

  public BigDecimal calculate(String stockName, int shares) {
    return priceFinder.apply(stockName).multiply(BigDecimal.valueOf(shares));
  }
}
```

- Streams are lazily evaluated and won't evaluate more if it doesn't have to
- Great for postponing execution until needed
- Parallel stream
  - great when collection is large and tasks are time consuming
- Declarative over Imperative
  - Reduces need for mutable variables
- Favor immutability
- Reduce side effects
  - JIT can optimize functions with no side effects
- Prefer expressions over statements
- Higher order functions
- Performance concerns

  - first time initialization, lambdas created at runtime
  - don't prematurely optimize

- Stream terminal operations

  - allMatch, anyMatch, collect, count, findAny, findFirst, forEach, forEachOrdered, max, min, noneMatch, reduce, toArray
  - flatmap in java 8

- **A lot of behavioral design patterns can be replaced with Functional code**
  - May still want class approach if logic is not simple
- Event-driven architectures are easy to implement using lambda callbacks

- **Prefer named functions ove ranonymous lambdas**

  - Always extract complex lambda functions into functions
    - try to avoid complex `-> { }`

- Large stream statements
  - avoid excessive method chaining -> use explanatory variables
    - Like temp local stream variables
- Don't take optional parameters
- Return opt9onal if signify that there might be no return value

- Avoiding Switch statements
  - [Source](https://dzone.com/articles/functional-programming-patterns-with-java-8)

```java
 public static class Movie {
        public enum Type {
            REGULAR(PriceService::computeNew);

            public final BiFunction<PriceService, Integer, Integer> algo;

            private Type(BiFunction<PriceService, Integer, Integer> algo) {
                this.algo = algo;
            }
        }
    }

    public static class PriceService {
        public int computeNew(int days) {
            return days * 0;
        }

        public int computeRegular(int days) {
            return days * 1;
        }

        public int computePrice(Movie.Type type, int days) {
            return type.algo.apply(this, days);
        }
    }
```

- Better to store functional implementations in interfaces
  - Avoid having enums implement interface or adding abstract method

```java
public interface DoSomething {
  BinaryOperator<Integer> ADDER = (a, b) -> a + b;
}
```

---

- `f.andThen(g)` equal to `g.compose(f)`
  - g compose f means: execute f and use results of f for g
  - Be very careful composing lots of functions, can run into overflow
  ```java
  //Example of writing compose
  static <T, U, V> Function<T, V> compose(Function<U, V> f, Function<T, U> g) {
    return arg -> f1.apply(f2.apply(arg));
  }
  ```
- Handling multiple arguments

  ```java
  //Just more verbose, take away Function: Integer -> (Integer -> Integer)
  Function<Integer, Function<Integer, Integer>> add = x -> y -> x + y;

  //Creating own named Function type for reuse
  public interface BinaryOperator extends Function<Integer, Function<Integer, Integer>> {}


  static <T, U, V> Function<Function<U, V>, Function<Function<T, U>, Function<T, V>>> higherCompose() {
        return (Function<U, V> f) -> (Function<T, U> g) -> (T x)
                -> f.apply(g.apply(x));

  ClassName.<Double, Integer, Double>higherCompose().apply(Double::valueOf).apply(Double::intValue).apply(10.0);



  //While many would just create BiConsumer, of Function3, Function4, etc
  //Learn to use currying
  Function<Double, Function<Double, Double>> addTax = taxRate -> price -> price + price * taxRate;
  ```

```java
//Need to an either class, Java does not provide this
Either<SomeException, Type>
```

- Ananymous vs named function
  - if used once and is short, can use anonymous
  - **Focus on clarity and maintainability**
    - Performance and usuability, use method references
  - Type inference, use method reference to provide compiler info
- Can have a `Function` inside a method/function
- Recursive functions

```java
//Need to master recursion

//Self-refrencing requires: static final, using 'this', or init block
Function<Integer, Integer> factorial = n -> n <= 1 ? n : n * this.factorial.apply(n - 1);
```

- `identity() { return t -> t }`
- Java interfaces
  - `java.util.function.Function`, `Supplier`, `Consumer`, `Runnable`
- **Debugging lambdas**

  - More difficult to debug lambdas, but less necessary to debug
  - Try ot break one-line version into several lines and set breakpoints
  - extensively unit test each component

- Control Structures Functionally

```java
public interface Effect<T> {
  void apply(T t);
}

public interface Result<T> {
  void bind(Effect<T> success, Effect<String> failure);
  void forEach(Effect<T> ef);

  public static <T> Result<T> success(String message) {
    return new Success<>(message);
  }

  public static <T> Result<T> failure(String message) {
    return new Failure<>(message);
  }

  public class Success<T> implements Result<T> {
    private final T value;
    //...

    public void bind(Effect<T> success, Effect<String> failure) {
      success.bind(value);
    }

    public void forEach(Effect<T> effect) { effect.apply(value); }
  }

  //Failure, similar logic to Success
}

someFunction("test").bind(success, failure);

static Effect<String> success = s -> System.out.println("Success with: " + s);

static Effect<String> failure = s -> System.err.println("Error message: " + s);


public class Case<T> extends Pair<Supplier<Boolean>, Supplier<Result<T>>> {
  private case(Supplier<Boolean> conditionSupplier, Supplier<Result<T>> resultSupplier) {
    super<condition, resultSupplier>
  }

  public static <T> Case<T> mcase(Supplier<Boolean> condition, Supplier<Result<T>> value) {
return new Case<>(condition, value);
  }

  public static <T> DefaultCase default(Supplier<Result<T>> value) {
    return new DefaultCase<>(() -> true, value);
  }

  private static class DefaultCase<T> extends Case<T> {
    //omit
  }

  public static <T> Result<T> match(DefaultCase<T> default, Case<T> ... matchers) {
    for (Case<T> case : matchers) {
      if (case.getCondition().get()) return case.getResult().get();
    }
    return default.getResult().get();
  }
}
```

```java
public static <T> T head(List<T> list) {
  if (list.isEmpty()) {
    throw new IllegalStateException("List is empty");
  }

  return list.get(0);
}

public static <T> List<T> tail(List<T> list) {
   if (list.isEmpty()) {
    throw new IllegalStateException("List is empty");
  }

  List<T> modifiableList = new ArrayList<>(list);
  modifiableList.remove(0);
  return Collections.unmodifiableList(modifiableList);
}

public static <T> List<T> append(List<T> list, T t) {
  List<T> ts = new ArrayList<>(list);
  ts.add(t);
  return Collections.unmodifiable(ts);
}

//Folding, transform list into single value aka reducing
public static <T, U> U foldLeft(List<T> values, U identity, Function<U, Function<T, U>> transform) {
  U result = identity;
  for (T i : values) {
    result = transform.apply(result).apply(i);
  }
  return result;
}

static <T> List<T> unfold(T seed, Function<T, T> f, Predicate<T> predicate) {
  List<T> results = new ArrayList<>();
  T temp = seed;
  while (predicate.test(temp)) {
    results.add(temp);
    temp = f.apply(temp);
  }
  return results;
}

static List<Integer> range(int start, int end) {
  return unfold(start, x -> x + 1, x -> x < end);
}
```

- Better to compose / andThen before mapping over to avoid repeated maps

  - Streams are lazily evaluated, so streams aren't really affected

- How to avoid wrong method for right type

  - Sometimes might have 2 doubles, but method used on them might be for the other

  ```java
  //Value types to represent values to avoid mixup
  public class Price {
    public static final Price ZERO = new Price(0.0);
    public static Function<Price Function<FoodLine, Price>> sum = x -> y -> x.add(y.getAmount());

    double price;

    public Price add(Price other) {
      return new Price(this.value + that.value);
    }
  }

  public class Weight {
    double weight;
  }
  ```

- Corecursion, computing steps by using output of one step as input of next step
- Recursion, same as corecursion but starts with last step first
- **No Tall Call Elmination in Java (other compilers have it)**
  - Tail call, recursion happens occurring in last position
    - Tail recursive, **often times need to use an accumlator, and a private helper method**
  - Tail call elimination, removing pushing environment to stack to process tail call
  - **Components needed for improved recursion**
    - represent **unevaluated method calls**
    - Store them in stack-like structure until encounter terminal condition
    - Evaluate calls LIFO order

```java
public interface TailCall<T> {
  TailCall<T> resume();
  T eval();
  boolean hasMore();

  public class Results<T> implements TailCall<T> {
    private final T t;
    public T eval() { return t; }
    public boolean isSuspend() { return false; }
    public TailCall<T> resume() { throw new IllegalStateException(); }
  }

  public class Suspend<T> implements TailCall<T> {
    private final Supplier<TailCall<T>> resume;
    public T eval() {
      TailCall<T> current = this;
      while (current.isSuspend()) {
        current = current.resume();
      }
      return current.eval();
    }
    public boolean isSuspend() { return true; }
    public TailCall<T> resume() { return resume.get(); }
  }

  public Results<T> finish(T data) { return new Results<>(data); }
  public Suspend<T> next(Supplier<TailCall<T>> next) { return new Suspend<>(next); }
}

static TailCall<Integer> add(int x, int y) {
  return y == 0 ? finish(x) : next(() -> add(x + 1, y - 1));
}

static Function<Integer, Function<Integer, Integer>> add = x -> y -> {
  class AddHelper {
    Function<Integer, Function<Integer, TailCall<Integer>>> addHelper = a -> b -> b == 0 ? finish(a) : next(() -> this.add.apply(a + 1).apply(b - 1));
  }

  //To get rid of the need to type 'eval'
  return new AddHelper().addHelper.apply(x).apply(y).eval();
}

```

```java
static <T> Function<T, T> composeAll(List<Function<T, T>> list) {
  return x -> {
    T y = x;
    for (Function<T, T> f : list) {
      y = f.apply(y);
    }
    return y;
  };
}
```

- Handling with lists

```java
public interface List<T> {
  public T head();
  public List<T> tail();
  public boolean isEmpty();
  public List<T> prepend(T item);
  public List<T> setHead(T item);
  public List<T> drop(int n);

  default U List<U> nil() {
    return new Nil<>();
  }

  default U List<U> list() {
    return new Nil<>();
  }

  default U List<U> list(U ... u) {
    List<U> node = nil();
    for (int  i = u.length - 1; i >= 0; i--) {
      n = new Cons<>(u[i], n);
    }
    return n;
  }

  default U List<U> concat(List<U> list1, List<U> list2) {
    return list1.isEmpty() ? list2 : new Cons<>(list1.head(), concat(list1.tail(), list2));
  }

  default T List<T> cons(T item) {
    return new Cons<>(item, this);
  }

  //To reverse list, can accumulator list to store results while traversing recursively

  private class Nil<T> implements List<T> {
    //omit rest, throw excepitons
    public boolean isEmpty() { return true; }
    public List<T> prepend(T item) { return new Cons<>(item, this); }
    public List<T> setHead(T item) { throw new IllegalStateException(); }
    public List<T> drop(int n) { return this; }
  }

  private class Cons<T> implements List<T> {
    private final T head;
    private final List<T> tail;

    public boolean isEmpty() { return false; }
    public List<T> prepend(T item) { return new Cons<>(item, this); }
    public List<T> setHead(T item) { return new Cons<>(item, tail()); }
    public List<T> drop(int n) { return n <=  0 ? this : dropHelper(this, n).eval(); }
    private TailCall<List<T>> dropHelper(List<T> list, int n) { return n <= 0 || list.isEmpty() ? finish(list) : next(() -> dropHelper(list.tail(), n-1)); }
  }
}

public Integer sum(List<Integer> numbers) { return numbers.isEmpty() ? 0 : numbers.head() + sum(numbers.tail()); }


public <T, U> TailCall<U> foldLeftHelper(B acc, List<A> list, Function<B, Function<A, B>> f) {
  return list.isEmpty() ? finish(acc) : next(() -> foldLeftHelper(f.apply(acc).apply(list.head()), list.tail(), f));
}

public Integer length(List<Integer> list) {
  return foldLeftHelper(0, list, x -> ignore -> x + 1);
}

public Double product(List<Double> list) {
  return foldLeftHelper(1.0, list, x -> y -> x * y);
}

public <A> List<A> reverse(List<A> list) {
  return foldLeft(List.list(), list, x -> x::cons);
}

//Can use foldLeft to implement map, filter, flatMap, etc
```

- FP, often involves terms of what intended result is rather than how to obtain it
- Find patterns that can be abstracted
- Optional Data / Dealing with null
  - Using list, has drawbacks
  - return sentinel value
  - throw exception
  - return null (worst solution)
  - Have user provide default value

```java
public interface Optional<T> {
  T get();
  T getOrElse(Supplier<T> defaultValue);
  <U> Optional<U> map(Function<T, U> f);

  default <U> Optional<U> flatMap(Function<T, Optional<U>> f) {
    return map(f).getOrElse(Optional::empty);
  }

  default Optional<T> orElse(Supplier<Optional<T>> defaultValue) {
    return map(x -> this).getOrElse(defaultValue);
  }

  private static final Optional<?> EMPTY = new Empty<>();

  public static <T> Optional<T> empty() {
    return (Optional<T>) EMPTY;
  }

  public static <T> Optional<T> of(T value) {
    return value == null ? empty() : new Value<>(value);
  }

  private static class Empty<T> implements Optional<T> {
    public T get() { throw IllegalStateException(); }
    public T getOrElse(Supplier<T> defaultValue) { return defaultValue.get(); }
    public <U> Optional<U> map(Function<T, U> f) { return this; }

    public boolean equals(Object o) { return this == o || o instanceof Empty; }

    public int hashCode() { return 0; }
  }

  private static class Value<T> implements Optional<T> {
    private final T value;

    public T get() { return value; }
    public T getOrElse(Supplier<T> defaultValue) { value != null ? value : defaultValue.get();  }
    public <U> Optional<U> map(Function<T, U> f) { return new Value<>(f.apply(value)); }

    public boolean equals(Object o) { return (this == o || o instanceof Value) && value.equals(((Value<?>)o).value); }

    public int hashCode() { return Objects.hashCode(value); }

  }

  static <T, U> Function<Optional<T>, Optional<U>> lift(Function<T, U> f) {
    return x -> {
      try {
        return Optional.of(x).map(f);
      } catch (Exception e) {
        return Optional.empty();
      }
    }
  }

  static <A, B, C, D> map3Args(Optional<A> a, Optional<B> b, Optional<C> c, Function<A, Function<B, Function<C, D>>> f) {
    return a.flatMap(ax -> b.flatMap(bx -> c.map(cx -> f.apply(ax).apply(bx).apply(cx))));
  }
}

```

- Necessary methods for all use cases to do with value for all use cases

  - use value as input to another function
  - apply effect to value
  - use value if it's not null or use default value to apply a function or an effect

- Use Functional methods instead of Functions as often as possible
  - Prefer function answer over nonfunctional techniques if possible
- Original use of Optional
  - needed clear way to represent no result and using null would cause errors
  - Almost never use it as a field or method parameter
  - **Never use optional.get** unless prove it will never be null
    - use orElse or ifPresent
- Should you use Optional as originally intended?
  - Just do what you want, don't do it without thinking
  - Some properties could use Optionals when appropriate
    - don't routinely use it for get methods
- Consider using final classes
- **Often times in business code, should probably just throw an error**

```java
//Can create your own Either
 public interface Either<T, U> {

        class Left<T, U> implements Either<T, U> {
            T value;
        }

        class Right<T, U> implements Either<T, U> {
            U value;
        }

        public static <T, U> Either<T, U> left(T value) { new Left<>(value); }
        public static <T, U> Either<T, U> right(U value) { new Right<>(value); }
    }
```

```java
//Similar to Optional but also storing an Exception
//Can also create Empty class
//Can also convert to Optional
public interface Result<V> {
  <U> Result<U> map(Function<V, U> f);
  <U> Result<U> flatMap(Function<V, Result<U>> f);
  V getOrElse(V defaultValue);
  V getOrElse(Supplier<V> defaultValue);
  Result<V> orElse(Supplier<Result<V>> defaultValue);
  Result<V> mapFailure(String s, Exception e);
  Result<V> mapFailure(Exception e);

  class Failure<V> implements Result<V> {
    RuntimeException exception;
  }
  class Success<V> implements Success<V> {
    V value;
  }

  public static <V> Result<V> failure(String message) {}
  public static <V> Result<V> failure(Exception e) {}
  public static <V> Result<V> success(V value) {}

  public default Result<V> filter(Predicate<V> p) {
    return flatMap(x -> p.test(x) ? this : failure("Failed condition"))
  }

  public default exists(Predicate<V> p) {
    return map(p).getOrElse(false);
  }

  public static <V> Result<V> of(V value);
  public static <V> Result<V> of(V value, String message);
  public static <V> Result<V> of(Predicate<V> predicate, T value);
  public static <V> Result<V> of(Predicate<V> predicate, T value, String message);
}
```

- Applying an effect
  - forEach, forEachOrThrow, forEachOrException
  - Ch 13 for more

```java
static Result<String> getFirstName() {}
getFirstName().flatMap(firstName -> getLastName().flatMap(lastName -> getMail().map(mail -> new Person(firstName, lastName, mail))));

//Function<String, Function....> same way of writing this

public static <A, B, C, D> Function<Result<A>,
Function<Result<B>, Function<Result<C>,
Result<D>>>> lift3(Function<A, Function<B, Function<C, D>>> f) {
return a -> b -> c -> a.map(f).flatMap(b::map).flatMap(c::map);
}
```

```java
//Comprehension pattern
result1.flatMap(p1 -> result2.flatMap(p2 -> result3.flatMap(p3 -> result4.flatMap(p4 -> result5.map(p5 -> function(p1, p2, p3, p4, p5))))));
```

- pg 225
