package ki18.jfour;

public class Match {

	public Match() {
		super();
	}

	public Match(Participant p1, Participant p2) {
		super();
		this.p1 = p1;
		this.p2 = p2;
	}

	@Override
	public String toString() {
		return "Match [p1=" + p1 + ", p2=" + p2 + ", winner=" + winner + ", moves=" + moves + "]";
	}

	private Participant p1;
	private Participant p2;

	private Participant winner;

	private int moves;

	public Participant getP1() {
		return p1;
	}

	public void setP1(Participant p1) {
		this.p1 = p1;
	}

	public Participant getP2() {
		return p2;
	}

	public void setP2(Participant p2) {
		this.p2 = p2;
	}

	public Participant getWinner() {
		return winner;
	}

	public void setWinner(Participant winner) {
		this.winner = winner;
	}

	public int getMoves() {
		return moves;
	}

	public void setMoves(int moves) {
		this.moves = moves;
	}

}
