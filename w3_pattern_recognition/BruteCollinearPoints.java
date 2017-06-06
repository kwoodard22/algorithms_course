/******************************************************************************
 *  Compilation:  javac BruteCollinearPoints.java
 *  Execution:    java BruteCollinearPoints
 *  Dependencies: Arrays, ArrayList
 *  
 *
 ******************************************************************************/
import java.util.Arrays;
import java.util.ArrayList;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> foundSegments = new ArrayList<>();
    
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new java.lang.NullPointerException();
       
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy);
        
        if (hasDuplicate(pointsCopy)) {
            throw new IllegalArgumentException("There are duplicate points");
        }
        
        for (int p = 0; p < pointsCopy.length; p++) { 
            for (int q = p + 1; q < pointsCopy.length; q++) {
                double slope1 = pointsCopy[p].slopeTo(pointsCopy[q]);
                
                for (int r = q + 1; r < pointsCopy.length; r++) {
                    double slope2 = pointsCopy[p].slopeTo(pointsCopy[r]);
                    
                    if (slope1 == slope2) {
                        for (int s = r + 1; s < pointsCopy.length; s++) {
                            double slope3 = pointsCopy[p].slopeTo(pointsCopy[s]);
             
                            if (slope2 == slope3) {
                                foundSegments.add(new LineSegment(pointsCopy[p], pointsCopy[s]));
                            }
                        }
                    }
                }
            }
        }
    }
    
    public int numberOfSegments() {
        return foundSegments.size();
    }

    public LineSegment[] segments() {
        return foundSegments.toArray(new LineSegment[foundSegments.size()]);
    }
    
    /*
     * PRIVATE UTILITY METHODS
     */
    private boolean hasDuplicate(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i+1]) == 0) {
                return true;
            }
        }
        return false;
    }
}