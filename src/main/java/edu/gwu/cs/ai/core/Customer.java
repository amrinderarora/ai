package edu.gwu.cs.ai.core;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Models a customer stage.
 * 
 * @author Amrinder Arora
 */
public class Customer {

	public static final String STAGE_ZERO = "ZERO";
	public static final String STAGE_AWARE = "AWARE";
	public static final String STAGE_CONSIDERING = "CONSIDERING";
	public static final String STAGE_EXPERIENCING = "EXPERIENCING";
	public static final String STAGE_READY = "READY";
	public static final String STAGE_SATISFIED = "SATISFIED";
	public static final String STAGE_LOST = "LOST";

	public static final String ACTION_DEMO = "DEMO";
	public static final String ACTION_VIDEO = "VIDEO";
	public static final String ACTION_TESTIMONIAL = "TESTIMONIAL";
	public static final String ACTION_PRICING = "PRICING";
	public static final String ACTION_BLOG = "BLOG";
	public static final String ACTION_PAYMENT = "PAYMENT";

	public List<String> stages = new ArrayList<>();

	// Transition Probabilities: stage x stage
	public double[][] transitionProbs;

	public List<String> actions = new ArrayList<>();

	// Emission Probabilities: stage X action
	public double[][] emissionProbs;

	private Map<String, Map<String, Double>> transitionEdges = new HashMap<>();

	private SecureRandom secureRandom = new SecureRandom();

	private String currentStage = STAGE_ZERO;

	public Customer() {
		stages.add(STAGE_ZERO);
		stages.add(STAGE_AWARE);
		stages.add(STAGE_CONSIDERING);
		stages.add(STAGE_EXPERIENCING);
		stages.add(STAGE_READY);
		stages.add(STAGE_SATISFIED);
		stages.add(STAGE_LOST);

		transitionEdges.put(STAGE_ZERO, new HashMap<>());
		transitionEdges.put(STAGE_AWARE, new HashMap<>());
		transitionEdges.put(STAGE_CONSIDERING, new HashMap<>());
		transitionEdges.put(STAGE_EXPERIENCING, new HashMap<>());
		transitionEdges.put(STAGE_READY, new HashMap<>());
		transitionEdges.put(STAGE_SATISFIED, new HashMap<>());
		transitionEdges.put(STAGE_LOST, new HashMap<>());

		actions.add(ACTION_DEMO);
		actions.add(ACTION_VIDEO);
		actions.add(ACTION_TESTIMONIAL);
		actions.add(ACTION_PRICING);
		actions.add(ACTION_BLOG);
		actions.add(ACTION_PAYMENT);

		// Transition Probabilities: stage x stage
		transitionProbs = new double[stages.size()][stages.size()];
		// Emission Probabilities: stage X action
		emissionProbs = new double[stages.size()][actions.size()];
	}

	/** Adds transition probability. */
	public void addTransitionProbability(String origStage, String nextStage, double prob) {
		// System.err.printf("origStage: %s, nextStage: %s, prob: %f\r\n", origStage,
		// nextStage, prob);
		if (prob < 0) {
			throw new IllegalArgumentException("Negative probabilities not supported :-(");
		}
		transitionProbs[stages.indexOf(origStage)][stages.indexOf(nextStage)] = prob;
		transitionEdges.get(origStage).put(nextStage, prob);
		validateProbabilities(transitionEdges.get(origStage));
	}

	private void validateProbabilities(Map<String, Double> map) {
		double sumProb = 0;
		for (String label : map.keySet()) {
			sumProb += map.get(label);
		}
		if (sumProb > 1) {
			throw new IllegalArgumentException("Sum of probabilities in map greater than 1");
		}
	}

	/** Adds emission probability. */
	public void addEmissionProbability(String stage, String action, double prob) {
		if (prob < 0 || prob > 1) {
			throw new IllegalArgumentException("Negative or Super Positive probabilities not supported :-(");
		}
		emissionProbs[stages.indexOf(stage)][actions.indexOf(action)] = prob;
	}

	/**
	 * Generates one move. Changes the current stage (possibly).
	 * 
	 * @return emissions
	 */
	public List<String> generateOneMove() {
		List<String> emissions = new ArrayList<>();
		// First, the emissions
		for (String linkType : actions) {
			double randomValue = secureRandom.nextDouble();
			if (randomValue <= emissionProbs[stages.indexOf(currentStage)][actions.indexOf(linkType)]) {
				emissions.add(linkType);
			}
		}

		// Next, let us see the transition
		{
			double randomValue = secureRandom.nextDouble();
			double cumulativeProbability = 0;
			Map<String, Double> edgesFromCurrentStage = transitionEdges.get(currentStage);
			for (String nextStage : edgesFromCurrentStage.keySet()) {
				cumulativeProbability += edgesFromCurrentStage.get(nextStage);
				if (randomValue <= cumulativeProbability) {
					currentStage = nextStage;
					System.out.println("Customer entered stage: " + currentStage);
					break;
				}
			}
			// No change
		}

		return emissions;
	}

	public String getCurrentStage() {
		return currentStage;
	}

	public boolean isSatisfiedCustomer() {
		return STAGE_SATISFIED.equals(currentStage);
	}
}