# Test Data Builder

- A design pattern used for **Unit testing** for creating test data
- Essentially using builder pattern using domain keywords
- Best to keep it simple, straightforward, and use mostly the constructor to build
- [Source](https://www.arhohuttunen.com/test-data-builders/)

```java
public class OrderBuilder {
    private OrderBuilder(OrderBuilder copy) {
        //copy constructor
    }

    public OrderBuilder with(SomeOtherBuilder builder) {
        this.otherBuilder = builder.build();
        return this;
    }

    public OrderBuilder but() {
        return new OrderBuilder(this);
    }

    //Other builder methods

    //Can also consider lambda approach
}
```
