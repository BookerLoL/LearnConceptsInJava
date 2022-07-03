# Self Shunt

- Testing Design Pattern
- Uses the testing unit to act as a collaborator
  - **This pattern should be avoided in favor of a a mock object or real object thats developed**

```java
public class SomeTest implements Collaborator {
    //omit

    @Test
    public void testMethod() {
        SUTObject o = new SUTOBject(this);
        //do some test
    }
}
```
