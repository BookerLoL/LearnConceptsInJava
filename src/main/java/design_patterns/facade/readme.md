# Facade Design Pattern
**Structural** design pattern

Provides simplified interface

Hides complexity of a system for users by providing an interface for them to call

```java
public class ComplexTesterApi {
    //Complex API method
    public static void test(Object input, boolean results, Object ... params) {
    }
}

public interface Tester {
    void test();
}

public class NonNullTesterFacade implements Tester {
    private Object testObject;
    
    public TesterFacade(Object objToTest) {
        this.testObject = objToTest;
    }
    
    public void test() {
        ComplexTesterApi.test(testObject, false, null);
    }
}

public class Example {
    public static void main(String[] args) {
        String testInput = null;
        Tester tester = new NonNullTesterFacade(testInput);
        tester.test();
    }
}
```