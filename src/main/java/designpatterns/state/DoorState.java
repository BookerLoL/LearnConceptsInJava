package designpatterns.state;

public interface DoorState {
	public void open();
	public void close();
	public void lock();
	public void unlock();
}
