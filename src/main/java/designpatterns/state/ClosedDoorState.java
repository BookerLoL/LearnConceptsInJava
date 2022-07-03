package designpatterns.state;

public class ClosedDoorState extends AbstractDoorState {

	public ClosedDoorState(Door door) {
		super(door);
	}

	@Override
	public void open() {
		System.out.println("Opening the door");
		this.getDoor().setState(new OpenDoorState(this.getDoor()));
	}

	@Override
	public void close() {
		System.out.println("Door is already closed!");
	}

	@Override
	public void lock() {
		System.out.println("Locking the door now!");
		this.getDoor().setState(new LockedDoorState(this.getDoor()));
	}

	@Override
	public void unlock() {
		System.out.println("The door is already unlocked!");
	}
}
