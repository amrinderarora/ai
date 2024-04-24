package edu.gwu.cs.ai.csp.coloring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.gwu.cs.ai.csp.CSP;
import edu.gwu.cs.ai.csp.CSPSearchState;
import edu.gwu.cs.ai.csp.Constraint;
import edu.gwu.cs.ai.csp.DomainValue;
import edu.gwu.cs.ai.csp.Variable;

/**
 * Models the graph coloring Problem.
 * 
 * @author Amrinder Arora
 */
public class ColoringProblem implements CSP, Cloneable {

	/** Mapping of the variables (index to the Variable object). */
    protected Map<Integer, Variable> variableMap;

	/** Coloring constraints. */
	private List<Constraint> constraints;

	/** Number of colors. */
	private int numColors;
	
	/** Map of adjacency maps. */
	private Map<Integer, Map<Integer, Boolean>> adjacencyMap = new HashMap<>();

	@Override
	public Collection<Variable> getVariables() {
		return variableMap.values();
	}

	@Override
	public boolean isGoalState() {
		for (Variable v: variableMap.values()) {
			if (v.getValue() == null) {
				return false;
			}
		}
		return true;
	}

	/** Creates an empty instance of the graph coloring problem. */
	public ColoringProblem() {
	}

	/**
	 * Creates an instance of a graph coloring problem, using the given number
	 * of colors (which provides initial domain values), the number of varibles
	 * and the given list of constraints. Both the variables and colors are assumed
	 * to be 1 index. So, if numColors = 4, that refers to colors 1, 2, 3 and 4, so in
	 * total 4 colors. Similarly numVariables = 5 refers to variables 1, 2, 3, 4 and 5.
	 * 
	 * @param numColors
	 * @param numVariables
	 * @param constraints
	 */
	public ColoringProblem(int numColors, int numVariables, List<Constraint> constraints) {
		this.setNumColors(numColors);
		this.initVariables(numVariables);
		this.setConstraints(constraints);
	}

	/** 
	 * Initializes the given number of variables. It is expected that the 
	 * number of colors is set prior to making this call.
	 * If it is not set, or is changed subsequently, the domain value
	 * list should be manually adjusted.
	 * 
	 * @param numVariables
	 */
	public void initVariables(int numVariables) {
		this.variableMap = new HashMap<>();
		for (int var=1;var<= numVariables;var++) {
			List<DomainValue> domValues = new ArrayList<>();
			for (int domValue = 1; domValue <= numColors; domValue++) {
				domValues.add(new DomainValue(domValue));
			}
			Variable v = new Variable(var, String.valueOf(var), domValues);
			variableMap.put(var, v);
		}
	}

	public int getNumColors() {
		return numColors;
	}

	public void setNumColors(int numColors) {
		this.numColors = numColors;
	}

	@Override
	public List<Constraint> getConstraints() {
		return constraints;
	}

	/**
	 * Initializes the set of coloring constraints for this
	 * coloring problem. It is assumed that the number of variables has 
	 * already been initialized.
	 * 
	 * @param constraints
	 */
	public void setConstraints(List<Constraint> constraints) {
		this.constraints = constraints;
		for (Constraint c : constraints) {
			if (!(c instanceof ColoringConstraint)) {
				throw new IllegalArgumentException("Can only use coloring constraints for this problem.");
			}
			ColoringConstraint cc = (ColoringConstraint) c;
			int endPoint1 = cc.getEndPoint1();
			if (this.variableMap.get(endPoint1) == null) {
				throw new IllegalArgumentException("Unknown variable: " + endPoint1);
			}
			int endPoint2 = cc.getEndPoint2();
			if (this.variableMap.get(endPoint2) == null) {
				throw new IllegalArgumentException("Unknown variable: " + endPoint2);
			}
			getMap(endPoint1).put(endPoint2, true);
			getMap(endPoint2).put(endPoint1, true);
		}
	}

	/**
	 * Gets the adjacency map for the given end point. Initializes the map
	 * to an empty map if necessary.
	 * 
	 * @param endPoint
	 * @return
	 */
	public Map<Integer, Boolean> getMap(int endPoint) {
		if (adjacencyMap.get(endPoint) == null) {
			adjacencyMap.put(endPoint, new HashMap<>());
		}

		return adjacencyMap.get(endPoint);
	}

	@Override
	public CSPSearchState getInitialState() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ColoringProblem clone() throws CloneNotSupportedException {
		ColoringProblem cpp = (ColoringProblem) super.clone();
		for (Variable var: this.getVariables()) {
			Variable var_clone = var.clone();
			cpp.variableMap.put(var_clone.getIndex(), var_clone);
		}
		return cpp;
	}

	
	@Override
	public String getProblemDescription() {
		StringBuffer sb = new StringBuffer();
		sb.append("# Coloring Problem, generated at: " + new java.util.Date() + System.lineSeparator());
		sb.append("# Colors" + System.lineSeparator());
		sb.append("colors = " + this.numColors);
		sb.append(System.lineSeparator());

		sb.append("# Graph: " + System.lineSeparator());
		sb.append("vertices = " + this.getVariables().size());
		sb.append(System.lineSeparator());

		int counter = 1;
		for (Constraint cons : constraints) {
			String edge = "edge." + counter++;
			sb.append(edge + "= " + cons.toString() + System.lineSeparator());
		}
		sb.append(System.lineSeparator());

		return sb.toString();
	}
}
