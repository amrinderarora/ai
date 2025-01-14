package edu.gwu.cs.ai.search;

/**
 * A simple statistics holder.
 * 
 * @author Amrinder Arora
 */
public class SearchStatistics {

    private static final int LARGE_BASE = 10000;

    // The number of nodes currently in open stage
    private int currentOpen = 0;
    
    // The number of nodes that were opened (maybe not simultaneously)
    private int totalOpen = 0;

    private int numberClosed = 0;

    private boolean found = false;

    private double distanceFromRoot;

    private int maxOpen = 0;

    private Long finishTime;

    private long startTime;

	private double currentDistanceFromRoot = 0;

	private double maxDistanceFromRoot = 0;

    public SearchStatistics() {
        startTimer();
    }

    public void incrementOpen() {
        totalOpen++;
        if (totalOpen % LARGE_BASE == 0) {
            System.out.println("total open: " + totalOpen);
        }
    }

    public void incrementClosed() {
        numberClosed++;
    }

    public int getMaxOpen() {
        return maxOpen;
    }

    public int getTotalOpen() {
        return totalOpen;
    }


    public void setTotalOpen(int totalOpenArg) {
        this.totalOpen = totalOpenArg;
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
        return "SearchStatistics [totalTime = " + getElapsedTimeMillis() + "ms, numberOpen=" + totalOpen + ", maxOpen: " + maxOpen + ", numberClosed="
                + numberClosed
                + ", found=" + found
                + ", distanceFromRoot=" + distanceFromRoot + "]";
    }

    /**
     * Returns the elapsed time in milli seconds. If the timer was stopped already, it uses that.
     * If the timer was not stopped yet, it returns the elapsed time so far, but does not stop the timer.
     * 
     * @return
     */
    public long getElapsedTimeMillis() {
        if (finishTime != null) {
            return finishTime - startTime;
        }
        return System.currentTimeMillis() - startTime;
    }

    public void stopTimer() {
        this.finishTime = System.currentTimeMillis();
    }

    public void startTimer() {
        this.startTime = System.currentTimeMillis();
        finishTime = null;
    }

    public double getDistanceFromRoot() {
        return distanceFromRoot;
    }

    public void setDistanceFromRoot(double distArg) {
        this.distanceFromRoot = distArg;
    }

    public void setCurrentOpen(int size) {
    	this.currentOpen = size;
    	if (this.currentOpen > this.maxOpen) {
    		this.maxOpen = this.currentOpen;
    	}
    }

	public void setCurrentDistance(double distanceFromRoot) {
		this.currentDistanceFromRoot = distanceFromRoot;
		if (this.currentDistanceFromRoot > this.maxDistanceFromRoot) {
			this.maxDistanceFromRoot = this.currentDistanceFromRoot;
		}
        if (maxDistanceFromRoot % LARGE_BASE == 0) {
            System.out.println("maxDistanceFromRoot: " + maxDistanceFromRoot + ", currentOpen: " + this.currentOpen);
        }
	}
}
