# Partial Response

- Client sends in a list of fields they need rather than all the fields from the resource

```java
public class JsonMapper {
    //fields is what will be fetched from the object using Reflection
    public String toJson(Object o, String[] fields) throws Exception {
        //omitting implementation
    }
}
```
