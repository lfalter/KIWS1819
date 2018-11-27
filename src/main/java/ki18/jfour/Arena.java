package ki18.jfour;

public class Arena {

	private static int CALCULATION_TIME = 10000;

	/*
	 * welcome to the summoners rift
	 */
	public static void main(String[] args) throws InterruptedException {

		MyAI myAi = new MyAI();
		MyAI NEWCONTENDERXXXXXX = new MyAI();

		Board b = new Board(7, 10);

		int i = -1;
		int maxMoves = 1;
		while (b.getWinner() == Player.NONE) {
			i++;
			if (i >= maxMoves) {
				System.out.println("Max moves reached.");
				return;
			}

			if (i % 2 == 0) {
				b = makeMove(CALCULATION_TIME, b, NEWCONTENDERXXXXXX);
			} else {
				b = makeMove(CALCULATION_TIME, b, myAi);
			}
			System.out.println(b);
//			System.out.println();
//			System.out.println();
		}

		System.out.println(b.getWinner());
		System.out.println(i);
	}

	@SuppressWarnings("deprecation")
	private static Board makeMove(final int time, final Board b, AI ai) throws InterruptedException {
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
		return b.executeMove(ai.getBestMove().get());
	}

}
