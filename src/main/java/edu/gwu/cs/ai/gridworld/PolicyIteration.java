package edu.gwu.cs.ai.gridworld;

/** @author Wang Yue
*/

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Vector;

public class PolicyIteration {
    static double epsilon = 0.001;
    static int[][] directions = { { 0, 1, 3 }, { 1, 0, 2 }, { 2, 1, 3 }, { 3, 0, 2 } }; // 0 = N, 1 = E, 2 = S, 3 = W

    // print formatted output of V values
    static void printVStar(GridWorld gw) {
        NumberFormat df = new DecimalFormat("#0.00");
        double[][] vVlaue = getVStar(gw);
        System.out.println("*******************Policy Iteration: V Values*********************");
        for (int i = 0; i < gw.n; i++) {
            for (int j = 0; j < gw.m; j++) {
                String temp = "";
                if (gw.gridWorld[i][j] == 'W')
                    temp = "W";
                else if (gw.gridWorldValue[i][j] != 0)
                    temp = Integer.toString(gw.gridWorldValue[i][j]);
                else {
                    temp = df.format(vVlaue[i][j]);
                }
                System.out.printf("%-10s", temp);
            }
            System.out.println("");
        }
        System.out.println("*************************************************\n");
    }

    static double[][] getVStar(GridWorld gw) {
        int[][] policy = new int[gw.n][gw.m]; // intial policy all zeros, i.e., north. 0 = N, 1 = E, 2 = S, 3 = W
        double[][] current = new double[gw.n][gw.m];

        while (true) {
            boolean policyChanged = false;

            // Policy evaluation, calculate values until convergence
            while (true) {
                double delta = 0;
                double[][] next = new double[gw.n][gw.m];
                for (int i = 0; i < gw.n; i++) {
                    for (int j = 0; j < gw.m; j++) {
                        next[i][j] = getVValue(current, gw, i, j, policy[i][j]);
                        if (Math.abs(next[i][j] - current[i][j]) > delta)
                            delta = Math.abs(next[i][j] - current[i][j]);
                    }
                }
                current = next;
                if (delta < epsilon) // if value has converged, break
                    break;
            }

            // look ahead with converged value, extract new policys
            for (int i = 0; i < gw.n; i++) {
                for (int j = 0; j < gw.m; j++) {
                    Vector<Double> vector = getQValue(current, gw, i, j);
                    int newPolicy = getArgMax(vector); // the policy that maximize reward
                    if (newPolicy != policy[i][j]) {
                        // update policy
                        policyChanged = true;
                        policy[i][j] = newPolicy;
                    }
                }
            }
            if (!policyChanged) // if policy has converged, break
                break;
        }
        return current;
    }

    // get V value with one policy (fixed direction)
    public static double getVValue(double[][] current, GridWorld gw, int i, int j, int direction) {
        if (gw.gridWorldValue[i][j] != 0) {
            return gw.gridWorldValue[i][j];
        }
        double value = 0;
        value += getMoveValue(current, gw, i, j, directions[direction][0], 0.8);
        value += getMoveValue(current, gw, i, j, directions[direction][1], 0.1);
        value += getMoveValue(current, gw, i, j, directions[direction][2], 0.1);
        return value;
    }

    // get q value. one-step look ahead
    static Vector<Double> getQValue(double[][] current, GridWorld gw, int i, int j) {
        Vector<Double> qValue = new Vector<>();
        if (gw.gridWorldValue[i][j] != 0) {
            qValue.add((double) gw.gridWorldValue[i][j]);
            qValue.add((double) gw.gridWorldValue[i][j]);
            qValue.add((double) gw.gridWorldValue[i][j]);
            qValue.add((double) gw.gridWorldValue[i][j]);
            return qValue;
        }
        double value = 0;
        value += getMoveValue(current, gw, i, j, directions[0][0], 0.8);
        value += getMoveValue(current, gw, i, j, directions[0][1], 0.1);
        value += getMoveValue(current, gw, i, j, directions[0][2], 0.1);
        qValue.add(value);
        value = 0;
        value += getMoveValue(current, gw, i, j, directions[1][0], 0.8);
        value += getMoveValue(current, gw, i, j, directions[1][1], 0.1);
        value += getMoveValue(current, gw, i, j, directions[1][2], 0.1);
        qValue.add(value);
        value = 0;
        value += getMoveValue(current, gw, i, j, directions[2][0], 0.8);
        value += getMoveValue(current, gw, i, j, directions[2][1], 0.1);
        value += getMoveValue(current, gw, i, j, directions[2][2], 0.1);
        qValue.add(value);
        value = 0;
        value += getMoveValue(current, gw, i, j, directions[3][0], 0.8);
        value += getMoveValue(current, gw, i, j, directions[3][1], 0.1);
        value += getMoveValue(current, gw, i, j, directions[3][2], 0.1);
        qValue.add(value);
        return qValue;
    }

    static int getArgMax(Vector<Double> vector) {
        double max = Double.NEGATIVE_INFINITY;
        int argMax = 0;
        for (int i = 0; i < 4; i++) {
            if (vector.get(i) > max) {
                max = vector.get(i);
                argMax = i;
            }
        }
        return argMax;
    }

    // get the value of a single move
    static double getMoveValue(double[][] current, GridWorld gw, int i, int j, int direction, double prob) {
        int i2 = i;
        int j2 = j;
        if (direction == 0)
            i2 -= 1;
        else if (direction == 1)
            j2 += 1;
        else if (direction == 2)
            i2 += 1;
        else if (direction == 3)
            j2 -= 1;
        if (i2 < 0 || j2 < 0 || i2 >= gw.n || j2 >= gw.m || gw.gridWorld[i2][j2] == 'W')
            return prob * current[i][j] * gw.gamma;
        return prob * current[i2][j2] * gw.gamma;
    }

    static void printArray(int[][] A) {
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length; j++) {
                System.out.print(" " + A[i][j] + " ");
            }
            System.out.println("");
        }
    }
}
