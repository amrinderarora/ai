package edu.gwu.cs.ai.mdp;

public class StateAction {
	
	private State state;
	
	private String action;

	public State getState() {
		return state;
	}

	public String getAction() {
		return action;
	}

	public StateAction(State state, String action) {
		super();
		this.state = state;
		this.action = action;
	}
}
