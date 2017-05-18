/******************************************************************************
 *  Compilation:  javac PercolationStats.java
 *  Execution:    java PercolationStats
 *  Dependencies: StdRandom.java StdStats.java Percolation.java
 *  Date: May 17, 2017
 *
 *  Calculates stats for percolation thresholds
 *
 ******************************************************************************
 */
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
   private double[] thresholds;
   
   // Perform trials independent experiments on an n-by-n grid
   public PercolationStats(int n, int trials) {  
       thresholds = new double[trials];
       for (int i = 0; i < trials; i++) {
           Percolation p = new Percolation(n);
           while (!p.percolates()) {
               p.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
           }
           double estimate = (float) p.numberOfOpenSites() / (n*n);
           thresholds[i] = estimate; 
       }
   }
   
   // Sample mean of percolation threshold
   public double mean() {
       return StdStats.mean(thresholds);
   }
   
   // Sample standard deviation of percolation threshold
   public double stddev() {
       return StdStats.stddev(thresholds);
   }
   
   // Low endpoint of 95% confidence interval
   public double confidenceLo() {
       return mean() - 1.96 * stddev();
   }
       
   // High endpoint of 95% confidence interval
   public double confidenceHi() {
       return mean() + 1.96 * stddev();
   }
   
   // MAIN
   public static void main(String[] args) {
       StdOut.println("Enter number for n x n grid:");
       int n = StdIn.readInt();
           
       StdOut.println("Enter number for total trials:");
       int t = StdIn.readInt();
       
       if (n < 1 || t < 1) {
           throw new java.lang.IllegalArgumentException(
               "Number should be greater than 0."
           );
       }
       
       PercolationStats stats = new PercolationStats(n, t);
       StdOut.println("Mean                    = " + stats.mean());
       StdOut.println("Stddev                  = " + stats.stddev());
       StdOut.print("95% confidence interval = ");
       StdOut.println("[" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
   }
}
