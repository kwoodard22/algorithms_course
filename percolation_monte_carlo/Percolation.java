/******************************************************************************
 *  Compilation:  javac Percolation.java
 *  Execution:    java Percolation
 *  Dependencies: StdRandom.java WeightedQuickUnionUF.java
 *
 *  Implements percolation grid
 *
 ******************************************************************************/
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int N;
    private boolean[][] grid;
    private int virtualTop;
    private int virtualBottom;
    private int openSitesCount;
    private WeightedQuickUnionUF uf;
    
    // Create n-by-n grid with all sites blocked & link to virtual top & bottom
    public Percolation(int n) {
        N = n;
        uf = new WeightedQuickUnionUF((n*n) + 2);
        virtualTop = n * n;
        virtualBottom = n * n + 1;
        grid = new boolean[n][n];
        
        for (int i = 1; i < n + 1; i++) {
            uf.union(virtualTop, indexOf(1, i));
            uf.union(virtualBottom, indexOf(n, i));
        }
    }
    
    // Opens a site if is not open & connects it to neighbors
    public void open(int row, int col) {
        validateRange(row, col);
        
        if (!isOpen(row, col)) {
            grid[row - 1][col - 1] = true;
            openSitesCount++;
            connectToNeighbors(row, col);
        }
    }
    
    // Returns true if the site is not blocked
    public boolean isOpen(int row, int col) { 
        validateRange(row, col);
        return grid[row - 1][col -1];
    }
    
    // is site connected to top numbers (i.e. virtual top)?
    public boolean isFull(int row, int col) {
        validateRange(row, col);
        return uf.connected(virtualTop, indexOf(row, col));
    }
    
    // Returns number of open sites
    public int numberOfOpenSites() {
        return openSitesCount;
    }
    
    // Returns true if top is connected to bottom through open sites
    public boolean percolates() {
        return uf.connected(virtualTop, virtualBottom);
    }
    
    // MAIN 
    public static void main(String[] args) {
        int[] trials;
        int r = 0;
        int c = 0;
        int n = StdIn.readInt();
        Percolation p = new Percolation(n);
        
        while (StdIn.hasNextLine() && StdIn.hasNextChar() && !p.percolates()) {
            r = StdIn.readInt();
            c = StdIn.readInt();
            p.open(r, c);
        }
        StdOut.println("Percolates at: " + r + "  " + c);
    }
    
    /**************************
     * Private utility methods
     **************************/
    
    // Get index of row and column from a grid with starting index 0
    // Assignment requires inputs of 1 or greater so this accounts it.
    private int indexOf(int row, int col) {
        return N * (row - 1) + (col - 1);
    }
    
    // Throws error if number is not in range
    private void validateRange(int row, int col) {
        if (row < 1 || row > N || col < 1 || col > N)  {
            throw new java.lang.IndexOutOfBoundsException(
                "Numbers " + row + " " + col + " not within range"
            );
        }
    }
    
    // If neighbors are open, then connect
    private void connectToNeighbors(int row, int col) {
        if (col != 1) {
            if (isOpen(row, col - 1)) {
                uf.union(indexOf(row, col), indexOf(row, col - 1));
            }
        }
        if (col != N) {
            if (isOpen(row, col + 1)) {
                uf.union(indexOf(row, col), indexOf(row, col + 1));
            }
        }
        if (row != 1) { 
            if (isOpen(row - 1, col)) {
                uf.union(indexOf(row, col), indexOf(row - 1, col));
            }
        }
        if (row != N) {
            if (isOpen(row + 1, col)) {
                uf.union(indexOf(row, col), indexOf(row + 1, col));
            }
        }
    }
}