package designpatterns.command;

public class MoveLeftCommand implements Command {
	private Player player;

	public MoveLeftCommand(Player obj) {
		player = obj;
	}

	@Override
	public void execute() {
		player.moveLeft();
	}

}
