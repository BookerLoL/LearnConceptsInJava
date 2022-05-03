package designpatterns.state;

public class LockedDoorState extends AbstractDoorState {

	public LockedDoorState(Door door) {
		super(door);
	}

	public void open() {
		System.out.println("Door is locked and can't be opened!");
	}

	public void close() {
		System.out.println("Door is already closed!");
	}

	public void lock() {
		System.out.println("Door is already locked!");
	}

	public void unlock() {
		System.out.println("Unlocking door!");
		this.getDoor().setState(new UnlockedDoorState(this.getDoor()));
	}
}
