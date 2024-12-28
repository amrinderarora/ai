package edu.gwu.cs.ai.core;

/**
 * A holder to capture the problem, solution and performance results.
 * 
 * @author Amrinder Arora
 */
public class ProblemSolutionPerformance {
	
	private String problem;
	private String solution;
	private String performance;
	
	public String getProblem() {
		return problem;
	}
	public void setProblem(String problem) {
		this.problem = problem;
	}
	public String getSolution() {
		return solution;
	}
	public void setSolution(String solution) {
		this.solution = solution;
	}
	public String getPerformance() {
		return performance;
	}
	public void setPerformance(String performance) {
		this.performance = performance;
	}
	
	public ProblemSolutionPerformance(String problemArg, String solutionArg, String performanceArg) {
		this.problem = problemArg;
		this.solution = solutionArg;
		this.performance = performanceArg;
	}
	
	@Override
	public String toString() {
		return "problem: " + problem + ", solution: " + solution + ", performance: " + performance;
	}
}
