package edu.gwu.cs.ai.csp;

import java.util.Collection;

/** A constraint Satisfaction Problem. */
public interface CSP {

    CSPSearchState getInitialState();

    Collection<Constraint> getConstraints();

}
