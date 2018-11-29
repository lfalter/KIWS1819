package ki18.jfour;

import java.util.Collection;

public class Executor {

	private final static String NAMEPREFIX = "AI_";
	private final static String NAMEPREFIXRANDOM = "RANDOM_";

	private final static int DEFAULT_ROUNDS = 50;
	private final static int DEFAULT_TIME = 1000;

	private static int PARTICIPANT_COUNT = 0;

	public static void main(String[] args) throws InterruptedException {

		versusAdapters();

	}

	private static void versusAdapters() throws InterruptedException {
		Tournament tournament = new Tournament();

		Participant pp1 = createParticipantWithEFaktorAndApater(1);
		Participant pp2 = createParticipantWithEFaktor(1);

		tournament.addParticipant(pp1);
		tournament.addParticipant(pp2);

		tournament.setRounds(DEFAULT_ROUNDS);
		tournament.setCalculationTime(DEFAULT_TIME);

		tournament.runTournament();

		System.out.println(pp1);
		System.out.println(pp2);
	}

	private static Participant createParticipantWithEFaktorAndApater(double i) {
		return createParticipantWithEFaktor(1, new TrivialSimulationAdapter());
	}

	private static void versusFaktors(double a, double b, int time, int rounds) throws InterruptedException {
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

	private static Participant createParticipantWithEFaktor(double expansionfaktor,
			SimulationAdapter simulationAdapter) {
		MyAI ai = new MyAI();
		ai.setExpansionFaktor(expansionfaktor);
		ai.setSimulationAdapter(simulationAdapter);
		return createParticipant(ai);
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

	private static Participant createParticipantWithEFaktor(double expansionfaktor) {
		return createParticipantWithEFaktor(expansionfaktor, null);
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
