package ki18.jfour;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Node {

	private int column;

//	private List<Node> fathers = new ArrayList<Node>(); 

	private List<Node> expandedChildList = new ArrayList<Node>();
	
	private Set<Integer> childColumns = new HashSet<>(); 

	private int total = 0;

	private double wins = 0;

	public Node(Move move) {
		column = move.column;
	}

	public Node(int randomColumn) {
		column = randomColumn;
	}

	public int getColumn() {
		return column;
	}

	public List<Node> getChilds() {
		return expandedChildList;
	}

	public void setChilds(List<Node> childs) {
		this.expandedChildList = childs;
	}

	public int getTotal() {
		return total;
	}

	public double getWins() {
		return wins;
	}

	/*
	 * public List<Node> getFathers() { return fathers; }
	 * 
	 * public void setFathers(List<Node> fathers) { this.fathers = fathers; }
	 */
	public void addValue(double value) {
		total++;
		wins += value;
	}

	public Set<Integer> getChildColumns() {
		return childColumns;
	}

	public void setChildColumns(Set<Integer> childColumns) {
		this.childColumns = childColumns;
	}

}
