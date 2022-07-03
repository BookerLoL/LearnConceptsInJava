# Interpreter Design Pattern

- Use this pattern if you need an Abstract Syntax Tree

```java
public interface Expression<T> {
	T interpret();
}
```
