# Remote Proxy

- Provides a local representative for an object in different address space
- Remote object is an object that lives in a heap of a different JVM
- **Java uses Remote Method Invocation (RMI)**

```java
public interface RemoteExample extends Remote {
    public String generateResult() throws RemoteException;
}

public class ReportGenerator extends UnicastRemoteObject implements RemoteExample {
    private static final long serialVersionUID = 1L;

    public String generateResult() throws RemoteException {
        //do sth
        return "";
    }
}

public static void main(String[] args) {
    ((RemoteExample)Naming.loopup("rmi://....")).generateResult();
}
```
