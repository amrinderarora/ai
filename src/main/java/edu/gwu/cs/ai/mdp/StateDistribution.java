package edu.gwu.cs.ai.mdp;

import java.util.HashMap;
import java.util.Map;

public class StateDistribution {
	
	private Map<State, Double> stateDistribution = new HashMap<>();

	public Map<State, Double> getStateDistribution() {
		return stateDistribution;
	}
	
	public void setStateProbability(State stateArg, Double probArg) {
		this.stateDistribution.put(stateArg, probArg);
	}
}
