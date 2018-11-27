package ki18.jfour;

import java.util.Scanner;

public class Arena {

//	private static int CALCULATION_TIME = 60000 * 5;
	private static int CALCULATION_TIME = 1000;
	private static boolean MANUAL = true;

	/*
	 * welcome to the summoners rift
	 */
	public static void main(String[] args) throws InterruptedException {

		MyAI myAi = new MyAI();
		MyAI NEWCONTENDERXXXXXX = new MyAI();

		Board b = new Board(7, 10);

		int i = -1;
		int maxMoves = 100;
		Move move = null;
		Scanner scanner = null;
		try {
			if (MANUAL) {
				scanner = new Scanner(System.in);
			}
			while (b.getWinner() == Player.NONE) {
				i++;
				if (i >= maxMoves) {
					System.out.println("Max moves reached.");
					return;
				}

				if (i % 2 == 0) {
					move = getMove(CALCULATION_TIME, b, NEWCONTENDERXXXXXX);
				} else {
					if (MANUAL) {
						System.out.println("ENTER YOUR MOVE: ");
						int column = scanner.nextInt() - 1;
						move = new Move(column);
					} else {
						move = getMove(CALCULATION_TIME, b, myAi);
					}
				}
				System.out.println("Executing move: " + move);
				b = b.executeMove(move);
				System.out.println(b);
			}

			System.out.println(b.getWinner());
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
