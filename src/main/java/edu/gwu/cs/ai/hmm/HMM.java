package edu.gwu.cs.ai.hmm;

public class HMM {
    private int numStates; // Same as numEmissions
    private double[][] transitionProb;
    private double[][] emissionProb;

    public int getNumStates() {
        return numStates;
    }

    public void setNumStates(int numStates) {
        this.numStates = numStates;
    }

    public double[][] getTransitionProb() {
        return transitionProb;
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
}