package gerneralsearch;

public class PawnMatrix {

	private Pawn[][] pawnMatrix = new Pawn[Simulator.COLS][Simulator.ROWS];

	public Pawn get(int column, int row) {
		return pawnMatrix[row][column];
	}

	public void put(int column, Pawn pawn) {
		for (int r = 0; r <= Simulator.ROWS; r++) {
			if (pawnMatrix[r][column] != null) {
				pawnMatrix[r][column] = pawn;
			}
		}

	}

	public void iterateColumn() {

	}

}
