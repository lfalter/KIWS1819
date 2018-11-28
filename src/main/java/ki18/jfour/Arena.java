package ki18.jfour;

import java.util.Scanner;

public class Arena {

	private static int CALCULATION_TIME = 1000;
	private static boolean MANUAL = true;

	private static int START = -1;

	/*
	 * welcome to the summoners rift
	 */
	public static void main(String[] args) throws InterruptedException {

		Arena arena = new Arena();

		AI p1 = new MyAI();
		Participant pp1 = new Participant();
		pp1.setAi(p1);
		AI p2 = new RandomAI();
		Participant pp2 = new Participant();
		pp2.setAi(p2);
		Match match = new Match(pp1, pp2);

		arena.runMatch(match, 1000, true);

	}

	public void runMatch(Match match, int calculationTime, boolean loggames) throws InterruptedException {

		try {

			if (calculationTime == 0) {
				calculationTime = CALCULATION_TIME;
			}

//			System.out.println("starting match: " + match.toString());
			AI p1 = match.getP1().getAi();
			AI p2 = match.getP2().getAi();
			Board board = new Board(7, 10);

			int i = -1;
			int maxMoves = 100;
			Move move = null;

			Player winner = board.getWinner();
			while (winner == Player.NONE) {
				i++;
				if (i >= maxMoves) {
					System.out.println("Max moves reached.");
					return;
				}

				if (i % 2 == 0) {
					move = getMove(calculationTime, board, p1);
				} else {
					move = getMove(calculationTime, board, p2);
				}
				board = board.executeMove(move);
				if (loggames) {
					System.out.println("Executing move: " + move);
					System.out.println(board);
				}
				winner = board.getWinner();
			}

			switch (i % 2) {
			case 0:
				match.setWinner(match.getP1());
				break;
			case 1:
				match.setWinner(match.getP2());
				break;
			}

			match.setMoves(i + 1); // it starts with 0
			if (loggames) {
				System.out.println(winner);
				System.out.println(i);
			}
		} catch (Exception e) {
			match.setWinner(null);
		}

	}

	public void runMatch2(Match match) {
		AI p1 = match.getP1().getAi();
		AI p2 = match.getP2().getAi();
		Board b = new Board(7, 10);

		int i = START;
		int maxMoves = 100;
		Move move = null;
		Scanner scanner = null;
		try {
			if (MANUAL) {
				scanner = new Scanner(System.in);
			}

			Player winner = b.getWinner();
			while (winner == Player.NONE) {
				i++;
				if (i >= maxMoves) {
					System.out.println("Max moves reached.");
					return;
				}

				if (i % 2 == 0) {
					move = getMove(CALCULATION_TIME, b, p2);
				} else {
					if (MANUAL) {
						System.out.println("ENTER YOUR MOVE: ");
						int column = scanner.nextInt() - 1;
						move = new Move(column);
					} else {
						move = getMove(CALCULATION_TIME, b, p1);
					}
				}
				System.out.println("Executing move: " + move);
				b = b.executeMove(move);
				System.out.println(b);
			}

			switch (i % 2) {
			case 0:
				match.setWinner(match.getP1());
				return;
			case 1:
				match.setWinner(match.getP2());
				return;
			}

			System.out.println(winner);
			System.out.println(i);

		} catch (Exception e) {
			if (scanner != null) {
				scanner.close();
			}
		}
	}

	@SuppressWarnings("deprecation")
	private static Move getMove(final int time, final Board b, AI ai) throws InterruptedException {
		final Thread t = new Thread(() -> ai.start(b));
		t.setUncaughtExceptionHandler((tt, e) -> {
			if (e instanceof ThreadDeath) {
				// swallow
			} else {
				e.printStackTrace();
			}
		});
		t.start();
		Thread.sleep(time);
		t.stop();
		return ai.getBestMove().get();
	}

}
