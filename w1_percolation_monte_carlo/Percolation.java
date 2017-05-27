/******************************************************************************
 *  Compilation:  javac Percolation.java
 *  Execution:    java Percolation
 *  Dependencies: StdIn, StdOut, WeightedQuickUnionUF
 *
 *  Implements percolation grid
 *
 ******************************************************************************/
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int n;
    private boolean[][] grid;
    private int virtualTop;
    private int virtualBottom;
    private int openSitesCount;
    private WeightedQuickUnionUF uf;
    
    // Create n-by-n grid with all sites blocked & link to virtual top & bottom
    public Percolation(int num) {
        if (num <= 0) {
            throw new java.lang.IllegalArgumentException("N cannot be zero");
        }
        n = num;
        uf = new WeightedQuickUnionUF((n*n) + 2);
        virtualTop = n * n;
        virtualBottom = (n * n) + 1;
        grid = new boolean[n][n];
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
        return isOpen(row, col) && uf.connected(virtualTop, indexOf(row, col));
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
        int r = 0;
        int c = 0;
        int n = StdIn.readInt();
        Percolation p = new Percolation(n);
        
        while (StdIn.hasNextLine() && StdIn.hasNextChar() && !p.percolates()) {
            r = StdIn.readInt();
            c = StdIn.readInt();
            p.open(r, c);
            int openSites = p.numberOfOpenSites();
            boolean full = p.isFull(r, c);
            StdOut.println(
                           "Open Sites: " + openSites + "\n" +
                           "Is full:    " + full + "\n \n \n");
            
        }
        if (p.percolates()) {
            StdOut.println("Percolates at: " + r + "  " + c);
        }
    }
    
    /**************************
     * Private utility methods
     **************************/
    
    // Get index of row and column from a grid with starting index 0
    // Assignment requires inputs of 1 or greater so this accounts it.
    private int indexOf(int row, int col) {
        return n * (row - 1) + (col - 1);
    }
    
    // Throws error if number is not in range
    private void validateRange(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n)  {
            throw new java.lang.IndexOutOfBoundsException(
                "Numbers " + row + " " + n + "" + col + " not within range"
            );
        }
    }
    
    // If neighbors are open, then connect
    private void connectToNeighbors(int row, int col) {
        // Connect to virtual top
        if (row == 1) {
            uf.union(virtualTop, indexOf(row, col));
        }
        // Connect to virtual bottom
        if (row == n) {
            uf.union(virtualBottom, indexOf(row, col));
        }
        // Connect to left neighbor
        if (col != 1) {
            if (isOpen(row, col - 1)) {
                uf.union(indexOf(row, col), indexOf(row, col - 1));
            }
        }
        // Connect to right neighbor
        if (col != n) {
            if (isOpen(row, col + 1)) {
                uf.union(indexOf(row, col), indexOf(row, col + 1));
            }
        }
        // Connect to top neighbor
        if (row != 1) { 
            if (isOpen(row - 1, col)) {
                uf.union(indexOf(row, col), indexOf(row - 1, col));
            }
        }
        // Connect to bottom neighbor
        if (row != n) {
            if (isOpen(row + 1, col)) {
                uf.union(indexOf(row, col), indexOf(row + 1, col));
            }
        }
    }
}