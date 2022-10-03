import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private static LineSegment[] lines;
    private static ArrayList<LineSegment> arraylines;
    private static Point[] sortedPoints;


    public BruteCollinearPoints(Point[] points) {
        // check if points is null
        if (points == null) throw new IllegalArgumentException("null points");
        // check if each element in points is null
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException("null Point in points");
        }
        arraylines = new ArrayList<LineSegment>();
        // sort points;
        sortedPoints = points.clone();
        Arrays.sort(sortedPoints);
        // check if there are repeated points
        for (int i = 1; i < points.length; i++) {
            if (sortedPoints[i - 1].compareTo(sortedPoints[i]) == 0) throw new IllegalArgumentException("There are repeated points");
        }

        int n = points.length;
        for (int i = 0; i < (n - 3); i++) {
            for (int j = i + 1; j < (n - 2); j++) {
                // slope of Point i and j in sorted points;
                double slopeij = sortedPoints[i].slopeTo(sortedPoints[j]);
                for (int k = j + 1; k < (n - 1); k++) {
                    // slope of Point i and k;
                    double slopeik = sortedPoints[i].slopeTo(sortedPoints[k]);
                    if (slopeik == slopeij) {
                        // StdOut.println("Points " + points[i] + " and " + points[j] + " and " + points[k] + " are colinear");
                        for (int m = k + 1; m < n; m++) {
                            // slope of Point i and j;
                            double slopeim = sortedPoints[i].slopeTo(sortedPoints[m]);
                            if (slopeim == slopeij) {
                                // StdOut.println("Points " + points[i] + " and " + points[j] + " and " + points[k] + " and " + pointsm] + " are colinear");
                                arraylines.add(new LineSegment(sortedPoints[i], sortedPoints[m]));
                            }
                        }
                    }
                }
            }
        }
        lines = arraylines.toArray(new LineSegment[0]);
    }

    public int numberOfSegments() {
        return lines.length;
    }

    public LineSegment[] segments() {
        return lines.clone();
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
            // StdOut.println(points[i]);
        }

        // draw the points
        // StdDraw.setPenColor(Color.BLACK);
        StdDraw.setPenRadius(0.02);
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        // StdOut.println("number of segments is " + collinear.numberOfSegments());
        // StdDraw.setPenColor(Color.BLUE);
        StdDraw.setPenRadius(0.01);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
