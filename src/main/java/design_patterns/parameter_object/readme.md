# Paramter Object Design Pattern

**Behavorial** design pattern

- Can provide default values for parameters, avoids lots of method overloading, and avoids large amount of parameters
- [Source](https://java-design-patterns.com/patterns/parameter-object/)

```java
public class ParameterObject {
  public static final String DEFAULT_SORT_BY = "price";
  public static final SortOrder DEFAULT_SORT_ORDER = SortOrder.ASC;

  private String type;
  private String sortBy = DEFAULT_SORT_BY;
  private SortOrder sortOrder = DEFAULT_SORT_ORDER;

  private ParameterObject(Builder builder) {
    type = builder.type;
    sortBy = builder.sortBy != null && !builder.sortBy.isBlank() ? builder.sortBy : sortBy;
    sortOrder = builder.sortOrder != null ? builder.sortOrder : sortOrder;
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  //Getters and Setters...

  public static final class Builder {

    private String type;
    private String sortBy;
    private SortOrder sortOrder;

    //Omitting builder methods
  }
}
```
