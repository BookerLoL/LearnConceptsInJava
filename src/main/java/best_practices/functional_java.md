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
