package ki18.jfour;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import gerneralsearch.Path;
import gerneralsearch.Simulator;

public class MyAI extends AI {

	private Player US;

	private static Random RANDOM = new Random();

	private boolean logdetails = false;

	@Override
	public void start(Board board) {

		US = board.getCurrentPlayer();

		Node root = new Node(100); // dummy start node
		Node leaf;

		Path path;
		int value;
		int counter = -1;
		int maxIterations = 1000;
		while (true) {

			counter++;
			if (counter >= maxIterations) {
				if (logdetails) {
					System.out.println("MAX ITERATIONS REACHED");
				}
				return;
			}

			/*
			 * select
			 */
			path = selection(root, board); // the board contained in path has the state after the execution of the move
											// in endNode

			/*
			 * expand
			 */
			leaf = expand(path.getEndNode(), path.getBoard());

			/*
			 * simulate
			 */
			value = simulate(leaf, path.getBoard());

			/*
			 * backtracking
			 */
			backtrack(leaf, path, value);

			setWinner(path.getBoard(), root, counter);
		}
	}

	private void setWinner(Board board, Node root, int counter) {
		Node winner = chooseBest(root);
		int column = winner.getColumn();
		if (logdetails) {
			System.out.println("------------- ITERATION " + counter + "--------------");
			printValues(root);
			System.out.println("root total: " + root.getTotal());
			System.out.println("setmove: " + column);
			System.out.println("-----------------------------------------------------");
		}
		setBestMove(new Move(column));
	}

	private void backtrack(Node leaf, Path path, int value) {
		List<Node> ancestorLine = path.getAncestorLineList();
		leaf.addValue(value);
		for (Node father : ancestorLine) {
			father.addValue(value);
		}
	}

	private void printValues(Node root) {
		for (Node node : root.getChilds()) {
			double value = calculateValue(node, root);
			System.out.println("Node: " + node.getColumn() + ", value: " + value + ", total: " + node.getTotal()
					+ ", wins: " + node.getWins());
		}
	}

	private int simulate(Node leaf, Board board) {
		board = board.executeMove(new Move(leaf.getColumn()));

		int result = -1;

		while (true) {

			Player winner = board.getWinner();
			List<Move> possibleMoves = board.possibleMoves();

			/*
			 * the end conditions
			 */
			if (winner != Player.NONE) {
				if (winner == US) {
					result = 1;
				} else {
					result = 0;
				}
			} else if (possibleMoves == null || possibleMoves.isEmpty()) { // then its drawn
				result = 0;
			}

			if (result >= 0) {
				if (logdetails) {
					System.out.println("Simulation ended with result: " + result + ", board:");
					System.out.println(board);
				}
				return result;
			}

			/*
			 * execute the next move
			 */
			Move nextMove = new Move(getRandomMove(board));
			board = board.executeMove(nextMove);
		}
	}

	private Path selection(Node root, Board board) {
		Path path = new Path();
		List<Node> anchestorLine = path.getAncestorLineList();
		Node current = root;

		while (true) {
			anchestorLine.add(current);

			if (hasUnexpandedChilds(current, board)) {
				path.setEndNode(current);
				path.setBoard(board);
				if (logdetails) {
					System.out.println("selected: " + current.getColumn());
				}
				return path;
			}

			current = chooseBest(current);
			board = board.executeMove(new Move(current.getColumn()));

		}
	}

	/**
	 * 
	 * @param current
	 * @return the leaf that had been added to the child list of the given node.
	 */
	private Node expand(Node current, Board board) {
		List<Node> childs = current.getChilds();
		Set<Integer> childColumns = current.getChildColumns();

		List<Move> possibleMoves = board.possibleMoves();
		Iterator<Move> iterator = possibleMoves.iterator();

		/*
		 * filter already expanded moves
		 */

		while (iterator.hasNext()) {
			if (childColumns.contains(iterator.next().column)) {
				iterator.remove();
			}
		}

		int nextColumn = getRandomMove(possibleMoves);

		Node node = new Node(nextColumn);
		childs.add(node);
		childColumns.add(nextColumn);
		if (logdetails) {
			System.out.println("expanded: " + node.getColumn());
		}
		return node;
	}

	private int getRandomMove(List<Move> possibleMoves) {
		int numberOfOptions = possibleMoves.size();
		if (numberOfOptions == 1) {
			return possibleMoves.iterator().next().column;
		}
		int randomIndex = RANDOM.nextInt(numberOfOptions - 1);
		int nextColumn = possibleMoves.get(randomIndex).column;
		return nextColumn;
	}

	private int getRandomMove(Board board) {
		return getRandomMove(board.possibleMoves());
	}

	private Node chooseBest(Node father) {
		double max = 0;
		Node winner = null;
		for (Node node : father.getChilds()) {
			double value = calculateValue(node, father);
			if (value >= max) {
				max = value;
				winner = node;
			}
		}
		return winner;
	}

	private double calculateValue(Node node, Node father) {
		int total = node.getTotal();
		int fatherTotal = father.getTotal();
		if (total == 0) {
			return 1000;
		}
		double wins = node.getWins();
		double expansionTerm = Simulator.EXPANSIONFACTOR * Math.sqrt(Math.log(fatherTotal) / total);
		return wins / total + expansionTerm;

	}

	private boolean hasUnexpandedChilds(Node node, Board board) {
		return node.getChilds().size() < board.possibleMoves().size();
	}

	@Override
	public String getDescription() {
		return "monte carlo fucker";
	}

}
