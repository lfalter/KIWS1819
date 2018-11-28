package ki18.jfour;

public class Participant {

	private String name;

	@Override
	public String toString() {
		return "Participant [name=" + name + ", ai=" + ai + ", score=" + score + "]";
	}

	private AI ai;

	private int score = 0;

	public AI getAi() {
		return ai;
	}

	public void setAi(AI ai) {
		this.ai = ai;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void increaseScore() {
		score++;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
