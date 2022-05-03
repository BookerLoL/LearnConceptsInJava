package design_patterns.command;

public class JumpCommand implements Command {
	private Player player;

	public JumpCommand(Player obj) {
		player = obj;
	}

	@Override
	public void execute() {
		player.jump();
	}
}
