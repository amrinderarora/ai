package edu.gwu.cs.ai.hmm;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

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
		double randomValue = new SecureRandom().nextDouble();
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