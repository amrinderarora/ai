package edu.gwu.cs.ai.nn;

import java.util.Arrays;
import java.util.Random;

/**
 * A Basic, toy neural network for the neural network to learn boolean operation.
 * Starting point for CS 6511 students @ GWU.
 * 
 * @author Amrinder Arora
 */
public class BasicNN {
    // Network sizes
    static final int INPUT = 2;
    static final int HIDDEN = 2;
    static final int OUTPUT = 1;

    // Weights and biases
    double[][] W1 = new double[HIDDEN][INPUT];
    double[] b1 = new double[HIDDEN];
    double[] W2 = new double[HIDDEN]; // to single output
    double b2;

    Random rnd = new Random(42);

    public BasicNN() {
        // Xavier-ish small random init
        double s1 = Math.sqrt(2.0 / (INPUT + HIDDEN));
        for (int i = 0; i < HIDDEN; i++) {
            for (int j = 0; j < INPUT; j++) { 
            	W1[i][j] = rnd.nextGaussian() * s1;
            }
            b1[i] = 0.0;
        }
        double s2 = Math.sqrt(2.0 / (HIDDEN + OUTPUT));
        for (int i = 0; i < HIDDEN; i++) { 
        	W2[i] = rnd.nextGaussian() * s2;
        }
        b2 = 0.0;
    }


    /**
     * Given a double x, returns 1/(1 + e^-x).
     * 
     * For example, if x = 0, then returns 0.5
     * Given x = 1, returns 1 / (1+1/e)  = e/(e+1) ~0.7
     * Given x = infinity, returns 1.
     * 
     * Sigmoid function is always positive.
     * 
     * @param x
     * @return 1/(1 + e^-x)
     */
    private static double sigmoid(double x) { 
    	return 1.0 / (1.0 + Math.exp(-x)); 
    }
    
    private static double dsigmoid_from_y(double y) { 
    	return y * (1 - y); 
    } // derivative using output y

    // forward pass: returns [hidden..., yhat]
    private double[] forward(double[] x) {
    	
    	// From input x to z, and then to h.
    	//              W1[i][1]      x1
    	//              W1[i][2]      x2
    	// zi = b1[i] + W1[i][3]   X  x3  
    	//              W1[i][..]     ..
    	//              W1[i][n]      xn
    	// h[i] = sigmoid(zi)
        double[] h = new double[HIDDEN];
        for (int i = 0; i < HIDDEN; i++) {
            double zi = b1[i];
            for (int j = 0; j < INPUT; j++) { 
            	zi += W1[i][j] * x[j];
            }
            h[i] = sigmoid(zi);
        }
        double z2 = b2;
        for (int i = 0; i < HIDDEN; i++) { 
        	z2 += W2[i] * h[i];
        }
        double yhat = sigmoid(z2);

        double[] out = Arrays.copyOf(h, HIDDEN + 1);
        out[HIDDEN] = yhat;
        return out;
    }

    private void trainBooleanFunction(double[][]X, double[] Y, double learning_rate, int epochs) {
        // Let us repeat our learning episodes
        for (int ep = 0; ep <= epochs; ep++) {
            double loss = trainOneEpisode(X, Y, learning_rate);

            if (ep % 500 == 0) {
                System.out.printf("Epoch %4d | loss=%.6f%n", ep, loss);
            }
        }
    }


	private double trainOneEpisode(double[][] X, double[] Y, double learning_rate) {
		
		// shuffle indices - just to make sure our NN doesn't learn something on the basis of the order.
		int[] idx = {0,1,2,3};
		for (int i = 3; i > 0; i--) {
		    int k = rnd.nextInt(i+1); 
		    int t = idx[i]; idx[i] = idx[k]; idx[k] = t;
		}
		
		double loss = 0;

		// Repeat this for each of the inputs..
		for (int ii = 0; ii < idx.length; ii++) {
		    int selectedIndex = idx[ii];
		    double[] x = X[selectedIndex];

		    // Forward
		    double[] out = forward(x);
		    double[] h = Arrays.copyOf(out, HIDDEN);
		    double yhat = out[HIDDEN];

		    // Loss
		    double err = yhat - Y[selectedIndex];
		    loss += 0.5 * err * err;

		    // Backprop
		    double dz2 = err * dsigmoid_from_y(yhat);     // dL/dz2
		    // grads for W2, b2
		    for (int i = 0; i < HIDDEN; i++) {
		        W2[i] -= learning_rate * dz2 * h[i];
		    }
		    b2 -= learning_rate * dz2;
		    
		    // Backprop to hidden
		    double[] dz1 = new double[HIDDEN];
		    for (int i = 0; i < HIDDEN; i++) {
		        double dh = dz2 * W2[i];
		        dz1[i] = dh * dsigmoid_from_y(h[i]);
		    }
		    
		    // grads for W1, b1
		    for (int i = 0; i < HIDDEN; i++) {
		        for (int j = 0; j < INPUT; j++) {
		            W1[i][j] -= learning_rate * dz1[i] * x[j];
		        }
		        b1[i] -= learning_rate * dz1[i];
		    }
		}
		return loss;
	}

    /** Tests predictions once the training is done. */
    private void demoPredictions() {
        double[][] X = { {0,0}, {0,1}, {1,0}, {1,1} };
        for (double[] x : X) {
            double yhat = forward(x)[HIDDEN];
            System.out.printf("Input %s -> %.3f%n", Arrays.toString(x), yhat);
        }
    }

    public static void main(String[] args) {
        BasicNN nn = new BasicNN();
        // Let us train the basic neural network on the XOR/OR/AND etc.
        double[][] X = { {0,0}, {0,1}, {1,0}, {1,1} };
        double[]     Y = { 0,     1,      1,      1   }; // OR. Feel free to change this.
        
        nn.trainBooleanFunction(X, Y, 0.5, 5000);   // epochs, learning rate, boolean
        // Now tests the predictions
        nn.demoPredictions();
    }
}
