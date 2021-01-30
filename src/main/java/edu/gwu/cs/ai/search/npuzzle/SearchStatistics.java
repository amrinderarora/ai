package edu.gwu.cs.ai.search.npuzzle;

/**
 * A simple statistics holder.
 * 
 * @author Amrinder Arora
 */
public class SearchStatistics {

    private int numberOpen = 0;

    private int numberClosed = 0;

    private boolean found = false;

    private int distanceToRoot;

    private int maxOpen = 0;

    public void incrementOpen() {
        numberOpen++;
    }

    public void incrementClosed() {
        numberClosed++;
    }

    public int getMaxOpen() {
        return maxOpen;
    }

    public int getNumberOpen() {
        return numberOpen;
    }


    public void setNumberOpen(int numberOpen) {
        this.numberOpen = numberOpen;
    }

    public int getNumberClosed() {
        return numberClosed;
    }

    public void setNumberClosed(int numberClosed) {
        this.numberClosed = numberClosed;
    }

    public void setFound(boolean foundArg) {
        this.found = foundArg;
    }

    public boolean isFound() {
        return found;
    }

    @Override
    public String toString() {
        return "SearchStatistics [numberOpen=" + numberOpen + ", maxOpen: " + maxOpen + ", numberClosed=" + numberClosed + ", found=" + found
                + ", distanceToRoot=" + distanceToRoot + "]";
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
