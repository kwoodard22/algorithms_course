/******************************************************************************
 *  Compilation:  javac FastCollinearPoints.java
 *  Execution:    java FastCollinearPoints
 *  Dependencies: Arrays, ArrayList, HashMap, List, Collections
 *  
 *
 ******************************************************************************/
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Collections;

public class FastCollinearPoints {
    
    private ArrayList<LineSegment> segments = new ArrayList<>();
    private HashMap<Double, List<Point>> foundSegments = new HashMap<>();

    public FastCollinearPoints(Point[] points) {
        checkForErrors(points);
       
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsCopy);
        
        if (hasDuplicate(pointsCopy)) {
            throw new IllegalArgumentException("There are duplicate points");
        }
        
        for (int p = 0; p < pointsCopy.length; p++) {
            Arrays.sort(pointsCopy, pointsCopy[p].slopeOrder());
            ArrayList<Point> segmentPoints = new ArrayList<>();
            
            double slope = 0.0;
            double previousSlope = pointsCopy[p].slopeTo(pointsCopy[p+1]);
            
            for (int q = p+2; q < pointsCopy.length - 2; q++) {
                segmentPoints.add(pointsCopy[q]);
                slope = pointsCopy[p].slopeTo(pointsCopy[q]);
                
                // If adjacent slope is equal add point
                if (slope == previousSlope) {
                    segmentPoints.add(pointsCopy[q]);
                } 
                // If not equal slope but has 3 or more points, add segment
                else if (segmentPoints.size() >= 3) {
                    segmentPoints.add(pointsCopy[p]);
                    addIfNewSegment(segmentPoints, previousSlope);
                } 
                // Otherwise reset setment
                else {
                    segmentPoints.clear();
                }
                previousSlope = slope;                                         
            }
        }
    }
    
    
    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[foundSegments.size()]);
    }
    
    /*
     * PRIVATE UTILITY METHODS
     */
    
    private void checkForErrors(Point[] points) {
        if (points == null) throw new java.lang.NullPointerException();
        if (hasDuplicate(points)) {
            throw new IllegalArgumentException("There are duplicate points");
        }
    }
    
    private boolean hasDuplicate(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i+1]) == 0) {
                return true;
            }
        }
        return false;
    }
    
    private void addIfNewSegment(List<Point> segmentPoints, double slope) {
        Collections.sort(segmentPoints);
        List<Point> endPoints = foundSegments.get(slope);

        Point startPoint = segmentPoints.get(0);
        Point endPoint = segmentPoints.get(segmentPoints.size() - 1);

        // If endpoints existing for given slope,
        // add endpoint for slope & add segment to final & found segment lists
        if (endPoints == null) {
            endPoints = new ArrayList<>();
            endPoints.add(endPoint);
            
            foundSegments.put(slope, endPoints);
            segments.add(new LineSegment(startPoint, endPoint));
        }
        // If it's the same segment (endpoint), then return; 
        // otherwise add segment fo final list
        else {
            for (Point current : endPoints) {
                if (current.compareTo(endPoint) == 0) {
                    return;
                }
            }
            endPoints.add(endPoint);
            segments.add(new LineSegment(startPoint, endPoint));
        }
    }
}
