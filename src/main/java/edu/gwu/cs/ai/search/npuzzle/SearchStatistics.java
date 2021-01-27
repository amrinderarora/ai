package edu.gwu.cs.ai.search.npuzzle;

public class SearchStatistics {

    private int numberOpen = 0;

    private int numberClosed = 0;

    private boolean found = false;

    private int distanceToRoot;

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

    public void incrementOpen() {
        numberOpen++;
    }

    public void incrementClosed() {
        numberClosed++;
    }

    public void setFound(boolean foundArg) {
        this.found = foundArg;
    }

    public boolean isFound() {
        return found;
    }

    @Override
    public String toString() {
        return "SearchStatistics [numberOpen=" + numberOpen + ", numberClosed=" + numberClosed + ", found=" + found + ", distanceToRoot=" + distanceToRoot
                + "]";
    }

    public int getDistanceToRoot() {
        return distanceToRoot;
    }

    public void setDistanceToRoot(int distanceToRootArg) {
        this.distanceToRoot = distanceToRootArg;
    }
}
