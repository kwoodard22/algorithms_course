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
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
   private StdStats stats;
   private double[] thresholds;
   
   // perform trials independent experiments on an n-by-n grid
   public PercolationStats(int n, int trials) {  
       thresholds = new double[trials];
       for (int i = 0; i < trials; i++) {
           Percolation p = new Percolation(n);
           while (!p.percolates()) {
               p.open(StdRandom.uniform(n), StdRandom.uniform(n));
           }
           double estimate = (float) p.numberOfOpenSites() / (n*n);
           thresholds[i] = estimate; 
       }
   }
   
   // sample mean of percolation threshold
   public double mean() {
       return StdStats.mean(thresholds);
   }
   
   // sample standard deviation of percolation threshold
   public double stddev() {
       return StdStats.stddev(thresholds);
   }
   
   // low  endpoint of 95% confidence interval
   public double confidenceLo() {
       return mean() - 1.96 * stddev();
   }
       
   // high endpoint of 95% confidence interval
   public double confidenceHi() {
       return mean() + 1.96 * stddev();
   }

   public static void main(String[] args) {
       StdOut.println("Enter number for n x n grid:");
       int n = StdIn.readInt();
       StdOut.println("Enter number for total trials:");
       int t = StdIn.readInt();
       
       if (n <= 0 || t <= 0) {
           throw new java.lang.IllegalArgumentException("Number and trials should be greater than zero.");
       }
       
       PercolationStats stats = new PercolationStats(n, t);
       StdOut.println("mean                    = " + stats.mean());
       StdOut.println("sample stddev           = " + stats.stddev());
       StdOut.print("95% confidence interval = ");
       StdOut.println("[" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
   }
}
