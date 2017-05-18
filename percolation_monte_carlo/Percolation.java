/******************************************************************************
 *  Compilation:  javac Percolation.java
 *  Execution:    java Percolation
 *  Dependencies: StdRandom.java WeightedQuickUnionUF.java
 *
 *  Implements percolation grid
 *
 ******************************************************************************/
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int N;
    private boolean[][] grid;
    private int virtualTop;
    private int virtualBottom;
    private int openSitesCount;
    private WeightedQuickUnionUF uf;
    
    // create n-by-n grid with all sites blocked & link to virtual top & bottom
    public Percolation(int n) {
        N = n;
        uf = new WeightedQuickUnionUF((n*n) + 2);
        virtualTop = n * n;
        virtualBottom = n * n + 1;
        grid = new boolean[n][n];
        
        for (int i = 0; i < n; i++) {
            uf.union(virtualTop, indexOf(0, i));
            uf.union(virtualBottom, indexOf(n - 1, i));
        }
    }
    
    // open a site if is not open already & connect to neighors
    public void open(int row, int col) {
        validateRange(row, col);
        
        if (!isOpen(row, col)) {
            grid[row][col] = true;
            openSitesCount++;
            connectToNeighbors(row, col);
        }
    }
    
    // is site open?
    public boolean isOpen(int row, int col) { 
        validateRange(row, col);
        return grid[row][col];
    }
    
    // is site connected to top numbers (i.e. virtual top)?
    public boolean isFull(int row, int col) {
        validateRange(row, col);
        return uf.connected(virtualTop, indexOf(row, col));
    }
    
    // number of open sites
    public int numberOfOpenSites() {
        return openSitesCount;
    }
    
    // is site connected to top and bottom?
    public boolean percolates() {
        return uf.connected(virtualTop, virtualBottom);
    }
    
    public static void main(String[] args) {
        int n = StdIn.readInt();
        if (n <= 0) { 
            throw new java.lang.IllegalArgumentException("Number must be greater than zero.");
        }
        
        Percolation p = new Percolation(n);
        
        while (!p.percolates()) {
            int x = StdRandom.uniform(n);
            int y = StdRandom.uniform(n);
            System.out.println("Grid[" + x + "][" + y + "]");
            p.open(x, y);
        }
        double estimate = (float) p.numberOfOpenSites() / (n*n);
        System.out.println("Estimate of percolation threshold: " + estimate);
    }
    
    /**************************
     * Private utility methods
     **************************/
    
    private int indexOf(int row, int col) {
        return N * row + col;
    }
    
    // throws error if number is not in range
    private void validateRange(int row, int col) {
        if (row < 0 || row > (N - 1) || col < 0 || col > (N - 1))  {
            throw new java.lang.IndexOutOfBoundsException("Number not within range");
        }
    }
    
    // if neighbors are open, then connect
    private void connectToNeighbors(int row, int col) {
        if (col != 0) {
            if (isOpen(row, col - 1)) {
                uf.union(indexOf(row, col), indexOf(row, col - 1));
            }
        }
        if (col != (N - 1)) {
            if (isOpen(row, col + 1)) {
                uf.union(indexOf(row, col), indexOf(row, col + 1));
            }
        }
        if (row != 0) { 
            if (isOpen(row - 1, col)) {
                uf.union(indexOf(row, col), indexOf(row - 1, col));
            }
        }
        if (row != (N - 1)) {
            if (isOpen(row + 1, col)) {
                uf.union(indexOf(row, col), indexOf(row + 1, col));
            }
        }
    }
}