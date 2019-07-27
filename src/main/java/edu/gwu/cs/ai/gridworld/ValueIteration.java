package edu.gwu.cs.ai.gridworld;

/** 
 * @author Wang Yue
 */

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Vector;

public class ValueIteration {
    static double epsilon = 0.001; // convergence test threshold
    static int[][] directions = { { 0, 1, 3 }, { 1, 0, 2 }, { 2, 1, 3 }, { 3, 0, 2 } }; // 0 = N, 1 = E, 2 = S, 3 = W

    // print formatted output of Q values
    static void printQStar(GridWorld gw) {
        NumberFormat df = new DecimalFormat("#0.00");
        Vector<Vector<Double>> qVlaue = getQStar(gw);
        System.out.println("******************Value Iteration: Q Values***********************");
        for (int i = 0; i < gw.n; i++) {
            for (int j = 0; j < gw.m; j++) {
                String temp = "";
                if (gw.gridWorld[i][j] == 'W')
                    temp = "W";
                else if (gw.gridWorldValue[i][j] != 0)
                    temp = Double.toString(gw.gridWorldValue[i][j]);
                else {
                    int n = i * gw.m + j;
                    temp = "[" + df.format(qVlaue.get(n).get(0)) + ", " + df.format(qVlaue.get(n).get(1)) + ", "
                            + df.format(qVlaue.get(n).get(2)) + ", " + df.format(qVlaue.get(n).get(3)) + "]";
                }
                System.out.printf("%-30s", temp);
            }
            System.out.println("");
        }
        System.out.println("*************************************************\n");
    }

    // print formatted output of V values
    public static void printVStar(GridWorld gw) {
        NumberFormat df = new DecimalFormat("#0.00");
        double[][] vVlaue = getVStar(gw);
        System.out.println("*******************Value Iteration: V Values*********************");
        for (int i = 0; i < gw.n; i++) {
            for (int j = 0; j < gw.m; j++) {
                String temp = "";
                if (gw.gridWorld[i][j] == 'W')
                    temp = "W";
                else if (gw.gridWorldValue[i][j] != 0)
                    temp = Double.toString(gw.gridWorldValue[i][j]);
                else {
                    temp = df.format(vVlaue[i][j]);
                }
                System.out.printf("%-10s", temp);
            }
            System.out.println("");
        }
        System.out.println("*************************************************\n");
    }

    // get Q*
    static Vector<Vector<Double>> getQStar(GridWorld gw) {
        Vector<Vector<Double>> qValue = new Vector<>();
        initVector(qValue, gw);
        // loop until converge
        while (true) {
            double delta = 0; // value change from last iteraton
            Vector<Vector<Double>> next = new Vector<>();
            initVector(next, gw);
            for (int i = 0; i < gw.n; i++) {
                for (int j = 0; j < gw.m; j++) {
                    int n = i * gw.m + j;
                    next.set(n, getQValue(qValue, gw, i, j));
                    if (Math.abs(next.get(n).get(0) - qValue.get(n).get(0)) > delta)
                        delta = Math.abs(next.get(n).get(0) - qValue.get(n).get(0));
                    if (Math.abs(next.get(n).get(1) - qValue.get(n).get(1)) > delta)
                        delta = Math.abs(next.get(n).get(1) - qValue.get(n).get(1));
                    if (Math.abs(next.get(n).get(2) - qValue.get(n).get(2)) > delta)
                        delta = Math.abs(next.get(n).get(2) - qValue.get(n).get(2));
                    if (Math.abs(next.get(n).get(3) - qValue.get(n).get(3)) > delta)
                        delta = Math.abs(next.get(n).get(3) - qValue.get(n).get(3));
                }
            }
            qValue = next;
            if (delta < epsilon) // if value change is less than convergence test threshold, beak
                break;
        }
        return qValue;
    }

    // get V*
    static double[][] getVStar(GridWorld gw) {
        double[][] current = new double[gw.n][gw.m];
        // loop until converge
        while (true) {
            double delta = 0;
            double[][] next = new double[gw.n][gw.m];
            for (int i = 0; i < gw.n; i++) {
                for (int j = 0; j < gw.m; j++) {
                    next[i][j] = getVValue(current, gw, i, j);
                    if (Math.abs(next[i][j] - current[i][j]) > delta)
                        delta = Math.abs(next[i][j] - current[i][j]);
                }
            }
            current = next;
            if (delta < epsilon)
                break;
        }
        return current;
    }

    // get V value for one grid
    static double getVValue(double[][] current, GridWorld gw, int i, int j) {
        Vector<Double> qValue = new Vector<>();
        // if this is a terminal state, return the terminal value
        if (gw.gridWorldValue[i][j] != 0)
            return gw.gridWorldValue[i][j];
        // North
        double value = 0;
        value += getMoveValue(current, gw, i, j, directions[0][0], 0.8);
        value += getMoveValue(current, gw, i, j, directions[0][1], 0.1);
        value += getMoveValue(current, gw, i, j, directions[0][2], 0.1);
        qValue.add(value);
        // East
        value = 0;
        value += getMoveValue(current, gw, i, j, directions[1][0], 0.8);
        value += getMoveValue(current, gw, i, j, directions[1][1], 0.1);
        value += getMoveValue(current, gw, i, j, directions[1][2], 0.1);
        qValue.add(value);
        // South
        value = 0;
        value += getMoveValue(current, gw, i, j, directions[2][0], 0.8);
        value += getMoveValue(current, gw, i, j, directions[2][1], 0.1);
        value += getMoveValue(current, gw, i, j, directions[2][2], 0.1);
        qValue.add(value);
        // West
        value = 0;
        value += getMoveValue(current, gw, i, j, directions[3][0], 0.8);
        value += getMoveValue(current, gw, i, j, directions[3][1], 0.1);
        value += getMoveValue(current, gw, i, j, directions[3][2], 0.1);
        qValue.add(value);
        // return the max of four values
        return getMax(qValue);
    }

    // get Q value for one grid
    static Vector<Double> getQValue(Vector<Vector<Double>> current, GridWorld gw, int i, int j) {
        Vector<Double> qValue = new Vector<>();
        // if this is a terminal state, return the terminal value
        if (gw.gridWorldValue[i][j] != 0) {
            qValue.add(gw.gridWorldValue[i][j]);
            qValue.add(gw.gridWorldValue[i][j]);
            qValue.add(gw.gridWorldValue[i][j]);
            qValue.add(gw.gridWorldValue[i][j]);
            return qValue;
        }
        // North
        double value = 0;
        value += getMoveValue(current, gw, i, j, directions[0][0], 0.8);
        value += getMoveValue(current, gw, i, j, directions[0][1], 0.1);
        value += getMoveValue(current, gw, i, j, directions[0][2], 0.1);
        qValue.add(value);
        // East
        value = 0;
        value += getMoveValue(current, gw, i, j, directions[1][0], 0.8);
        value += getMoveValue(current, gw, i, j, directions[1][1], 0.1);
        value += getMoveValue(current, gw, i, j, directions[1][2], 0.1);
        qValue.add(value);
        // South
        value = 0;
        value += getMoveValue(current, gw, i, j, directions[2][0], 0.8);
        value += getMoveValue(current, gw, i, j, directions[2][1], 0.1);
        value += getMoveValue(current, gw, i, j, directions[2][2], 0.1);
        qValue.add(value);
        // West
        value = 0;
        value += getMoveValue(current, gw, i, j, directions[3][0], 0.8);
        value += getMoveValue(current, gw, i, j, directions[3][1], 0.1);
        value += getMoveValue(current, gw, i, j, directions[3][2], 0.1);
        qValue.add(value);
        return qValue;
    }

    // get the reward for a single move
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

    // get the reward for a single move
    static double getMoveValue(Vector<Vector<Double>> current, GridWorld gw, int i, int j, int direction, double prob) {
        if (current.isEmpty())
            return 0;
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
            return prob * getMax(current.get(i * gw.m + j)) * gw.gamma;
        return prob * getMax(current.get(i2 * gw.m + j2)) * gw.gamma;
    }

    // return the max value in a vector
    static double getMax(Vector<Double> vector) {
        double max = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < 4; i++) {
            if (vector.get(i) > max)
                max = vector.get(i);
        }
        return max;
    }

    // initialize vector
    // each "grid" contains four values, initialized to 0.
    static void initVector(Vector<Vector<Double>> V, GridWorld gw) {
        for (int i = 0; i < gw.n; i++) {
            for (int j = 0; j < gw.m; j++) {
                int n = i * gw.m + j;
                V.add(new Vector<Double>());
                V.get(n).add(0.0);
                V.get(n).add(0.0);
                V.get(n).add(0.0);
                V.get(n).add(0.0);
            }
        }
    }

    static void printArray(double[][] A) {
        NumberFormat df = new DecimalFormat("#0.00");
        for (int i = 0; i < A.length; i++) {
            System.out.println("");
            for (int j = 0; j < A[i].length; j++) {
                System.out.print("  " + df.format(A[i][j]) + "  ");
            }
        }
        System.out.println("\n****************");
    }

    static void printVector(Vector<Vector<Double>> V, int n, int m) {
        NumberFormat df = new DecimalFormat("#0.00");
        for (int i = 0; i < n; i++) {
            System.out.println("");
            for (int j = 0; j < m; j++) {
                int k = i * m + j;
                System.out.print(" [");
                System.out.print(df.format(V.get(k).get(0)) + ", " + df.format(V.get(k).get(1)) + ", " + df.format(V.get(k).get(2)) + ", "
                        + df.format(V.get(k).get(3)));
                System.out.print("] ");
            }
        }
        System.out.println("\n****************");
    }

}
