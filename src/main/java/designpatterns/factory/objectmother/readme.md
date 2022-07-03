# Object Mother

- Design Pattern for **unit testing**
- Use sparingly, creates object with certain characteristics
- **Use good descriptive name**

```java
//While you could just create a method, this is great for reuse
public class Approvers {
    public static Employee averageEmployee() {
        //provide values that define average employee
        return new Employee();
    }

    public static Employee highPositionEmployeeWithSatisfactoryReviews() {
        return new Employee();
    }

    //Etc
}
```
