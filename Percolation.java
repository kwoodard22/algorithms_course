/******************************************************************************
 *  Compilation:  javac Percolation.java
 *  Execution:    java Percolation
 *  Dependencies: StdIn.java StdRandom.java WeightedQuickUnionUF.java
 *
 *  Implements percolation grid
 *
 ******************************************************************************/
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[] topRow;
    private int[][] number;
    private boolean[][] grid;
    private int openSitesCount = 0;
    private WeightedQuickUnionUF uf;
    
    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        uf = new WeightedQuickUnionUF(n*n);
        topRow = new int[n];
        grid = new boolean[n][n];
        number = new int[n][n];
        int num = 0;
        
        for (int c = 0; c < n; c++) {
            topRow[0] = c;
        }
        for (int r = 0; r < n; r++) {
            for (int c = 0; c < n; c++) {
                number[r][c] = num;
                num++;
            }
        }
    }
    
    // open a site if is not open already & connect to neighors
    public void open(int row, int col) {
        checkOutOfBoundsError(row, col);
        
        if (!isOpen(row, col)) {
            grid[row][col] = true;
            openSitesCount++;
            connectToNeighbors(row, col);
        }
    }
    
    // if neighbors are open, then connect
    private void connectToNeighbors(int row, int col) {
        if (col != 0) {
            if (isOpen(row, col - 1)) {
                uf.union(number[row][col], number[row][col - 1]);
            }
        }
        if (col != (grid[0].length - 1)) {
            if (isOpen(row, col + 1)) {
                uf.union(number[row][col], number[row][col + 1]);
            }
        }
        if (row != 0) { 
            if (isOpen(row - 1, col)) {
                uf.union(number[row][col], number[row - 1][col]);
            }
        }
        if (row != (grid.length - 1)) {
            if (isOpen(row + 1, col)) {
                uf.union(number[row][col], number[row + 1][col]);
            }
        }
    }

    
    // is site open?
    public boolean isOpen(int row, int col) { 
        checkOutOfBoundsError(row, col);
        return grid[row][col];
    }
    
    // is site full?
    // i.e. is bottom site connected to top numbers?
    public boolean isFull(int row, int col) {
        checkOutOfBoundsError(row, col);
        
        for (int i = 0; i < grid[0].length; i++) {
            if (uf.connected(number[row][col], number[0][i])) {
                System.out.println("Last Row: " + number[row][col] + " is connected to Top Row: " + number[0][i]);
                return true;
            }
        }
        return false;
    }
    
    // number of open sites
    public int numberOfOpenSites() {
        System.out.println(openSitesCount);
        return openSitesCount;
    }
    
    // does the system percolate?
    // any in bottom row open & full?
    public boolean percolates() {
        int lastRow = grid.length - 1;
        
        for (int i = 0; i < grid[lastRow].length; i++) {
            if (isOpen(lastRow, i) && isFull(lastRow, i)) {
                return true;
            }
        }
        return false;
    }

    // throws error if number is not in range
    private void checkOutOfBoundsError(int row, int col) {
        if (row < 0 || row > (grid.length - 1) || col < 0 || col > (grid[0].length - 1))  {
            throw new java.lang.IndexOutOfBoundsException("Number not within range");
        }
    }

    public static void main(String[] args) {
        int n = StdIn.readInt();
        if (n <= 0) { 
            throw new java.lang.IllegalArgumentException("Number must be greater than zero.");
        }
        
        Percolation p = new Percolation(n);
        
        while (!p.percolates()) {
            int randomX = StdRandom.uniform(n);
            int randomY = StdRandom.uniform(n);
            System.out.println("x: " + randomX + ", y: " + randomY);
            p.open(randomX, randomY);
            p.numberOfOpenSites();
        }
    }
}   