package edu.gwu.cs.ai.hmm;

import java.util.HashMap;
import java.util.Map;

/**
 * Models a hidden Markov Model. 
 * 
 * @author Amrinder Arora
 */
public class HMM {
	// States
    private String[] states;
    private Map<String, Integer> stateIndexMap = new HashMap<>();
    private String[] emissions;
    private Map<String, Integer> emissionIndexMap = new HashMap<>();
    private double[][] transitionProb;
    private double[][] emissionProb;
    
    /** Sets the states using the given states array. */
    public void setStates(String[] statesArg) {
    	this.states = statesArg;
    	int idx = 0;
    	for (String s: statesArg) {
    		stateIndexMap.put(s, idx++);
    	}
    }

    /** 
     * Given an integer number of states, 
     * sets the states just starting with character 'S0', 'S1' etc.
     * To define states that are more meaningful, use the setStates method with array of state strings.
     * 
     * @param numStates
     */
    public void setStates(int numStates) {
    	states = new String[numStates];
    	for (int i = 0;i<numStates;i++) {
    		states[i] = "S" + i;
    		stateIndexMap.put(states[i], i);
    	}
    }

    /** Gets the states. */
    public String[] getStates() {
        return states;
    }

    /** Gets the number of states. */
    public int getNumStates() {
        return states.length;
    }
    
    public Map<String, Integer> getStateIndexMap() {
    	return stateIndexMap;
    }

    public double[][] getTransitionProb() {
        return transitionProb;
    }

    public double getTransitionProb(int fromState, int toState) {
        return transitionProb[fromState][toState];
    }

    public void setTransitionProb(double[][] transitionProb) {
        this.transitionProb = transitionProb;
    }

    public double[][] getEmissionProb() {
        return emissionProb;
    }

    public void setEmissionProb(double[][] emissionProb) {
        this.emissionProb = emissionProb;
    }
    
    public double getEmissionProbability(int state, String emission) {
    	// Firstly, need to get the index of this emission String
    	int emissionIndex = emissionIndexMap.get(emission);
    	return emissionProb[state][emissionIndex];
    }

	public void setEmissions(String[] emissionsArg) {
		this.emissions = emissionsArg;
    	int idx = 0;
    	for (String e: emissionsArg) {
    		emissionIndexMap.put(e, idx++);
    	}
	}

	/** Gets the emission index map to map from String to integer. */
	public Map<String, Integer> getEmissionIndexMap() {
		return emissionIndexMap;
	}
	
	/** Gets the emission for the given index. */
	public String getEmission(int index) {
		return emissions[index];
	}

	public String getState(int state) {
		return states[state];
	}
}