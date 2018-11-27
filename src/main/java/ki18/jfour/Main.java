package ki18.jfour;

import java.util.*;

class Main {
  public static void main(String[] args) throws Exception {
    final Random r = new Random();
    Board b = new Board(7, 10);

    while (b.getWinner() == Player.NONE) {
      b = makeMove(1000, b);
      System.out.println(b);
      System.out.println();
      System.out.println();
    }

    System.out.println(b.getWinner());
  }

  private static Board makeMove(final int time, final Board b) throws InterruptedException {
      final RandomAI ai = new RandomAI();
      final Thread t = new Thread(() -> ai.start(b));
      t.setUncaughtExceptionHandler((tt, e) -> {
        if (e instanceof ThreadDeath) {
          //swallow
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
