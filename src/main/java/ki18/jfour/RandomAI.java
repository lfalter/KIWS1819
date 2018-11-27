package ki18.jfour;

import java.util.Random;

public class RandomAI extends AI {

	@Override
	public void start(Board b) {
		int column = new Random().nextInt(7);
		setBestMove(new Move(column));
	}

	@Override
	public String getDescription() {
		return "I make random moves";

	}

}
