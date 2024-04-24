package edu.gwu.cs.ai.csp;

/**
 * A *Constraint* object models a constraint in the CSPs.
 * An example constraint might be that node 2 and node 7 cannot have the same color.
 * Another example constraint might be that variables. x1, x2 and x3 all have to different.
 */
public interface Constraint {

    boolean validate(CSPSearchState state);
}
