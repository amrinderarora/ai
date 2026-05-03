package edu.gwu.cs.ai.hmm;

/**
 * Just a little test harness to test HMMs.
 * 
 * @author Amrinder Arora
 */
public class HMMEvaluator {
	private static final int NUM_EXPERIMENTS = 1;

	public static void main(String[] args) {
		for (int i = 0; i < NUM_EXPERIMENTS; i++) {
			runCoinExperiment();
			runDiceExperiment();
		}
	}

	private static void runCoinExperiment() {
		// Creates an HMM object
		HMM hmm = new HMM();
		// States
		String[] states = { "F", "B", "VB" };
		hmm.setStates(states);
		System.out.println(hmm.getStateIndexMap());
		
		// Transition probability
		double[][] transitionProb = { { 0.6, 0.3, 0.1 }, { 0.3, 0.6, 0.1 }, { 0.333, 0.333, 0.334 } };
		hmm.setTransitionProb(transitionProb);
		// Emissions
		String[] emissions = { "H", "T" };
		hmm.setEmissions(emissions);
		// EmissionProb
		double[][] emissionProb = { { 0.5, 0.5 }, { 0.75, 0.25 }, { 1.0, 0.0 } };
		hmm.setEmissionProb(emissionProb);

		// Generates some random states (based on HMM transition probabilities)
		// Initial state, randomly
//		int initialStateIndex = (int) (Math.random() * states.length);
//		String initialState = states[initialStateIndex];
//		for (int i=1;i<20;i++) {
//			
//		}

//		String obs = "HTHTHHHHHHTTH";
		String[] observations = { "H", "T", "H", "T", "H", "H", "H", "H", "H", "T", "H", "T", "H", "H", "H" };
		Viterbi v = new Viterbi();
		double[] initialProb = { 1.0, 0, 0}; //  / 3, 1.0 / 3, 1.0 / 3 };
		String[] mle = v.solve(hmm, initialProb, observations);
		System.out.print("MLE: ");
		for (String s: mle) {
			System.out.print(s + " ");
		}
		System.out.println();

		// String[] actualStates = generateStates(hmm);
		// String[] mleStates Viterbi.solve()
		// Compare actualStates vs. mleStates
		// TODO Auto-generated method stub

	}

	private static void runDiceExperiment() {
		// Creates an HMM object
		HMM hmm = new HMM();
		// States
		String[] states = { "R", "G", "B" };
		hmm.setStates(states);
		System.out.println(hmm.getStateIndexMap());
		
		// Transition probability
		double[][] transitionProb = { { 1.0/3, 1.0/3, 1.0/3 }, { 0.0, 0.5, 0.5 }, { 0.0, 0.0, 1.0 } };
		hmm.setTransitionProb(transitionProb);
		// Emissions
		String[] emissions = { "1", "2", "3", "4" };
		hmm.setEmissions(emissions);
		// EmissionProb
		double[][] emissionProb = { { 0.5, 0.25, 0.25, 0.0}, { 0.25, 0.5, 0.25, 0.0 }, { 0.1666, 0.1666, 0.1667, 0.5 } };
		hmm.setEmissionProb(emissionProb);

		// Generates some random states (based on HMM transition probabilities)
		// Initial state, randomly
//		int initialStateIndex = (int) (Math.random() * states.length);
//		String initialState = states[initialStateIndex];
//		for (int i=1;i<20;i++) {
//			
//		}

//		String obs = "HTHTHHHHHHTTH";
		String[] observations = { "1", "2", "3", "1", "1", "2", "1", "2", "3", "1", "1" };
		Viterbi v = new Viterbi();
		double[] initialProb = { 1.0, 0, 0}; 
		String[] mle = v.solve(hmm, initialProb, observations);
		System.out.print("MLE: ");
		for (String s: mle) {
			System.out.print(s + " ");
		}
		System.out.println();

		// String[] actualStates = generateStates(hmm);
		// String[] mleStates Viterbi.solve()
		// Compare actualStates vs. mleStates
		// TODO Auto-generated method stub

	}

//    /
//	private static int getRandomInt(int length) {
//		return (int) (Math.random() * length);
//	}
}