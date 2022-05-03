# Servant Design Pattern

**Behavioral** Design Pattern

Helper class that defines behavior for a group of common objects

```java
public class Point {
    int x;
    int y;
    
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

interface Moveable {
    public void setPosition(Point p);
    public Point getPosition();
}

public class Triangle implements Moveable {
    Point p;
    //other stuff

    public void setPosition(Point p) {
        this.p = p;
    }
    public Point getPosition() {
        return p;
    }
}

public class Square implements Moveable {
    Point p;
    //other stuff

    public void setPosition(Point p) {
        this.p = p;
    }
    public Point getPosition() {
        return p;
    }
}

public class MoverServant {
    public static void moveTo(Moveable moveable, int x, int y) {
        Point prevPos = moveable.getPosition();
        System.out.println("Previous pos: " + prevPos.x + ", " + prevPos.y);
        moveable.setPosition(new Point(x, y));
    }
    
    public static void moveBy(Moveable moveable, int x, int y) {
        Point prevPos = moveable.getPosition();
        prevPos.x = prevPos.x + x;
        prevPos.y = prevPos.y + y;
    }
}

public class Example {
    public static void main(String[] args) {
        Triangle t = new Triangle();
        t.setPosition(new Position(0, 0));
        MoverServant.moveTo(t, 1, 1);
        
        Square s = new Square();
        s.setPosition(new Position(1, 1));
        MoverServant.moveBy(s, 1, 1);
    }
}
```