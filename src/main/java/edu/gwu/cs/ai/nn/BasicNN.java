package edu.gwu.cs.ai.nn;

import java.util.Random;
import java.util.Arrays;

/**
 * A Basic, toy neural network.
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

    
    private static double sigmoid(double x) { 
    	return 1.0 / (1.0 + Math.exp(-x)); 
    }
    private static double dsigmoid_from_y(double y) { 
    	return y * (1 - y); 
    } // derivative using output y

    // forward pass: returns [hidden..., yhat]
    private double[] forward(double[] x) {
        double[] h = new double[HIDDEN];
        for (int i = 0; i < HIDDEN; i++) {
            double z = b1[i];
            for (int j = 0; j < INPUT; j++) { 
            	z += W1[i][j] * x[j];
            }
            h[i] = sigmoid(z);
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

    private void trainXOR(int epochs, double lr) {
        // XOR dataset
        double[][] X = { {0,0}, {0,1}, {1,0}, {1,1} };
        double[]     Y = { 0,     1,      1,      0   };

        for (int ep = 1; ep <= epochs; ep++) {
            double loss = 0.0;

            // shuffle indices (tiny dataset – optional)
            int[] idx = {0,1,2,3};
            for (int i = 3; i > 0; i--) {
                int k = rnd.nextInt(i+1); int t = idx[i]; idx[i] = idx[k]; idx[k] = t;
            }

            for (int ii = 0; ii < idx.length; ii++) {
                int n = idx[ii];
                double[] x = X[n];
                double y = Y[n];

                // Forward
                double[] out = forward(x);
                double[] h = Arrays.copyOf(out, HIDDEN);
                double yhat = out[HIDDEN];

                // Loss
                double err = yhat - y;
                loss += 0.5 * err * err;

                // Backprop
                double dz2 = err * dsigmoid_from_y(yhat);     // dL/dz2
                // grads for W2, b2
                for (int i = 0; i < HIDDEN; i++) {
                    double g = dz2 * h[i];
                    W2[i] -= lr * g;
                }
                b2 -= lr * dz2;

                // backprop to hidden
                double[] dz1 = new double[HIDDEN];
                for (int i = 0; i < HIDDEN; i++) {
                    double dh = dz2 * W2[i];
                    dz1[i] = dh * dsigmoid_from_y(h[i]);
                }
                // grads for W1, b1
                for (int i = 0; i < HIDDEN; i++) {
                    for (int j = 0; j < INPUT; j++) {
                        W1[i][j] -= lr * dz1[i] * x[j];
                    }
                    b1[i] -= lr * dz1[i];
                }
            }

            if (ep % 500 == 0 || ep == 1) {
                System.out.printf("Epoch %4d | loss=%.6f%n", ep, loss);
            }
        }
    }

    /** Tests predictions */
    private void demoPredictions() {
        double[][] X = { {0,0}, {0,1}, {1,0}, {1,1} };
        for (double[] x : X) {
            double yhat = forward(x)[HIDDEN];
            System.out.printf("Input %s -> %.3f%n", Arrays.toString(x), yhat);
        }
    }

    public static void main(String[] args) {
        BasicNN nn = new BasicNN();
        // Let us train the basic neural network on the XOR
        nn.trainXOR(5000, 0.5);   // epochs, learning rate
        // Now tests the predictions
        nn.demoPredictions();
    }
}
