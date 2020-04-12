package edu.gwu.cs.ai.hmm.datagen;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.gwu.cs.ai.hmm.Dice;

public class DiceSequenceGenerator {

	private static final double HALF = 0.5;

	public void generateOneInput(int timeSlots, double probKeepSame, double[][] emissionProbs) throws IOException {
		/* States and emission Probabilities */
		Dice d1 = new Dice(1);
		d1.addFace(Dice.FACE_1, emissionProbs[0][0]);
		d1.addFace(Dice.FACE_2, emissionProbs[0][1]);
		d1.addFace(Dice.FACE_3, emissionProbs[0][2]);
		d1.finalizeDice();

		Dice d2 = new Dice(2);
		d2.addFace(Dice.FACE_1, emissionProbs[1][0]);
		d2.addFace(Dice.FACE_2, emissionProbs[1][1]);
		d2.addFace(Dice.FACE_3, emissionProbs[1][2]);
		d2.finalizeDice();

		Dice d3 = new Dice(3);
		d3.addFace(Dice.FACE_1, emissionProbs[2][0]);
		d3.addFace(Dice.FACE_2, emissionProbs[2][1]);
		d3.addFace(Dice.FACE_3, emissionProbs[2][2]);
		d3.finalizeDice();

		List<Dice> states = new ArrayList<>();
		states.add(d1);
		states.add(d2);
		states.add(d3);

		List<String> stateStrings = new ArrayList<>();
		List<String> emissions = new ArrayList<>();
		SecureRandom secureRandom = new SecureRandom();


		Dice d = d1;
		System.out.println("Starting with " + d.getId());
		for (int i = 0; i < timeSlots; i++) {
			states.add(d);
			stateStrings.add(String.valueOf(d.getId()));
			emissions.add(d.generateRandomMove());
			double rv = secureRandom.nextDouble();
			if (rv < probKeepSame) {
				System.out.println("Not switching");
				// keep the same dice
			} else {
				double rv2 = secureRandom.nextDouble();
				if (rv2 < HALF) {
					// Add 1
					d = states.get(d.getId() % 3); // Looks weird, but adds 1, due to 0 indexing of the array
					System.out.println("Adding 1, Switching to " + d.getId());
				} else {
					// Add 2
					d = states.get((d.getId() + 1) % 3); // Looks weird, but adds 2, due to 0 indexing of the array
					System.out.println("Adding 2, witching to " + d.getId());
				}
			}
		}

		PrintWriter pw = new PrintWriter("src/main/resources/hmm/dice/hmm_dice_" + System.currentTimeMillis() + ".txt");
		pw.println("# Sample input file generated automatically at " + new Date());
		pw.println("# All lines beginning with # are comments and can be ignored");
		pw.println("# Probability of keeping the same dice");
		pw.println("" + probKeepSame);
		pw.println("# Emission Probs");
		pw.println(d1.getFaceProbability(Dice.FACE_1) + " " + d1.getFaceProbability(
				Dice.FACE_2)
				+ " "
				+ d1.getFaceProbability(Dice.FACE_3));
		pw.println(d2.getFaceProbability(Dice.FACE_1) + " " + d2.getFaceProbability(
				Dice.FACE_2)
				+ " "
				+ d2.getFaceProbability(Dice.FACE_3));
		pw.println(d3.getFaceProbability(Dice.FACE_1) + " " + d3.getFaceProbability(
				Dice.FACE_2)
				+ " "
				+ d3.getFaceProbability(Dice.FACE_3));
		pw.println("#");
		pw.println("# Emissions");
		pw.println(String.join(",", emissions));
		pw.println("#");
		pw.println("# States, for testing/validation (Only available in some files)");
		pw.println(String.join(",", stateStrings));
		pw.close();
	}

	public static void main(String[] args) throws IOException {
		DiceSequenceGenerator dsg = new DiceSequenceGenerator();
		double[][] emissionProbs = { { 0.6, 0.2, 0.2 }, { 0.2, 0.6, 0.2 }, { 0.2, 0.2, 0.6 } };
		dsg.generateOneInput(100, 0.99, emissionProbs);
	}

	public static Object getRandom(Object o1, Object o2, Object o3) {
		double rv = new SecureRandom().nextDouble();
		if (rv < 1 / 3) {
			return o1;
		}
		if (rv < 2 / 3) {
			return o2;
		}
		return o3;
	}
}
