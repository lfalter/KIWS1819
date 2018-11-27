package gerneralsearch;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BoardState {

	private Pawn[][] pawnMatrix = new Pawn[Simulator.COLS][Simulator.ROWS];

	private List<BoardState> parentStateList;
	private List<BoardState> childStateList;

	private int moveCount = 0;

	public List<BoardState> getParentStateList() {
		return parentStateList;
	}

	public void setPawnMatrix(Pawn[][] pawnMatrix) {
		this.pawnMatrix = pawnMatrix;
	}

	public void setParentStateList(List<BoardState> parentStateList) {
		this.parentStateList = parentStateList;
	}

	public void setChildStateList(List<BoardState> childStateList) {
		this.childStateList = childStateList;
	}

	public List<BoardState> getChildStateList() {
		return childStateList;
	}

	public static BoardState executeOptionToNewState(BoardState inititalState, Option option) {
		BoardState boardState = new BoardState();
		boardState.setPawnMatrix(inititalState.getPawnMatrix().clone());
		boardState.setMoveCount(inititalState.getMoveCount());
		boardState.execute(option);
		return boardState;
	}

	public boolean samePawnMatrix(BoardState o) {
		Pawn[][] pawnMatrix2 = o.getPawnMatrix();
		for (int c = 0; c <= Simulator.ROWS; c++) {
			for (int r = 0; r <= Simulator.COLS; r++) {
				if (pawnMatrix2[c][r] != pawnMatrix[c][r]) {
					return false;
				}
			}
		}
		return true;
	}

	public Pawn[][] getPawnMatrix() {
		return pawnMatrix;
	}

	public Set<Option> createAndgetAllOptions() {
		for (int c = 0; c < Simulator.COLS; c++) {
			if (pawnMatrix[c][Simulator.COLS - 1] != null) {
				Option option = new Option();
				option.setColumn(c);
				option.setPawn(Pawn.BLUE);
			}
		}
		return new HashSet<>();
	}

	public void execute(Option option) {
		int column = option.getColumn();
		Pawn pawn = option.getPawn();
		for (int r = 0; r <= Simulator.ROWS; r++) {
			if (pawnMatrix[r][column] != null) {
				pawnMatrix[r][column] = pawn;
			}
		}
	}

	public int getMoveCount() {
		return moveCount;
	}

	public void setMoveCount(int moveCount) {
		this.moveCount = moveCount;
	}

}
