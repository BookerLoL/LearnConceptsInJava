# Data Access Object (DAO) Design Pattern

**Structural** Design Pattern

Used to separate logic between low level implementation from high level business operations

```java
import datastructures.list.ArrayList;

import java.util.Collections;

public class BillPojo {
    int id;
    int total;
}

public interface BillDao {
    BillPojo getBill(int id);

    BillPojo getAllBills();

    BillPojo deleteBill(int id);
}

public class BillDaoImpl implements BillDao {
    private List<BillPojo> bills = new ArrayList<>();

    BillPojo getBill(int id) {
        for (BillPojo bill : bills) {
            if (bill.id == id) {
                return bill;
            }
        }
        return null;
    }
    }

    List<BillPojo> getAllBills() {
        return Collections.unmodifiableList(bills);
    }

    BillPojo deleteBill(int id) {
        for (int i = 0; i < bills.size(); i++) {
            BillPojo bill = bills.get(i);
            if (bill.id == id) {
                return bills.remove(id);
            }
        }
        return null;
    }

public class Example {
    public static void main(String[] args) {
        BillDao billDao = new BillDaoImpl();
        billDao.deleteBill(0);
    }
}
```