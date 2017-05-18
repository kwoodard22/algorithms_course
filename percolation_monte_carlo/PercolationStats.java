/******************************************************************************
 *  Compilation:  javac PercolationStats.java
 *  Execution:    java PercolationStats
 *  Dependencies: StdRandom.java StdStats.java Percolation.java
 *
 *  Calculates stats for percolation thresholds
 *
 ******************************************************************************
 */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import Percolation.java;

public class PercolationStats {
   private StdStats stats;
   private double[] thresholds;
   
   // perform trials independent experiments on an n-by-n grid
   public PercolationStats(int n, int trials) {    
       for (i = 0; i < trials; i++) {
           results.add(Percolation(n)); 
       }
   }
   
   // sample mean of percolation threshold
   public double mean() {
       return StdStats.mean();
   }
   
   // sample standard deviation of percolation threshold
   public double stddev() {
   }
   
   // low  endpoint of 95% confidence interval
   public double confidenceLo() {
   }
       
   // high endpoint of 95% confidence interval
   public double confidenceHi() {
   }

   public static void main(String[] args) {
       int n = StdIn.readInt();
       int t = StdIn.readInt();
       
       if (n <= 0 || t <= 0) {
           throw new java.lang.IllegalArgumentException("Number and trials should be greater than zero.");
       }
   }
   
   
   public static void main(String[] args) {
        int n = StdIn.readInt();
        if (n <= 0) { 
            throw new java.lang.IllegalArgumentException("Number must be greater than zero.");
        }
        
        Percolation p = new Percolation(n);
        
        while (!p.percolates()) {
         p.open(StdRandom.uniform(n), StdRandom.uniform(n));
        }
      
        System.out.println("Estimate of percolation threshold: " + estimate);
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
}
