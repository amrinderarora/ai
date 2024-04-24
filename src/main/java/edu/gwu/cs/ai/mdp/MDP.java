package edu.gwu.cs.ai.mdp;

import java.util.List;
import java.util.Map;

public class MDP {
	private List<State> states;

	private List<String> actions;
	
	private State startState;
	
	private Map<StateAction,StateDistribution> transitionProb;
	
	public List<String> getActions() {
		return actions;
	}

	public void setActions(List<String> actions) {
		this.actions = actions;
	}

	public List<State> getStates() {
		return states;
	}

	public void setStates(List<State> states) {
		this.states = states;
	}


}
