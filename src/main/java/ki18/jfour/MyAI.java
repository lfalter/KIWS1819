package ki18.jfour;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import gerneralsearch.Path;
import gerneralsearch.Simulator;

public class MyAI extends AI {

	private SimulationAdapter simulationAdapter = null;

	public void setSimulationAdapter(SimulationAdapter simulationAdapter) {
		this.simulationAdapter = simulationAdapter;
	}

	private Player US;

	private static Random RANDOM = new Random();

	private boolean logdetails = false;
	private int maxIterations = 100000000;
	private int logAfterNIterations = 100000;

	private double expansionFaktor = Simulator.EXPANSIONFACTOR;

	private boolean tournamentModus = true;

	public double getExpansionFaktor() {
		return expansionFaktor;
	}

	public void setExpansionFaktor(double expansionFaktor) {
		this.expansionFaktor = expansionFaktor;
	}

	@Override
	public void start(Board board) {

		US = board.getCurrentPlayer();

		Node root = new Node(100); // dummy start node
		Node leaf;

		Path path;
		double value;
		int loopcounter = -1;
		while (true) {

			loopcounter++;
			if (loopcounter >= maxIterations) {
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
			if (path == null) {
				continue;
			}
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

			setWinner(path.getBoard(), root, loopcounter);
		}
	}

	private void setWinner(Board board, Node root, int loopcounter) {
		Node winner = chooseBest(root, false, 0);
		int column = winner.getColumn();
		if (!tournamentModus) {
			if (logdetails || loopcounter % logAfterNIterations == 0) {
				System.out.println("------------- ITERATION " + loopcounter + "--------------");
				printValues(root);
				System.out.println("root total: " + root.getTotal());
				System.out.println("setmove: " + column);
				System.out.println("-----------------------------------------------------");
			}
		}
		setBestMove(new Move(column));
	}

	private void backtrack(Node leaf, Path path, double value) {
		List<Node> ancestorLine = path.getAncestorLineList();
		leaf.addValue(value);
		for (Node father : ancestorLine) {
			father.addValue(value);
		}
	}

	private void printValues(Node root) {
		for (Node node : root.getChilds()) {
			int moveCoutner = 0;
			ValueObjekt valueObjekt = calculateValue(node, root, true, moveCoutner);
			System.out.println("Node: " + node.getColumn() + ", value: " + valueObjekt);
		}
	}

	private double simulate(Node leaf, Board board) {
		board = board.executeMove(new Move(leaf.getColumn()));

		double result = -1;

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
				result = 1 / 2;
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
			Move nextMove = getSimulationMove(board);
			board = board.executeMove(nextMove);
		}
	}

	/**
	 * may be overriden
	 * 
	 * @param board
	 * @return
	 */
	protected Move getSimulationMove(Board board) {

		if (simulationAdapter != null) {
			Move innerResult = simulationAdapter.innerSimulation(board);
			if (innerResult != null) {
				return innerResult;
			}
		}

		return new Move(getRandomMove(board));
	}

	private Path selection(Node root, Board board) {
		Path path = new Path();
		List<Node> anchestorLine = path.getAncestorLineList();
		Node current = root;

		int moveCounter = 0;

		while (true) {
			anchestorLine.add(current);

			if (board.possibleMoves().isEmpty()) {
				return null;
			}

			if (hasUnexpandedChilds(current, board)) {
				path.setEndNode(current);
				path.setBoard(board);
				if (logdetails) {
					System.out.println("selected: " + current.getColumn());
				}
				return path;
			}

			current = chooseBest(current, true, moveCounter);
			board = board.executeMove(new Move(current.getColumn()));
			moveCounter++;

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

	private Node chooseBest(Node father, boolean considerExpansionTermin, int moveCounter) {
		double max = 0;
		Node winner = null;
		for (Node node : father.getChilds()) {
			double value = calculateValue(node, father, considerExpansionTermin, moveCounter).getValue();
			if (value >= max) {
				max = value;
				winner = node;
			}
		}
		return winner;
	}

	private ValueObjekt calculateValue(Node node, Node father, boolean considerExpansionTermin, int moveCounter) {
		ValueObjekt valueObjekt = new ValueObjekt();
		int total = node.getTotal();
		double fatherTotal = father.getTotal();
		if (total == 0) {
			valueObjekt.setValue(1000);
			return valueObjekt;
		}
		double wins = node.getWins();
		double expansionTerm = expansionFaktor * Math.sqrt(Math.log(fatherTotal) / total);
		double winavg;
		double value;

		if (moveCounter % 2 == 0) {
			winavg = wins / total; // out win avg
		} else {
			winavg = 1 - (wins / total); // opponents win avg

		}
		if (considerExpansionTermin) {
			value = winavg + expansionTerm;
		} else {
			value = winavg;
		}

		valueObjekt.setExpansionTerm(expansionTerm);
		valueObjekt.setTotal(total);
		valueObjekt.setWinavg(winavg);
		valueObjekt.setWins(wins);
		valueObjekt.setValue(value);
		valueObjekt.setFatherTotal(fatherTotal);
		return valueObjekt;

	}

	private boolean hasUnexpandedChilds(Node node, Board board) {
		return node.getChilds().size() < board.possibleMoves().size();
	}

	@Override
	public String getDescription() {
		return "monte carlo fucker";
	}

	@Override
	public String toString() {
		return "MyAI [expansionFaktor=" + expansionFaktor + "]";
	}

}
