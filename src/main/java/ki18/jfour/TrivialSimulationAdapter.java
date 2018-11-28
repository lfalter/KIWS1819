package ki18.jfour;

public class TrivialSimulationAdapter implements SimulationAdapter {

	public Move innerSimulation(Board board) {

		for (Move move : board.possibleMoves()) {
			Board z = board.executeMove(move);
			if (z.getWinner() != Player.NONE) {
				return move;
			}
		}
		return null;

	}
}
