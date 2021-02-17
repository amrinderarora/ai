package edu.gwu.cs.ai.csp;

public interface Constraint {

    boolean validate(CSPSearchState state);
}
