package edu.gwu.cs.ai.hmm;

/**
 * Classic Viterbi DP algorithm for HMM MLE.
 * 
 * @author Amrinder Arora
 */
public class Viterbi {
    public String[] solve(HMM hmm, double[] initialProb, String[] observations) {
    	
    	// One variable, for brevity
    	final int numObs = observations.length;
    	
    	// Solution state, as symbols
    	String[] solutionStateSymbols = new String[numObs];
    	// Solution state, as integer indexes
    	int[] solutionStates = new int[numObs];
    	
    	double[][] v = new double[hmm.getNumStates()][numObs];
    	int[][] traceback = new int[hmm.getNumStates()][numObs];
    	
    	// Initialization
    	for (int j = 0;j<hmm.getNumStates(); j++) {
    		v[j][0] = initialProb[j] * hmm.getEmissionProbability(j, observations[0]);
    		// System.out.println("j: " + j + ", obs0: " + observations[0] + ", e_prob: " + hmm.getEmissionProbability(j, observations[0]));
    		traceback[j][0] = 0;
    	}

    	// DP Core
        for (int i=1;i<numObs;i++) {
            for (int j=0;j<hmm.getNumStates();j++) {
                double maxVal = 0.0;
                for (int prevState=0; prevState < hmm.getNumStates();prevState++) {
                    double currVal = v[prevState][i-1] * 
                    		hmm.getTransitionProb(prevState,j) * 
                    		hmm.getEmissionProbability(j, observations[i]);
                    if (currVal > maxVal) {
                        maxVal = currVal;
                        traceback[j][i] = prevState;
                    }
                }
                v[j][i] = maxVal;
                // System.out.println("cs,i,v: " + currState + ", " + i + ": " + v[currState][i]);
            }
        }
        
        // Reconstruction
        double bestLastStateValue = -1;
        int bestLastState = -1;
        for (int j = 0; j < hmm.getNumStates(); j++) {
        	if (v[j][observations.length - 1] > bestLastStateValue) {
        		bestLastStateValue = v[j][observations.length - 1];
        		bestLastState = j;
        	}
        }
        System.out.println("bestLastState and its value: " + bestLastStateValue);
        solutionStates[numObs - 1] = bestLastState;
        solutionStateSymbols[numObs - 1] = hmm.getState(bestLastState);
        // System.out.println("For: " + (numObs - 1) + ", bestSymbol: " + solutionStateSymbols[numObs - 1]);
        for (int i = observations.length - 1;i>=1;i--) {
        	solutionStates[i-1] = traceback[solutionStates[i]][i];
        	solutionStateSymbols[i-1] = hmm.getState(solutionStates[i-1]);
            // System.out.println("For: " + i + ", bestSymbol: " + solutionStateSymbols[i-1]);
        }

        // Returns the MLE for the states.
        return solutionStateSymbols;
    }
}
