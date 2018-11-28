package ki18.jfour;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Tournament {

	private int contenderCount = -1;
	private Map<Integer, Participant> contenders = new HashMap<Integer, Participant>();
	private int rounds;
	private boolean loggames = false;
	private boolean logrounds = false;

	private int calculationTime = 1000;

	public int getCalculationTime() {
		return calculationTime;
	}

	public void setCalculationTime(int calculationTime) {
		this.calculationTime = calculationTime;
	}

	public int getRounds() {
		return rounds;
	}

	public void setRounds(int rounds) {
		this.rounds = rounds;
	}

	@Override
	public String toString() {
		return "Tournament [contenderCount=" + contenderCount + ", contenders=" + contenders + ", rounds=" + rounds
				+ ", calculationTime=" + calculationTime + "]";
	}

	private static Random RANDOM = new Random();

	public void addParticipant(Participant participant) {
		contenderCount++;
		contenders.put(contenderCount, participant);
	}

	public Collection<Participant> runTournament() throws InterruptedException {
		System.out.println("starting tournament: " + this.toString());

		for (int i = 0; i < rounds; i++) {

			if (logrounds) {
				System.out.println("Starting round: " + i);
			}

			executeRound();
		}

		return contenders.values();
	}

	public void executeRound() throws InterruptedException {

		Set<Match> pairings = generatePairings();

		if (logrounds) {
			System.out.println("generated pairings: " + pairings);
		}

		for (Match match : pairings) {
			Arena arena = new Arena();
			arena.runMatch(match, getCalculationTime(), loggames);
			Participant participant = match.getWinner();
			participant.increaseScore();
			if (loggames) {
				System.out.println("MATCH ENDED: " + match);
			}
		}
		
		for (Match match : pairings) {
			System.out.println("---- RESULT OF ROUND ");
			System.out.println(match);
		}

	}

	private Set<Match> generatePairings() {
		Set<Match> pairings = new HashSet<Match>();
		Set<Integer> avoid = new HashSet<Integer>();
		int i = 0;
		while (i < contenderCount) {
			int p1 = getRandom(avoid);
			int p2 = getRandom(avoid);
			Match match = new Match();
			match.setP1(contenders.get(p1));
			match.setP2(contenders.get(p2));
			if (logrounds) {
				System.out.println("generated match: " + match);
			}
			pairings.add(match);
			i += 2;
		}

		return pairings;
	}

	private int getRandom(Set<Integer> avoid) {
		if (avoid.size() > contenderCount) {
			throw new RuntimeException("no options");
		}
		int result = RANDOM.nextInt(contenderCount + 1);
		while (avoid.contains(result)) {
			result = RANDOM.nextInt(contenderCount + 1);
		}
		avoid.add(result);
		return result;
	}

}
