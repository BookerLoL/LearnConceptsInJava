# Method Chaining

- Make each method return instance, often this

- **Builder** pattern does this

- Avoiding the new keyword

```java
public class FluentItem {
    private FluentItem() {}

    FluentItem price(double price) { return this; }
    FluentItem name(String name) { return this; }

    public static void register(Consumable<FluentItem> block) {
        FluentItem item = new FluentItem();
        block.accept(item);
    }
}

FluentItem.register(item -> {
    item.name("item").price(1.23);
})
```
