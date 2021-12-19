package design_patterns.state;

public class UnlockedDoorState extends AbstractDoorState {

	public UnlockedDoorState(Door door) {
		super(door);
	}

	@Override
	public void open() {
		System.out.println("Opening the door");
		this.getDoor().setState(new OpenDoorState(this.getDoor()));
	}

	@Override
	public void close() {
		System.out.println("Door is already locked");
	}

	@Override
	public void lock() {
		System.out.println("Unlocking door!");
		this.getDoor().setState(new LockedDoorState(this.getDoor()));
	}

	@Override
	public void unlock() {
		System.out.println("Door is already unlocked!");
	}

}
