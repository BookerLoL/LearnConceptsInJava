# Transfer Object Design Pattern

Create a transfer object to pass data from client to server or server to client. Or can hide certain fields.

Also known as the data transfer object design pattern

```java
public class BusinessObject {
    private int id;
    private String name;
    private List<Object> otherInfos;
}

public class BusinessObjectDTO {
    public String name;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
```