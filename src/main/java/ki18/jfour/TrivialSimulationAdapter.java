package ki18.jfour;

public class TrivialSimulationAdapter implements SimulationAdapter {

	private int impromentCounter = 0;

	public Move innerSimulation(Board board) {

		for (Move move : board.possibleMoves()) {

			Board z = board.executeMove(move);
			if (z.getWinner() != Player.NONE) { // since its my turn i am always the winner, if one exists
				impromentCounter++;
				return move;
			}

		}
		return null;

	}

	public int getImpromentCounter() {
		return impromentCounter;
	}

}
