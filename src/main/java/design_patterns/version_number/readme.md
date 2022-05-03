# Version Number Design Pattern

- An Optimistic Offline Lock pattern

```java
public class Data {
    private long id;
    private String data;
    private long version;

    public Data(Data other) {
        this.id = other.id;
        this.data = other.data;
        this.version = other.version;
    }

    //Omitting getters and setters
}

public class DataRepository {
    private final Map<Long, Data> dataCollection = new HashMap<>();

    public Data get(long dataId) {
        if (!dataCollection.containsKey(dataId)) {
            //can throw exception or return null
        }

        return new Data(dataCollection.get(dataId));
    }

    public boolean update(Data data) {
        if (!dataCollection.containskey(data.getId())) {
            return false;
        }

        Data latestData = dataCollection.get(data.getId());
        if (data.getVersion() != latestData.getVersion()) {
            return false;
        }

        //Update data
        //Update version
        data.setVersion(data.getVersion() + 1);
        dataCollection.put(data.getId(), new Data(data));
        return true;
    }
}
```
