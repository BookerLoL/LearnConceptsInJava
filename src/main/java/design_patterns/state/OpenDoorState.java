package designpatterns.state;

public class OpenDoorState extends AbstractDoorState {

	public OpenDoorState(Door door) {
		super(door);
	}

	@Override
	public void open() {
		System.out.println("Door is already opened!");
	}

	@Override
	public void close() {
		System.out.println("Closing the door");
		this.getDoor().setState(new ClosedDoorState(this.getDoor()));
	}

	@Override
	public void lock() {
		System.out.println("The door can't be locked while open");
	}

	@Override
	public void unlock() {
		System.out.println("The door is already unlocked");
	}

}
