package gerneralsearch;

import java.util.ArrayList;
import java.util.List;

import ki18.jfour.Board;
import ki18.jfour.Node;

public class Path {

	private List<Node> ancestorLineList = new ArrayList<>();

	private Node move;

	private Board board;

	public Node getEndNode() {
		return move;
	}

	public void setEndNode(Node move) {
		this.move = move;
	}

	public List<Node> getAncestorLineList() {
		return ancestorLineList;
	}

	public void setAncestorLineList(List<Node> ancestorLineList) {
		this.ancestorLineList = ancestorLineList;
	}

	public void setBoard(Board b) {
		board = b;
	}

	public Board getBoard() {
		return board;
	}

}
