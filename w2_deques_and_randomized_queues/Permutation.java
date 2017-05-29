/******************************************************************************
 *  Compilation:  javac Permutation.java
 *  Execution:    java Permutation
 *  Dependencies: StdIn, StdOut
 *
 *  Takes input and prints k of them at random
 *
 ******************************************************************************/
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    
    public static void main(String[] args) {
        int k = Integer.valueOf(args[0]);
        
        RandomizedQueue<String> s = new RandomizedQueue<String>();
        String input = StdIn.readString();
        
        while (StdIn.hasNextLine()) {
            input = StdIn.readString();
            s.enqueue(input);
        }
        
        for (int i = 0; i < k; i++) {
            StdOut.println(s.dequeue());
        }
    }
}