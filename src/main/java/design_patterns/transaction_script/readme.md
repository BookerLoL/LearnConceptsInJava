# Transaction Script

Organizing business logic by procedures

- procedure handles single request

```java
public class Reserver {
    private final ReserveDao reserveDao;

    public void reserveRoom(int roomNumber) {
        //make DAO calls
    }

    public void cancelRoomReservation(int roomNumber) {
        //make DAO calls
    }
}
```
