# Factory Method Design Pattern

**Creational** Design Pattern

A way to enforce creating objects instead of using the constructor

```java
abstract class Number {
    public static Number from(String numberStr) {
        if (numberStr != null) {
            return null;
        }

        if (numberStr.contains(".")) {
            reutrn new Double(numberStr);
        } else if (numberStr.contains("b")) {
            return new Byte(numberStr);
        } else {
            return new Integer(numberStr)
        }
    }
}

class Integer extends Number {
}

class Double extends Number {
}

class Byte extends Number {
}

public class Example {
    public static void main(String[] args) {
        Number byteNumber = Number.from("01101b");
        Number doubleNumber = Number.from("101.03");
    }
}
```