package gerneralsearch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Simulator {

	static final int ROWS = 7;
	public static final int COLS = 10;
	public static final double EXPANSIONFACTOR = 1.3;

	public static void main(String[] args) {

		List<List<BoardState>> boardStateSet = new ArrayList<List<BoardState>>();
		Simulator simulator = new Simulator();

		while (true) {

			BoardState boardState = new BoardState();
			simulator.constructChildStateList(boardState);
		}

	}

	private void constructChildStateList(BoardState boardState) {
		Set<Option> options = boardState.createAndgetAllOptions();
		for (int i = 1; i <= ROWS; i++) {
		}
	}

}
