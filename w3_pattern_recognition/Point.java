/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *
 ******************************************************************************/
import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point
    
    // Constructs the point (x, y)
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    // Draws this point
    public void draw() {
        StdDraw.point(x, y);
    }
    
    // Draws the line segment from this point to that point
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }
    
    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
    
    
     /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        if (this.y == that.y && this.x == that.x) {
            return 0;
        } 
        else if ((this.y < that.y) || (this.y == that.y && this.x < that.x)) {
            return -1;
        } 
        else {
            return 1; 
        }
    }
    
    
    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        
        // Same point
        if (this.x == that.x && this.y == that.y) {
            return Double.NEGATIVE_INFINITY;
        }
        
        double difX = that.x - this.x;
        double difY = that.y - this.y;

        // Vertical
        if (difX == 0.0) return Double.POSITIVE_INFINITY;

        // Horizontal
        if (difY == 0.0) return +0.0;

        return difY / difX;
    }
    
        /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    // Compare two points by slopes they make with this point
    public Comparator<Point> slopeOrder() {
        return new SlopeOrderComparator();
    }
          
    private class SlopeOrderComparator implements Comparator<Point> {
        
        public int compare(Point p1, Point p2) {
            double slopeDiff = slopeTo(p1) - slopeTo(p2);
            
            if (slopeDiff > 0) return 1; 
            if (slopeDiff < 0) return -1;
            return 0; // if equal
        }
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        
    }
}