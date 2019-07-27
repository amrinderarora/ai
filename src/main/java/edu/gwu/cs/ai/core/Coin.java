package edu.gwu.cs.ai.core;

import java.security.SecureRandom;

public class Coin {
    private static final String HEADS = "H";
    private static final String TAILS = "T";
    private String name;
    double pHeads; // pTails = 1 - pHead;

    public Coin(String name, double pHead) {
        this.name = name;
        this.pHeads = pHead;
    }

    private static SecureRandom srandom = new SecureRandom();

    public String toss() {
        double dval = srandom.nextDouble();
        if (dval < pHeads) {
            return Coin.HEADS;
        }
        return Coin.TAILS;
    }

    @Override
    public String toString() {
        return name;
    }
}