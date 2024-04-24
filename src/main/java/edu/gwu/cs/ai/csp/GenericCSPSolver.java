package edu.gwu.cs.ai.csp;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

import edu.gwu.cs.ai.search.SearchStatistics;

public class GenericCSPSolver implements CSPSearchAlgorithm {

	public static final String NEW_LINE = "\r\n";

	/**
	 * Solves using the starting search state using DFS tree search (does not
	 * maintain a closed list). Puts the initial version on top of the stack. Gets
	 * the next variable, puts the next value and adds to the stack, and keeps
	 * going. Sets the current value being explored on variable.currentValue One
	 * item in the stack for each variable. When popping, assigns the next value.
	 * 
	 * @param nPuzzle
	 * @throws Exception
	 */
	@Override
	public SearchStatistics solve(CSP searchProblem) {

		SearchStatistics searchStats = new SearchStatistics();

		CSPSearchState initialState = searchProblem.getInitialState();

		if (searchProblem.isGoalState()) {
			searchStats.setFound(true);
			searchStats.stopTimer();
			return searchStats;
		}

		Deque<CSP> openSet = new ArrayDeque<>();
		openSet.push(searchProblem); // Push, that is add first, head of the deque.
		searchStats.incrementOpen();

		searchWhile: while (!openSet.isEmpty()) {
			CSPSearchState bestNode = (CSPSearchState) openSet.pop(); // Pop, that is remove first, head of deque
			Variable bestVariable = getBestVariable(bestNode, CSP.VariableStrategy.RANDOM);
			DomainValue bestValue = getBestValue(bestVariable, CSP.ValueStrategy.RANDOM);
			CSPSearchState bestNodeClone;
			try {
				bestNodeClone = (CSPSearchState) bestNode.clone();
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			if (bestVariable != null && bestValue != null) {
				// TODO
				CSPSearchState nextNode = (CSPSearchState) (bestNodeClone.assignVariable(bestVariable, bestValue));
			}

		}
		searchStats.stopTimer();
		return searchStats;
	}

	/** Gets the "best" value. The generic solver ignores the strategy. */
	private DomainValue getBestValue(Variable bestVariable, CSP.ValueStrategy strategy) {
		for (DomainValue value : bestVariable.getPossibleValues()) {
			return value;
		}
		return null;
	}

	/** Gets the "best" variable. The generic solver ignores the strategy. */
	private Variable getBestVariable(CSPSearchState bestNode, CSP.VariableStrategy strategy) {
		for (Variable var : bestNode.vars.keySet()) {
			if (var.getValue() == null) {
				return var;
			}
		}
		return null;
	}

}
