package edu.gwu.cs.ai.search;

/**
 * A simple statistics holder.
 * 
 * @author Amrinder Arora
 */
public class SearchStatistics {

    private static final int LARGE_BASE = 1000000;

    private int numberOpen = 0;

    private int numberClosed = 0;

    private boolean found = false;

    private int distanceToRoot;

    private int maxOpen = 0;

    private Long finishTime;

    private long startTime;

    public SearchStatistics() {
        startTimer();
    }

    public void incrementOpen() {
        numberOpen++;
        if (numberOpen % LARGE_BASE == 0) {
            System.out.println("number Open: " + numberOpen);
        }
    }

    public void incrementClosed() {
        numberClosed++;
    }

    public int getMaxOpen() {
        return maxOpen;
    }

    public int getTotalOpen() {
        return numberOpen;
    }


    public void setTotalOpen(int totalOpenArg) {
        this.numberOpen = totalOpenArg;
    }

    public int getTotalClosed() {
        return numberClosed;
    }

    public void setTotalClosed(int totalClosedArg) {
        this.numberClosed = totalClosedArg;
    }

    public void setFound(boolean foundArg) {
        this.found = foundArg;
    }

    public boolean isFound() {
        return found;
    }

    @Override
    public String toString() {
        return "SearchStatistics [totalTime in Millis= " + getElapsedTimeMillis() + ", numberOpen=" + numberOpen + ", maxOpen: " + maxOpen + ", numberClosed="
                + numberClosed
                + ", found=" + found
                + ", distanceToRoot=" + distanceToRoot + "]";
    }

    /**
     * Returs the elapsed time in milli seconds. If the timer was stopped already, it uses that.
     * If the timer was not stopped yet, it stops it now.
     * 
     * @return
     */
    public long getElapsedTimeMillis() {
        if (finishTime == null) {
            stopTimer();
        }
        return finishTime - startTime;
    }

    public void stopTimer() {
        this.finishTime = System.currentTimeMillis();
    }

    public void startTimer() {
        this.startTime = System.currentTimeMillis();
        finishTime = null;
    }

    public int getDistanceToRoot() {
        return distanceToRoot;
    }

    public void setDistanceToRoot(int distanceToRootArg) {
        this.distanceToRoot = distanceToRootArg;
    }

    public void setCurrentOpen(int size) {
        if (size > maxOpen) {
            maxOpen = size;
        }
    }
}
