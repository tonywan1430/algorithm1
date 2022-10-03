import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Point implements Comparable<Point> {
    // coordinate of Point(x, y)
    private final int x;
    private final int y;

    public Point(int x, int y) {
        // initialization Point(x, y)
        this.x = x;
        this.y = y;
    }

    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        double slope;
        if (this.x == that.x && this.y == that.y) slope = Double.NEGATIVE_INFINITY;
        else if (this.x == that.x && this.y != that.y) slope = Double.POSITIVE_INFINITY;
        else if (this.y == that.y) slope = 0.0;
        else slope = (double) (that.y - this.y) * 1.0 / (that.x - this.x);
        return slope;
    }

    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        if (this.x == that.x && this.y == that.y) return 0;
        if ((this.y < that.y) || (this.x < that.x && this.y == that.y)) return -1;
        else return 1;
    }

    public Comparator<Point> slopeOrder() {
        return new SlopeComparator();
    }

    private class SlopeComparator implements Comparator<Point> {
        /* YOUR CODE HERE */
        // @Override
        public int compare(Point p1, Point p2) {
            double slope1 = slopeTo(p1);
            double slope2 = slopeTo(p2);
            if (slope1 == slope2) return 0;
            else if (slope1 > slope2) return 1;
            else return -1;
        }
    }

    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        Point origin = new Point(0, 0);
        Point point1 = new Point(0, 1);
        Point point2 = new Point(2, 4);

        StdOut.println("---Test compareTo---");
        StdOut.println("origin compareTo point " + point1 + " = " + origin.compareTo(point1));

        StdOut.println("---Test slopeTo---");
        StdOut.println("origin slopeTo point " + point1 + " = " + origin.slopeTo(point1));
        StdOut.println("origin slopeTo point " + point2 + " = " + origin.slopeTo(point2));


    }

}
