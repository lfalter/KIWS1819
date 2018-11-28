package ki18.jfour;

import java.util.Collection;

public class Executor {

	private final static String NAMEPREFIX = "AI_";
	private final static String NAMEPREFIXRANDOM = "RANDOM_";

	private static int PARTICIPANT_COUNT = 0;

	public static void main(String[] args) throws InterruptedException {

//		versusRandom();
//		versusFaktors(1, 2);
//		versusFaktors(0, 1);
//		versusFaktors(0, 2);
//		versusFaktors(2, 2);
//		versusFaktors(1, 1);

		int time = 1000;
		int rounds = 50;
		versusFaktors(1, 10, time, rounds);

		versusFaktors(1, 1, time, rounds, new TrivialSimulationAdapter());

//		roundRobin();
	}

	private static void versusFaktors(int i, int j, int time, int rounds,
			TrivialSimulationAdapter trivialSimulationAdapter) {

	}

	private static void versusFaktors(int a, int b, int time, int rounds) throws InterruptedException {
		Tournament tournament = new Tournament();

		Participant pp1 = createParticipantWithEFaktor(a);
		Participant pp2 = createParticipantWithEFaktor(b);

		tournament.addParticipant(pp1);
		tournament.addParticipant(pp2);

		tournament.setRounds(rounds);
		tournament.setCalculationTime(time);

		tournament.runTournament();

		System.out.println(pp1);
		System.out.println(pp2);
	}

	private static void roundRobin() throws InterruptedException {

		Tournament tournament = new Tournament();

		for (int i = 0; i < 5; i++) {
			tournament.addParticipant(createParticipantWithEFaktor(i));
		}

		tournament.setRounds(10);
		tournament.setCalculationTime(500);

		Collection<Participant> result = tournament.runTournament();

		for (Participant participant : result) {
			System.out.println(participant);
		}

	}

	private static Participant createParticipantWithEFaktor(double i) {
		MyAI ai = new MyAI();
		ai.setExpansionFaktor(i);
		return createParticipant(ai);
	}

	private static void versusRandom() throws InterruptedException {
		Tournament tournament = new Tournament();

		Participant pp1 = createParticipant();
		Participant pp2 = createParticipant(new RandomAI());

		tournament.addParticipant(pp1);
		tournament.addParticipant(pp2);

		tournament.setRounds(100);
		tournament.setCalculationTime(500);

		tournament.runTournament();

		System.out.println(pp1);
		System.out.println(pp2);
	}

	private static Participant createParticipant() {
		return createParticipant(new MyAI());
	}

	private static Participant createParticipant(AI ai) {
		PARTICIPANT_COUNT++;
		Participant pp1 = new Participant();
		pp1.setAi(ai);
		if (ai instanceof RandomAI) {
			pp1.setName(NAMEPREFIXRANDOM + PARTICIPANT_COUNT);
		} else {
			pp1.setName(NAMEPREFIX + PARTICIPANT_COUNT);
		}
		return pp1;
	}
}
