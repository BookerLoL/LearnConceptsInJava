# Private Class Design

- Creates read-only data class but used as a field in another class

```java
public class FakeData {
    private final int cost;
    private final int amount;
    private final String name;

    public FakeData(int cost, int amount, String name) {
        this.cost = cost;
        this.amount = amount;
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public int getAmount() {
        return amount;
    }

    public int getName() {
        return name;
    }
}


public class Product {
    private final FakeData data;

    public Product(int cost, int amount, String name) {
        data = new FakeData(cost, amount, name);
    }

    public void print() {
        //do sth
    }
}
```
