package edu.gwu.cs.ai.core;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

/**
 * Models a regular dice. Dice is loaded, so the probabilities of different
 * faces are not all the same. As many faces can be added as you like, although
 * 6 are given as constants. You can have a dice with just 2 faces, that's OK,
 * as long as the probabilities add up to 1.
 * 
 * And probabilities of less than 0 are not supported - we will add that feature
 * after launching time travel.
 * 
 * @author Amrinder Arora
 */
public class Dice {

	public static final String FACE_1 = "1";
	public static final String FACE_2 = "2";
	public static final String FACE_3 = "3";
	public static final String FACE_4 = "4";
	public static final String FACE_5 = "5";
	public static final String FACE_6 = "6";

	private static final double EPSILON = 0.001;

	private int id = 0;
	public Map<String, Double> faces = new HashMap<>();
	public boolean ready = false;

	/** SecureRandom to generate random values. */
	private SecureRandom secureRandom = new SecureRandom();

	/** Constructs a dice object with the given ID. */
	public Dice(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public double getFaceProbability(String face) {
		return faces.get(face);
	}

	public void addFace(String face, double prob) {
		if (prob < 0) {
			throw new IllegalArgumentException("Negative probabilities not supported :-(");
		}
		faces.put(face, prob);
	}

	public void finalizeDice() {
		double sumProb = 0;
		for (double prob : faces.values()) {
			sumProb += prob;
		}
		if (sumProb > 1 + EPSILON || sumProb < 1 - EPSILON) {
			throw new IllegalArgumentException("Cannot finalize the dice, probabilities not complete");
		}
		ready = true;
	}

	public String generateRandomMove() {
		if (!ready) {
			throw new IllegalArgumentException("Dice is not ready");
		}
		double randomValue = secureRandom.nextDouble();
		double cumulativeProbability = 0;
		for (String diceFace : faces.keySet()) {
			cumulativeProbability += faces.get(diceFace);
			if (randomValue <= cumulativeProbability) {
				return diceFace;
			}
		}
		throw new IllegalArgumentException("Unexpected dice face");
	}
}